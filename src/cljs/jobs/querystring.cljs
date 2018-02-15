(ns jobs.querystring
  (:require [clojure.string :as str]))

(defn e [x] (.encodeURIComponent js/window x))

(defn param [name value]
  (str/join [name "=" (e value)]))

(defn encode-keywords [job]
  (str/join "&" (map (partial param "keywords") (:keywords job))))


(defn encode [job]
  (str/join
   "&"
   [((comp (partial param "company") :company) job)
    ((comp (partial param "title") :title) job)
    (encode-keywords job)]))
