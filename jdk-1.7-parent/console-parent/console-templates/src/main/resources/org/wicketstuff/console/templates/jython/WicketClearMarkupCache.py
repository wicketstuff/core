from org.apache.wicket.markup import MarkupCache
mc = MarkupCache.get()
cache = mc.getMarkupCache()
for key in cache.keys:
    print key
mc.clear()
print "cleared"
