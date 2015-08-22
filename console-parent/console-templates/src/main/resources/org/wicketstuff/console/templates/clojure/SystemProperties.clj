(let [p (System/getProperties)]
  (doseq [prop (.stringPropertyNames p)]
    (println prop "=" (.getProperty p prop))))
