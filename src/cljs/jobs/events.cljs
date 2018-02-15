(ns jobs.events
  (:require [clojure.string :as str]
            [re-frame.core :as r]
            [ajax.core :as ajax]
            [jobs.querystring :as qs]
            [jobs.test :as test]))

(defn empty-job [] {:company "" :title "" :keywords []})

(r/reg-event-db
 :init-db
 (fn [_ _]
   {:active-panel :jobs-panel
    :jobs-fetch :not-asked
    :jobs {}
    :current-job-id nil
    :current-job (empty-job)
    :current-job-saving false
    :current-job-error nil}))

(r/reg-event-fx
  :fetch-jobs
  (fn [{:keys [db]} _]
    {:db (assoc db :active-panel :jobs-panel :jobs-fetch :loading)
     :http-xhrio {:method :get
                  :uri "/jobs"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:fetch-jobs-success]
                  :on-failure [:fetch-jobs-failure]}}))

(r/reg-event-db
  :fetch-jobs-success
  (fn [db [_ result]]
    (assoc db :jobs-fetch :success :jobs (:jobs result))))

(r/reg-event-db
  :fetch-jobs-failure
  (fn [db _]
    (assoc db :jobs-fetch :failure)))

(r/reg-event-db
 :edit-new-job
 (fn [db _]
   (assoc db
          :active-panel :current-job-panel
          :current-job-saving false
          :current-job-error nil
          :current-job-id nil
          :current-job (empty-job))))

(r/reg-event-db
 :edit-existing-job
 (fn [db [_ id]]
   (assoc db
          :active-panel :current-job-panel
          :current-job-saving false
          :current-job-error nil
          :current-job-id id
          :current-job (get-in db [:jobs (-> id str keyword)]))))

(r/reg-event-db
 :change-current-job
 (fn [db [_ key value]]
   (assoc-in db [:current-job key] value)))

(defn generic-error-message [] "Oops, couldn't make it, sorry. Should we try again?")

(r/reg-event-fx
  :post-job
  (fn [{:keys [db]} [_ current-job]]
    {:db (assoc db :current-job-saving true)
     :http-xhrio {:method :post
                  :uri "/jobs"
                  :body (qs/encode current-job)
                  :format (ajax/url-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:fetch-jobs]
                  :on-failure [:current-job-failure]}}))
(r/reg-event-fx
  :delete-job
  (fn [{:keys [db]} [_ id]]
    {:db db
     :http-xhrio {:method :delete
                  :uri (str/join ["/jobs/" id])
                  :body ""
                  :format (ajax/url-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:fetch-jobs]
                  :on-failure [:delete-failure]}}))

(r/reg-event-db
  :current-job-failure
  (fn [db _]
    (assoc db :current-job-error (generic-error-message))))

(r/reg-event-db
  :delete-failure
  (fn [db _]
    (do
      (.alert js/window (generic-error-message))
      db)))

(r/reg-event-db
 :run-tests
 (fn [db _]
   (do
     (test/run)
     db)))
