(import '(org.apache.wicket MarkupContainer))

(defn visitComponent [c level f]
  (f c level)
  (if (instance? MarkupContainer c)
    (if (> (.size c) 0)
      (dotimes [childnr (.size c)]
        (visitComponent (.get c childnr) (inc level) f)))))

(defn printComponent [c level]
  (dotimes [n level]
    (print "  "))
  (println c))

(visitComponent user/component 0 printComponent)
