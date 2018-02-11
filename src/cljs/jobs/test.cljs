(ns jobs.test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest test-numbers
  (is (= 1 1)))

(defn run [] (run-tests))
