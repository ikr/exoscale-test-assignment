(ns jobs.views
  (:require [re-frame.core :as r]
            [clojure.string :as str]
            [jobs.views-jobs :as views-jobs]
            [jobs.views-current-job :as views-current-job]
            [jobs.routes :refer [url-for]]
            [jobs.subs :as subs]
            [jobs.config :as config]))

(defn content []
  (let [active (r/subscribe [:active-panel])]
    (condp = @active
      :jobs-panel (views-jobs/panel)
      :current-job-panel (views-current-job/panel))))

(defn footer []
  [:footer.text-right {:style {:padding-top "100px"}}
   (if config/debug?
     (list
      [:button.btn.btn-sm.btn-outline-info
       {:type "button"
        :on-click #(r/dispatch [:fetch-jobs])
        :key "b1"}
       "Refresh"]
      " "
      [:button.btn.btn-sm.btn-outline-info
       {:type "button"
        :on-click #(r/dispatch [:run-tests])
        :key "b2"}
       "Run tests"])
     "")])

(defn layout []
  [:div (content) (footer)])
