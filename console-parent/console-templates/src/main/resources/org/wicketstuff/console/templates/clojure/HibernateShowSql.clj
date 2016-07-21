(import '(org.apache.log4j Logger Level))

(.. (Logger/getLogger "org.hibernate.SQL") 
  (setLevel Level/DEBUG))
"done"
