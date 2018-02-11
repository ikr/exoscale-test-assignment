(ns jobs.views
  (:require [re-frame.core :as r]
            [clojure.string :as str]
            [jobs.routes :refer [url-for]]
            [jobs.subs :as subs]
            [jobs.config :as config]))

(defn jobs-panel []
  [:h1 "All jobs"])

(defn current-job-panel []
  [:p "Current job"])

(defn content []
  (let [active (r/subscribe [:active-panel])]
    (condp = @active
      :jobs-panel (jobs-panel)
      :current-job-panel (current-job-panel))))

(defn footer []
  [:footer.text-right {:style {:padding-top "100px"}}
   (if config/debug?
     [:button.btn.btn-sm.btn-outline-info
      {:type "button" :on-click #(r/dispatch [:run-tests])}
      "Run tests"]
     "")])

(defn layout []
  [:div (content) (footer)])
