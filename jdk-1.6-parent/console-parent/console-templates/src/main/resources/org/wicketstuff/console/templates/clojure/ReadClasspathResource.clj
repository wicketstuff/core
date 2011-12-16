(let [clazz (.getClass user/page)
      cl (.getClassLoader clazz)
      path "org/apache/wicket/Application.properties"
      res (.getResource cl path)]
  (println (slurp res)))
