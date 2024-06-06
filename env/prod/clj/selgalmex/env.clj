(ns selgalmex.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[selgalmex started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[selgalmex has shut down successfully]=-"))
   :middleware identity})
