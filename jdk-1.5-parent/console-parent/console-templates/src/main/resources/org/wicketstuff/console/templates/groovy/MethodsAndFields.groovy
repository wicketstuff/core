clazz = page.class

println "Methods"
clazz.methods.each {println it}

println "Fields"
clazz.fields.each {println it}