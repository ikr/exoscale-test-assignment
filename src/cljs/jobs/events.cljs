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
    :jobs []
    :current-job-fetch :not-asked
    :current-job nil}))

(r/reg-event-db
 :run-tests
 (fn [db _]
   (test/run)
   db))
