pageSize = page.getSizeInBytes() >> 10
print "Page Size ", pageSize, "KB"
sessionSize = page.getSession().getSizeInBytes() >> 10
print "Session Size ", sessionSize, "KB"
