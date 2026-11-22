package org.wicketstuff.datastores.cassandra;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.AbstractPersistentPageStore;
import org.apache.wicket.pageStore.IPersistedPage;
import org.apache.wicket.pageStore.IPersistentPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.schema.KeyspaceMetadata;
import com.datastax.oss.driver.api.core.metadata.schema.TableMetadata;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Selector;

/**
 * A store that saves serialiazed pages in Apache Cassandra.
 */
public class CassandraDataStore extends AbstractPersistentPageStore implements IPersistentPageStore {

	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraDataStore.class);

	/**
	 * The name of the column where the session ids will be stored
	 */
	private static final String COLUMN_SESSION_ID = "sessionId";

	/**
	 * The name of the column where the page ids will be stored
	 */
	private static final String COLUMN_PAGE_ID = "pageId";

	/**
	 * The name of the column where the pages' type will be stored
	 */
	private static final String COLUMN_PAGE_TYPE = "type";

	/**
	 * The name of the column where the pages' size will be stored
	 */
	private static final String COLUMN_PAGE_SIZE = "size";

	/**
	 * The name of the column where the pages' bytes will be stored
	 */
	private static final String COLUMN_DATA = "data";

	/**
	 * The connection to the cluster
	 */
	private final CqlSession session;

	/**
	 * The various settings
	 */
	private final ICassandraSettings settings;

	/**
	 * Constructor.
	 *
	 * Initializes the connection to the Cassandra cluster and creates
	 * the keyspace and/or table if necessary.
	 *
	 * @param applicationName  The name of the application
	 * @param settings The various settings
	 */
	public CassandraDataStore(String applicationName, ICassandraSettings settings)
	{
		super(applicationName);

		this.settings = Args.notNull(settings, "settings");

		if (settings.getContactEndPoints().isEmpty()) {
			throw new IllegalArgumentException("At least one contact point must be provided to be able to connect to Cassandra. See ICassandraSettings#getContactEndPoints.");
		}
		session = CqlSession.builder()
				.addContactEndPoints(settings.getContactEndPoints())
				.withKeyspace(settings.getKeyspaceName())
				.build();

		Metadata metadata = session.getMetadata();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Connected to cluster: {}", metadata.getClusterName());

			for (Node node : metadata.getNodes().values()) {
				LOGGER.info("Datatacenter: {}; Host: {}; Rack: {}",
						new Object[]{node.getDatacenter(), node.getEndPoint().resolve(), node.getRack()});
			}
		}
		String keyspaceName = settings.getKeyspaceName();
		KeyspaceMetadata keyspaceMetadata = createKeyspaceIfNecessary(keyspaceName, metadata);

		createTableIfNecessary(keyspaceName, keyspaceMetadata);

		LOGGER.info("Data will be stored in table '{}' in keyspace '{}'.", settings.getTableName(), keyspaceName);
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int pageId) {
		ResultSet rows = session.execute(QueryBuilder
				.selectFrom(settings.getKeyspaceName(), settings.getTableName())
				.columns(COLUMN_PAGE_TYPE, COLUMN_DATA)
				.whereColumn(COLUMN_SESSION_ID).isEqualTo(literal(sessionIdentifier))
				.whereColumn(COLUMN_PAGE_ID).isEqualTo(literal(pageId))
				.build());
		Row row = rows.one();
		if (row != null) {
			String pageType = row.getString(COLUMN_PAGE_TYPE);

			ByteBuffer data = row.getBytesUnsafe(COLUMN_DATA);
			byte[] bytes = new byte[data.remaining()];
			data.get(bytes);

			LOGGER.debug("Got data for session '{}' and page id '{}'", sessionIdentifier, pageId);

			return new SerializedPage(pageId, pageType, bytes);
		}

		return null;
	}

	@Override
	protected void removePersistedPage(String identifier, IManageablePage page) {
		session.execute(QueryBuilder.deleteFrom(settings.getKeyspaceName(), settings.getTableName())
				.whereColumn(COLUMN_SESSION_ID).isEqualTo(literal(identifier))
				.whereColumn(COLUMN_PAGE_ID).isEqualTo(literal(page.getPageId()))
				.build());

		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", identifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String identifier) {
		session.execute(QueryBuilder.deleteFrom(settings.getKeyspaceName(), settings.getTableName())
				.whereColumn(COLUMN_SESSION_ID).isEqualTo(literal(identifier))
				.build());

		LOGGER.debug("Deleted data for session '{}'", identifier);
	}

	@Override
	protected void addPersistedPage(String identifier, IManageablePage page) {
		if (page instanceof SerializedPage == false) {
			throw new WicketRuntimeException("CassandraDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;

		session.execute(QueryBuilder
				.insertInto(settings.getKeyspaceName(), settings.getTableName())
				.value(COLUMN_SESSION_ID, literal(identifier))
				.value(COLUMN_PAGE_ID, literal(serializedPage.getPageId()))
				.value(COLUMN_PAGE_SIZE, literal(serializedPage.getData().length))
				.value(COLUMN_PAGE_TYPE, literal(serializedPage.getPageType()))
				.value(COLUMN_DATA, literal(ByteBuffer.wrap(serializedPage.getData()), TypeCodecs.BLOB))
				.usingTtl((int) settings.getRecordTtl().toSeconds())
				.build()
			);

		LOGGER.debug("Inserted data for session '{}' and page id '{}'", identifier, page.getPageId());
	}

	@Override
	public void destroy() {
		if (session != null) {
			session.close();
		}
		super.destroy();
	}

	/**
	 * Pages are always serialized, so versioning is supported.
	 */
	@Override
	public boolean supportsVersioning()
	{
		return true;
	}

	/**
	 * Creates the table where the data will be stored if it doesn't exists already
	 *
	 * @param keyspaceName     The name of the keyspace where to create the table
	 * @param keyspaceMetadata The keyspace metadata. May be {@code null} if this is the first usage
	 *                         of this store with these settings
	 */
	protected void createTableIfNecessary(String keyspaceName, KeyspaceMetadata keyspaceMetadata) {
		String tableName = settings.getTableName();
		Optional<TableMetadata> tableMetadata = keyspaceMetadata == null ? null : keyspaceMetadata.getTable(tableName);
		if (tableMetadata.isEmpty()) {
			session.execute(
				String.format(
					"CREATE TABLE %s.%s (" +
						"%s varchar," +
						"%s int," +
						"%s int," +
						"%s varchar," +
						"%s blob," +
						"PRIMARY KEY (%s, %s)" +
						");", keyspaceName, tableName, COLUMN_SESSION_ID, COLUMN_PAGE_ID, COLUMN_PAGE_SIZE, COLUMN_PAGE_TYPE, COLUMN_DATA, COLUMN_SESSION_ID, COLUMN_PAGE_ID));
			LOGGER.debug("Created table with name {}.{}", keyspaceName, tableName);
		}
	}

	/**
	 * Creates the Cassandra keyspace where the data will be stored if it doesn't exists already
	 *
	 * @param keyspaceName The name of the keyspace
	 * @param metadata     The cluster metadata
	 * @return The keyspace metadata. May be {@code null} if the the metadata doesn't exists yet.
	 */
	protected KeyspaceMetadata createKeyspaceIfNecessary(String keyspaceName, Metadata metadata)
	{
		Optional<KeyspaceMetadata> keyspaceMetadata = metadata.getKeyspace(keyspaceName);
		if (keyspaceMetadata.isEmpty()) {
			session.execute(
					String.format("CREATE KEYSPACE %s WITH replication " +
							"= {'class':'SimpleStrategy', 'replication_factor':3};", keyspaceName));
			LOGGER.debug("Created keyspace with name {}", keyspaceName);
		}
		return keyspaceMetadata.orElse(null);
	}

	@Override
	public Set<String> getSessionIdentifiers() {
		Set<String> identifiers = new HashSet<>();
		session.execute(QueryBuilder.selectFrom(settings.getKeyspaceName(), settings.getTableName())
				.column(COLUMN_SESSION_ID)
				.build())
			.forEach(row -> { identifiers.add(row.getString(COLUMN_SESSION_ID)); });

		return identifiers;
	}

	@Override
	public Bytes getTotalSize() {
		ResultSet rows = session.execute(QueryBuilder.selectFrom(settings.getKeyspaceName(), settings.getTableName())
			.function("sum", Selector.column(COLUMN_PAGE_SIZE))
			.build());
		Row row = rows.one();
		if (row != null) {
			return Bytes.bytes(row.getInt(0));
		}

		return null;
	}

	@Override
	public List<IPersistedPage> getPersistedPages(String contextIdentifier)
	{
		List<IPersistedPage> pages = new LinkedList<>();
		session.execute(QueryBuilder.selectFrom(settings.getKeyspaceName(), settings.getTableName())
				.columns(COLUMN_PAGE_ID, COLUMN_PAGE_SIZE, COLUMN_PAGE_TYPE)
				.whereColumn(COLUMN_SESSION_ID).isEqualTo(literal(contextIdentifier))
				.build())
			.forEach(row -> { pages.add(new PersistedPage(row.getInt(COLUMN_PAGE_ID), row.getString(COLUMN_PAGE_TYPE), row.getInt(COLUMN_PAGE_SIZE))); });

		return pages;
	}
}