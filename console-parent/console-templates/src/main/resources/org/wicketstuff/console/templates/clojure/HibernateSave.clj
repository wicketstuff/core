(import '(org.wicketstuff.console.examples.hibernate Book))

(let [sf (.getSessionFactory user/application)]
  (with-open [s (.openSession sf)]
    (let [b (Book. "Wicket Console" "Peter")
          tx (.beginTransaction s)]
        (.save s b)
        (.commit tx)
        (println b))))
