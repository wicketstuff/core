(println "Page Size: " (double (/ (.getSizeInBytes user/page) 1024)) "KB")
(println "Page Size: " (double (/ (.getSizeInBytes (.getSession user/page)) 1024)) "KB")
