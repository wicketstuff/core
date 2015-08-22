(import '(org.wicketstuff.console.examples.hibernate Book))

(let [sf (.getSessionFactory user/application)]
  (with-open [s (.openSession sf)]
    (let [hql "from Book"
          books (.. s (createQuery hql) list)]
      (doseq [book books]
        (println book)))))
