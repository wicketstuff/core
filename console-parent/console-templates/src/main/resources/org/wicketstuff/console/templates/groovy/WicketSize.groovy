def pageSize = page.sizeInBytes/1024
println "Page Size ${pageSize}KB"
def sessionSize = page.getSession().sizeInBytes/1024
println "Session Size ${sessionSize}KB"