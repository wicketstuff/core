/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.datastores.ignite;

import static org.apache.ignite.catalog.definitions.ColumnDefinition.column;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ignite.catalog.ColumnType;
import org.apache.ignite.catalog.definitions.TableDefinition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.sql.ResultSet;
import org.apache.ignite.sql.SqlRow;
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

/**
 * An IPageStore implementation that saves serialized pages in Apache Ignite
 * <p>
 * Ignite is not compatible with Java 9 - the following JVM parameters must be used:
 * <pre>
 * --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
 * --add-exports=java.base/sun.nio.ch=ALL-UNNAMED
 * </pre>
 *
 * @see https://stackoverflow.com/questions/50639471/using-ignite-on-jdk-9
 *
 * @author Alexey Prudnikov
 */
public class IgniteDataStore extends AbstractPersistentPageStore  implements IPersistentPageStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(IgniteDataStore.class);
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
	 * Apache Ignite instance
	 */
	private final IgniteClient ignite;

	// queries
	private final String pageSql;
	private final String pagesSql;
	private final String delPageSql;
	private final String delPagesSql;
	private final String addPageSql;
	private final String sessionsSql;
	private final String sizeSql;

	/**
	 * Constructor
	 *
	 * @param ignite The Apache Ignite instance
	 */
	public IgniteDataStore(String applicatioName, IIgniteSettings settings) {
		super(applicatioName);
		Args.notNull(settings, "settings");

		if (settings.getAddresses().isEmpty()) {
			throw new IllegalArgumentException("At least one address must be provided to be able to connect to Ignite. See IIgniteSettings#getAddresses.");
		}
		this.ignite = IgniteClient.builder()
			.addresses(settings.getAddresses().toArray(new String[]{}))
			.build();

		if (ignite.tables().table(settings.getTableName()) == null) {
			ignite.catalog().createTable(
				TableDefinition.builder(settings.getTableName())
					.primaryKey(COLUMN_SESSION_ID, COLUMN_PAGE_ID)
					.columns(
						//, COLUMN_PAGE_SIZE, COLUMN_PAGE_TYPE, COLUMN_DATA
						column(COLUMN_SESSION_ID, ColumnType.VARCHAR),
						column(COLUMN_PAGE_ID, ColumnType.INT32),
						column(COLUMN_PAGE_SIZE, ColumnType.INT32),
						column(COLUMN_PAGE_TYPE, ColumnType.VARCHAR),
						column(COLUMN_DATA, ColumnType.VARBINARY)
					)
					.build()
				);
		}
		pageSql = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s = ?"
			, COLUMN_PAGE_TYPE, COLUMN_DATA, settings.getTableName(), COLUMN_SESSION_ID, COLUMN_PAGE_ID);
		delPageSql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?"
			, settings.getTableName(), COLUMN_SESSION_ID, COLUMN_PAGE_ID);
		delPagesSql = String.format("DELETE FROM %s WHERE %s = ?"
			, settings.getTableName(), COLUMN_SESSION_ID);
		addPageSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
			, settings.getTableName()
			, COLUMN_SESSION_ID
			, COLUMN_PAGE_ID
			, COLUMN_PAGE_SIZE
			, COLUMN_PAGE_TYPE
			, COLUMN_DATA);
		sessionsSql = String.format("SELECT DISTINCT(%s) FROM %s"
			, COLUMN_SESSION_ID, settings.getTableName());
		pagesSql = String.format("SELECT %s, %s, %s FROM %s WHERE %s = ?"
			, COLUMN_PAGE_ID, COLUMN_PAGE_TYPE, COLUMN_PAGE_SIZE, settings.getTableName(), COLUMN_SESSION_ID);
		sizeSql = String.format("SELECT SUM(%s) FROM %s"
			, COLUMN_PAGE_SIZE, settings.getTableName());
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int pageId) {
		try (ResultSet<SqlRow> rs = ignite.sql().execute(null, pageSql, sessionIdentifier, pageId)) {
			if (!rs.hasRowSet()) {
				return null;
			}
			SqlRow row = rs.next();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Got Data for session '{}' and page id '{}'", sessionIdentifier, pageId);
			}
			return new SerializedPage(pageId, row.stringValue(COLUMN_PAGE_TYPE), row.bytesValue(COLUMN_DATA));
		}
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		ignite.sql().execute(null, delPageSql, sessionIdentifier, page.getPageId());
		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String sessionIdentifier) {
		ignite.sql().execute(null, delPagesSql, sessionIdentifier);
		LOGGER.debug("Deleted data for session '{}'", sessionIdentifier);
	}

	@Override
	protected void addPersistedPage(String sessionIdentifier, IManageablePage page) {
		if (page instanceof SerializedPage == false) {
			throw new WicketRuntimeException("IgniteDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage) page;

		ignite.sql().execute(null, addPageSql
				, sessionIdentifier
				, page.getPageId()
				, serializedPage.getData().length
				, serializedPage.getPageType()
				, serializedPage.getData());
		LOGGER.debug("Inserted page for session '{}' and page id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	public void destroy() {
		if (ignite != null) {
			ignite.close();
		}
	}

	@Override
	public boolean supportsVersioning() {
		return true;
	}

	@Override
	public Set<String> getSessionIdentifiers() {
		Set<String> sessions = new HashSet<>();
		try (ResultSet<SqlRow> rs = ignite.sql().execute(null, sessionsSql)) {
			while (rs.hasNext()) {
				SqlRow row = rs.next();
				sessions.add(row.stringValue(0));
			}
		}
		return sessions;
	}

	@Override
	public List<IPersistedPage> getPersistedPages(String contextIdentifier) {
		List<IPersistedPage> pages = new ArrayList<>();

		try (ResultSet<SqlRow> rs = ignite.sql().execute(null, pagesSql, contextIdentifier)) {
			while (rs.hasNext()) {
				SqlRow row = rs.next();
				pages.add(new PersistedPage(row.intValue(COLUMN_PAGE_ID), row.stringValue(COLUMN_PAGE_TYPE), row.intValue(COLUMN_PAGE_SIZE)));
			}
		}
		return pages;
	}

	@Override
	public Bytes getTotalSize() {
		long bytes = 0;

		try (ResultSet<SqlRow> rs = ignite.sql().execute(null, sizeSql)) {
			if (rs.hasNext()) {
				bytes = rs.next().intValue(0);
			}
		}
		return Bytes.bytes(bytes);
	}
}
