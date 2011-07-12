from org.wicketstuff.console.examples.hibernate import Book
s = application.getSessionFactory().openSession()
tx = s.beginTransaction()
book = Book("Wicket Console", "Me")
s.save(book)
tx.commit()
s.close()
result = book