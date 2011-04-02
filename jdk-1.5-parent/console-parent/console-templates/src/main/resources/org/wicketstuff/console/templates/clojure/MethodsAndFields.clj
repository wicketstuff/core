(import '(org.wicketstuff.console.engine ClojureEngine))

(let [bindings (ClojureEngine/getBindings)
      page (.get bindings "page")
      clazz (.getClass page)]
  (doseq [m (.getMethods clazz)] (println m))
  (doseq [f (.getFields clazz)] (println f)))