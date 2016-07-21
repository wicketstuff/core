def sf = application.sessionFactory
def s = sf.openSession()
def q = s.createQuery("""
	from Book
""")
result = q.list()
result.each {println it}
s.close()