def pf = application.resourceSettings.propertiesFactory
def cache = pf.cache

cache.each {key, value ->
  println key
}

pf.clearCache()
println "cleared"
