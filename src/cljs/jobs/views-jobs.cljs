(ns jobs.views-jobs
  (:require [re-frame.core :as r]
            [clojure.string :as str]))

(defn jobs-table-header-row []
  [:tr
   [:th.text-right "#"]
   [:th "Company"]
   [:th "Title"]
   [:th "Keywords"]
   [:th ""]])

(defn job-row [job]
  [:tr {:key (str/join ["r" (:id job)])}
   [:td.text-right (:id job)]
   [:td (:company job)]
   [:td (:title job)]
   [:td (str/join " " (:keywords job))]
   [:td.text-right
    [:button.btn.btn-sm.btn-secondary
     {:type "button"
      :on-click #(r/dispatch [:edit-existing-job (:id job)])
      :key "e"}
     "Edit"]
    " "
    [:button.btn.btn-sm.btn-warning
     {:type "button"
      :on-click #(r/dispatch [:delete-job (:id job)])
      :key "d"}
     "Delete"]]])

(defn jobs-table []
  (let [fetch-state (r/subscribe [:jobs-fetch])
        jobs-list (r/subscribe [:jobs-list])]
    (condp = @fetch-state
      :not-asked [:div.alert.alert-info "Initializing…"]
      :loading [:div.alert.alert-info "Fetching…"]
      :failure [:div.alert.alert-danger "Failed fetching jobs :("]
      :success [:table.table
                [:thead (jobs-table-header-row)]
                [:tbody (map job-row @jobs-list)]])))

(defn panel []
  [:div
   [:h1 "All jobs"]
   [:div.text-right
    {:style {:margin "12px"}}
    [:button.btn.btn-lg.btn-primary
     {:type "button"
      :on-click #(r/dispatch [:edit-new-job])}
     "New"]]
   (jobs-table)])
