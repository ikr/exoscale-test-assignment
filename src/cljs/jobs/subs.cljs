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

(r/reg-sub
  :current-job
  (fn [db _]
    (:current-job db)))

(r/reg-sub
  :current-job-id
  (fn [db _]
    (:current-job-id db)))

(r/reg-sub
  :current-job-saving
  (fn [db _]
    (:current-job-saving db)))

(r/reg-sub
  :current-job-error
  (fn [db _]
    (:current-job-error db)))
