(ns jobs.test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [jobs.querystring :as qs]))

(deftest test-qs-encode-keywords
  (is (=
       (qs/encode-keywords {:keywords ["x" "y"]})
       "keywords=x&keywords=y")))

(deftest test-qs-encode
  (is (=
       (qs/encode {:company "c"
                   :title "t"
                   :keywords ["x" "y"]})
       "company=c&title=t&keywords=x&keywords=y")))

(defn run [] (run-tests))
