(ns jobs.querystring
  (:require [clojure.string :as str]))

(defn e [x] (.encodeURIComponent js/window x))

(defn encode [job]
  (str/join "=" (map #(str/join ["keywords=", (e %)]) (:keywords job))))
