(ns jobs.views-current-job
  (:require [re-frame.core :as r]))

(defn form []
  [:form
   [:div.form-group
    [:label {:for "company"} "Company"]
    [:input#company.form-control {:required true}]]
   [:div.form-group
    [:label {:for "title"} "Title"]
    [:input#title.form-control {:required true}]]
   [:div.form-group
    [:label {:for "keywords"} "Keywords"]
    [:input#keywords.form-control {:required true}]
    [:small.form-text.text-muted
     "Please separate keywords with spaces."
     " "
     "You can use dashes to specify a single multi-part-keyword."]]
   [:button.btn.btn-lg.btn-primary {:type "submit"} "Create"]
   " "
   [:button.btn.btn-lg.btn-secondary
    {:type "button"
     :on-click #(r/dispatch [:fetch-jobs])}
    "Cancel"]])

(defn panel []
  [:div
   [:h1 "New job"]
   (form)])
