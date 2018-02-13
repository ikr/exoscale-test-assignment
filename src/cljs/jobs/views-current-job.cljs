(ns jobs.views-current-job
  (:require [re-frame.core :as r]))

(defn panel []
  [:div
   [:h1 "New job"]
   [:p "Coming soon"]
   [:button.btn.btn-lg.btn-primary {:type "button"} "Create"]
   " "
   [:button.btn.btn-lg.btn-secondary
    {:type "button"
     :on-click #(r/dispatch [:fetch-jobs])}
    "Cancel"]])
