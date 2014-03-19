(import '(org.apache.log4j Logger Level))

(.. (Logger/getLogger "org.apache.wicket") 
  (setLevel Level/DEBUG))
"done"