(ns jobs.subs
  (:require [re-frame.core :as r]))

(r/reg-sub
  :active-panel
  (fn [db _]
    (:active-panel db)))

(r/reg-sub
  :jobs-fetch
  (fn [db _]
    (:jobs-fetch db)))

(r/reg-sub
  :jobs-list
  (fn [db _]
    (vals (:jobs db))))
