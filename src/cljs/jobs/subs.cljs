(ns jobs.subs
  (:require [re-frame.core :as r]))

(r/reg-sub
  :active-panel
  (fn [db _]
    (:active-panel db)))
