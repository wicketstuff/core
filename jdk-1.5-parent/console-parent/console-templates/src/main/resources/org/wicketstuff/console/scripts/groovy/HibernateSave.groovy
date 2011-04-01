import org.wicketstuff.console.examples.hibernate.Book
def sf = application.sessionFactory
def s = sf.openSession()
def tx = s.beginTransaction()
def book = new Book(title:"Wicket Console", author:"Me")
s.save(book)
tx.commit()
s.close()
book