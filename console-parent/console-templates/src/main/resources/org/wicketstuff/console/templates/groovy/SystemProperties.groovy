def p = System.properties
p.propertyNames().each {
  println it + "=" + p.getProperty(it)
}