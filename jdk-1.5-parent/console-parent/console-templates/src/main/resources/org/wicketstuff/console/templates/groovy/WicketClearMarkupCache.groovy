def markupCache = org.apache.wicket.markup.MarkupCache.get()
def cache = markupCache.getMarkupCache()
cache.keys.each {
    println "${it}"
}
markupCache.clear()
println "cleared"