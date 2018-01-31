(ns jobs.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
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
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel] (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (mount-root))
