(ns jobs.views
  (:require [re-frame.core :as r]
            [clojure.string :as str]
            [jobs.routes :refer [url-for]]
            [jobs.subs :as subs]
            [jobs.config :as config]))

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
   [:td (str/join ", " (:keywords job))]
   [:td.text-right
    [:button.btn.btn-sm.btn-secondary
     {:type "button"
      :key "e"}
     "Edit"]
    " "
    [:button.btn.btn-sm.btn-warning
     {:type "button"
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

(defn jobs-panel []
  [:div
   [:h1 "All jobs"]
   [:div.text-right
    {:style {:margin "12px"}}
    [:button.btn.btn-lg.btn-primary
     {:type "button"}
     "New"]]
   (jobs-table)])

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
