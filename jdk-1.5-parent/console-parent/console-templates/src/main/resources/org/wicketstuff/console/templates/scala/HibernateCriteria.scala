import org.wicketstuff.console.examples.hibernate.Book

val sf = application.getSessionFactory()
val s = sf.openSession()
val books = s.createCriteria(classOf[Book]).list() :: List()
for (b <- books) {
  println(b)
}
s.close()
