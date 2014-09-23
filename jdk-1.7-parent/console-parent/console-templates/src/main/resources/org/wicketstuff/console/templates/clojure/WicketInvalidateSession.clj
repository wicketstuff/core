(let [session (.getSession user/page)]
  (.invalidate session))
