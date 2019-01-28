package org.wicketstuff.datastores.cassandra;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

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
	 * The Cassandra cluster
	 */
	private final Cluster cluster;

	/**
	 * The connection to the cluster
	 */
	private final Session session;

	/**
	 * The various settings
	 */
	private final ICassandraSettings settings;

	/**
	 * Constructor.
	 *
	 * Creates a Cluster based on the contact points provided
	 * by the passed settings
	 *
	 * @param settings The various settings
	 */
	public CassandraDataStore(String applicationName, ICassandraSettings settings)
	{
		this(applicationName, createCluster(settings), settings);
	}

	private static Cluster createCluster(ICassandraSettings settings)
	{
		Args.notNull(settings, "settings");

		List<String> contactPoints = settings.getContactPoints();
		if (contactPoints == null || contactPoints.size() == 0)
		{
			throw new IllegalArgumentException("At least one contact point must be provided" +
					"to be able to connect to Cassandra. See ICassandraSettings#getContactPoints.");
		}

		String[] contactPointsAsArray = contactPoints.toArray(new String[contactPoints.size()]);

		Cluster cluster = Cluster.builder()
				.addContactPoints(contactPointsAsArray).build();

		return cluster;
	}

	/**
	 * Constructor.
	 *
	 * Initializes the connection to the Cassandra cluster and creates
	 * the keyspace and/or table if necessary.
	 *
	 * @param cluster  The Cassandra cluster
	 * @param settings The various settings
	 */
	public CassandraDataStore(String applicationName, Cluster cluster, ICassandraSettings settings)
	{
		super(applicationName);
		
		this.cluster = Args.notNull(cluster, "cluster");
		this.settings = Args.notNull(settings, "settings");

		Metadata metadata = cluster.getMetadata();

		if (LOGGER.isInfoEnabled())
		{
			LOGGER.info("Connected to cluster: {}", metadata.getClusterName());

			for (Host host : metadata.getAllHosts())
			{
				LOGGER.info("Datatacenter: {}; Host: {}; Rack: {}",
						new Object[]{host.getDatacenter(), host.getAddress(), host.getRack()});
			}
		}

		session = cluster.connect();

		String keyspaceName = settings.getKeyspaceName();
		KeyspaceMetadata keyspaceMetadata = createKeyspaceIfNecessary(keyspaceName, metadata);

		createTableIfNecessary(keyspaceName, keyspaceMetadata);

		LOGGER.info("Data will be stored in table '{}' in keyspace '{}'.", settings.getTableName(), keyspaceName);
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int pageId) {
		Select.Where dataSelect = QueryBuilder
			.select(COLUMN_PAGE_TYPE, COLUMN_DATA)
			.from(settings.getKeyspaceName(), settings.getTableName())
			.where(QueryBuilder.eq(COLUMN_SESSION_ID, sessionIdentifier))
			.and(QueryBuilder.eq(COLUMN_PAGE_ID, pageId));
		
		ResultSet rows = session.execute(dataSelect);
		Row row = rows.one();
		if (row != null)
		{
			String pageType = row.getString(COLUMN_PAGE_TYPE);
			
			ByteBuffer data = row.getBytes(COLUMN_DATA);
			byte[] bytes = new byte[data.remaining()];
			data.get(bytes);
			
			LOGGER.debug("Got data for session '{}' and page id '{}'", sessionIdentifier, pageId);

			return new SerializedPage(pageId, pageType, bytes);
		}
		
		return null;
	}

	@Override
	protected void removePersistedPage(String identifier, IManageablePage page) {
		Delete.Where delete = QueryBuilder
				.delete()
				.all()
				.from(settings.getKeyspaceName(), settings.getTableName())
				.where(QueryBuilder.eq(COLUMN_SESSION_ID, identifier))
				.and(QueryBuilder.eq(COLUMN_PAGE_ID, page.getPageId()));
		session.execute(delete);

		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", identifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String identifier) {
		Delete.Where delete = QueryBuilder
				.delete()
				.all()
				.from(settings.getKeyspaceName(), settings.getTableName())
				.where(QueryBuilder.eq(COLUMN_SESSION_ID, identifier));
		session.execute(delete);

		LOGGER.debug("Deleted data for session '{}'", identifier);
	}

	@Override
	protected void addPersistedPage(String identifier, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("CassandraDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;
		
		Insert insert = QueryBuilder
				.insertInto(settings.getKeyspaceName(), settings.getTableName())
				.using(QueryBuilder.ttl((int) settings.getRecordTtl().seconds()))
				.values(new String[]{COLUMN_SESSION_ID, COLUMN_PAGE_ID, COLUMN_PAGE_SIZE, COLUMN_PAGE_TYPE, COLUMN_DATA},
						new Object[]{identifier, serializedPage.getPageId(), serializedPage.getData().length, serializedPage.getPageType(), ByteBuffer.wrap(serializedPage.getData())});
		session.execute(insert);

		LOGGER.debug("Inserted data for session '{}' and page id '{}'", identifier, page.getPageId());
	}

	@Override
	public void destroy()
	{
		if (session != null)
		{
			session.close();
		}
		
		if (cluster != null)
		{
			cluster.close();
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
	protected void createTableIfNecessary(String keyspaceName, KeyspaceMetadata keyspaceMetadata)
	{
		String tableName = settings.getTableName();
		TableMetadata tableMetadata = keyspaceMetadata != null ? keyspaceMetadata.getTable(tableName) : null;
		if (tableMetadata == null)
		{
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
		KeyspaceMetadata keyspaceMetadata = metadata.getKeyspace(keyspaceName);
		if (keyspaceMetadata == null)
		{
			session.execute(
					String.format("CREATE KEYSPACE %s WITH replication " +
							"= {'class':'SimpleStrategy', 'replication_factor':3};", keyspaceName));
			LOGGER.debug("Created keyspace with name {}", keyspaceName);
		}
		return keyspaceMetadata;
	}
	
	@Override
	public Set<String> getSessionIdentifiers()
	{
		Select.Where dataSelect = QueryBuilder
			.select(COLUMN_SESSION_ID)
			.from(settings.getKeyspaceName(), settings.getTableName())
			.where();
		
		Set<String> identifiers = new HashSet<>();
		session.execute(dataSelect).forEach(row -> { identifiers.add(row.getString(COLUMN_SESSION_ID)); });
		
		return identifiers; 
	}
	
	@Override
	public Bytes getTotalSize()
	{
		Select.Where dataSelect = QueryBuilder
			.select(QueryBuilder.sum(COLUMN_PAGE_SIZE))
			.from(settings.getKeyspaceName(), settings.getTableName())
			.where();

		ResultSet rows = session.execute(dataSelect);
		Row row = rows.one();
		if (row != null)
		{
			return Bytes.bytes(row.getInt(0)); 
		}

		return null;
	}
	
	@Override
	public List<IPersistedPage> getPersistedPages(String contextIdentifier)
	{
		Select.Where dataSelect = QueryBuilder
			.select(COLUMN_PAGE_ID, COLUMN_PAGE_SIZE, COLUMN_PAGE_TYPE)
			.from(settings.getKeyspaceName(), settings.getTableName())
			.where(QueryBuilder.eq(COLUMN_SESSION_ID, contextIdentifier));
		
		List<IPersistedPage> pages = new LinkedList<>();
		session.execute(dataSelect).forEach(row -> { pages.add(new PersistedPage(row.getInt(COLUMN_PAGE_ID), row.getString(COLUMN_PAGE_TYPE), row.getInt(COLUMN_PAGE_SIZE))); });
		
		return pages; 
	}
}