(import '(org.apache.wicket.markup MarkupCache))
(let [mc (MarkupCache/get)]
  (.clear mc))
"done"