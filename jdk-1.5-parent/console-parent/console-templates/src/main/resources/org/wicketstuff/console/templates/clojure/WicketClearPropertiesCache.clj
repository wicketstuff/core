(let [rs (.getResourceSettings user/application)
      pf (.getPropertiesFactory rs)]
  (.clearCache pf))
"done"