from org.wicketstuff.console.examples.hibernate import Book

s = application.getSessionFactory().openSession()
books = s.createCriteria(Book).list()
for book in books:
    print book
s.close()
