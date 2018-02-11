(ns jobs.events
  (:require [re-frame.core :as r]
            [ajax.core :as ajax]
            [jobs.db :as db]
            [jobs.test :as test]
            ))

(r/reg-event-db
 :init-db
 (fn [_ _]
   {:active-panel :jobs-panel
    :jobs-fetch :not-asked
    :jobs {}
    :jobs-fetch-error nil
    :current-job-fetch :not-asked
    :current-job nil}))

(r/reg-event-fx
  :fetch-jobs
  (fn [{:keys [db]} _]
    {:db (assoc db :jobs-fetch :loading)
     :http-xhrio {:method :get
                  :uri "/jobs"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:fetch-jobs-success]
                  :on-failure [:fetch-jobs-failure]}}))

(r/reg-event-db
  :fetch-jobs-success
  (fn [db [_ result]]
    (assoc db :jobs-fetch :success :jobs result)))

(r/reg-event-db
  :fetch-jobs-failure
  (fn [db [_ error]]
    (assoc db :jobs-fetch :failure :jobs-fetch-error error)))

(r/reg-event-db
 :run-tests
 (fn [db _]
   (test/run)
   db))
