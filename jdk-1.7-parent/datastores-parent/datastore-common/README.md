wicketstuff-datastore-common
===========================

Provides SessionQuotaManagingDataStore - an implementation of Wicket's IDataStore that should be used as a wrapper around another IDataStore.
SessionQuotaManagingDataStore uses IStoreSettings#getMaxSizePerSession() to manage the amount of memory a user of the application can
store in the wrapped IDataStore
