(ns selgalmex.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [selgalmex.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[selgalmex started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[selgalmex has shut down successfully]=-"))
   :middleware wrap-dev})
