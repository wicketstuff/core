(import '(org.wicketstuff.console.examples.hibernate Book))

(let [sf (.getSessionFactory user/application)]
  (with-open [s (.openSession sf)]
    (let [c (.createCriteria s Book)
          books (.list c)]
      (doseq [book books]
        (println book)))))
