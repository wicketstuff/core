(let [clazz (.getClass user/page)]
  (doseq [m (.getMethods clazz)] (println m))
  (doseq [f (.getFields clazz)] (println f)))
