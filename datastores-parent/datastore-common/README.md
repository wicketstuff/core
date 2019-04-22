wicketstuff-datastore-common
===========================

Provides SessionQuotaManagingDataStore - an implementation of Wicket's IPageStore that should be used as a wrapper around another IPageStore.
SessionQuotaManagingDataStore uses IStoreSettings#getMaxSizePerSession() to manage the amount of memory a user of the application can
store in the wrapped IPageStore
