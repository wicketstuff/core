from java.lang import System
p = System.getProperties()
for e in p.entrySet():
   print e.getKey(), ":", e.getValue()