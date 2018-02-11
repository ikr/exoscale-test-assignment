(ns jobs.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as r]
            [jobs.events :as events]
            [jobs.routes :as routes]
            [jobs.views :as views]
            [jobs.config :as config]
            [day8.re-frame.http-fx]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (r/clear-subscription-cache!)
  (reagent/render [views/layout] (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (r/dispatch [:init-db])
  (r/dispatch [:fetch-jobs])
  (mount-root))
