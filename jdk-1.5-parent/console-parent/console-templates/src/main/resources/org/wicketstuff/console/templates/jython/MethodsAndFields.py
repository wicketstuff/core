clazz = page.getClass()

print "Methods"
for m in clazz.getMethods():
    print m

print "Fields"
for f in clazz.getFields():
    print f
