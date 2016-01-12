(let [sf (.getSessionFactory user/application)
      stats (.getStatistics sf)]
  (.setStatisticsEnabled stats true)
  (doseq [stat (.split (.toString stats) ",")]
    (println stat)))
