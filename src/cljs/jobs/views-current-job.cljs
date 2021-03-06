(ns jobs.views-current-job
  (:require [re-frame.core :as r]
            [clojure.string :as str]))

(defn direct-input-change-handler [key]
  #(r/dispatch [:change-current-job
                key
                (-> % .-target .-value)]))

(defn split [s]
  (str/split (str/trim s) #"\s+"))

(defn splitting-input-change-handler [key]
  #(r/dispatch [:change-current-job
                key
                (-> % .-target .-value split)]))

(defn form []
  (let [current-job-saving (r/subscribe [:current-job-saving])
        current-job-id (r/subscribe [:current-job-id])
        current-job (r/subscribe [:current-job])]
   [:form
    {:on-submit #(do
                   (.preventDefault %)
                   (r/dispatch (if @current-job-id
                                 [:put-job @current-job-id @current-job]
                                 [:post-job @current-job])))}
    [:div.form-group
     [:label {:for "company"} "Company"]
     [:input#company.form-control
      {:required true
       :default-value (:company @current-job)
       :on-change (direct-input-change-handler :company)}]]
    [:div.form-group
     [:label {:for "title"} "Title"]
     [:input#title.form-control
      {:required true
       :default-value (:title @current-job)
       :on-change (direct-input-change-handler :title)}]]
    [:div.form-group
     [:label {:for "keywords"} "Keywords"]
     [:input#keywords.form-control
      {:required true
       :default-value (str/join " " (:keywords @current-job))
       :on-change (splitting-input-change-handler :keywords)}]
     [:small.form-text.text-muted
      "Please separate keywords with spaces."
      " "
      "You can use dashes to specify a single multi-part-keyword."]]
    [:button.btn.btn-lg.btn-primary
     {:type "submit" :disabled @current-job-saving}
     (if @current-job-id "Update" "Create")]
    " "
    [:button.btn.btn-lg.btn-secondary
     {:type "button"
      :on-click #(r/dispatch [:fetch-jobs])}
     "Cancel"]]))

(defn error-alert []
  (let [error (r/subscribe [:current-job-error])]
    (when @error [:div {:style {:margin-top "12px"}} [:div.alert.alert-danger @error]])))

(defn panel []
  [:div
   [:h1 "Edit job"]
   (form)
   (error-alert)])
