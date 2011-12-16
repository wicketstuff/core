(let [ci (.. user/page getSession getClientInfo)]
  (println (.getUserAgent ci))
  (println (.getProperties ci)))
