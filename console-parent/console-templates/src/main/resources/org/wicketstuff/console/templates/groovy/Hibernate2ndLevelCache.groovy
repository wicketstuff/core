//import model.Book

def cache = application.sessionFactory.cache

cache.evictEntityRegions()
//cache.evictEntityRegion(Book.class)
//cache.evictEntity(Book.class, 1L)

cache.evictCollectionRegions()
//cache.evictCollectionRegion("model.Book.authors")
//cache.evictCollection("model.Book.authors", 1L)

//cache.evictQueryRegions()
//cache.evictQueryRegion("org.hibernate.cache.QueryCache")
//cache.evictDefaultQueryRegion()
