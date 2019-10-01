(ns datafrisk.demo
  (:require [rum.core :as rum]
            [datafrisk.core :as d]))

(enable-console-print!)

(rum/defc Animals [data]
  [:div "Awesome animals"
   (into [:ul]
         (map-indexed (fn [i {:keys [animal age]}]
                        [:li {:key (str i)} (str animal ", " age " years old")])
                      (:animals data)))])

(defonce state (atom {:animals                     '({:animal "Monkey", :age 22222}
                                                      {:animal "Giraffe", :age 45}
                                                      {:animal "Zebra" :age 3})
                      :some-string                 "a"
                      :vector-with-map             [1 2 3 3 {:a "a" :b "b"}]
                      :a-set                       #{1 2 3}
                      :a-map                       {:x "x" :y "y" :z [1 2 3 4]}
                      :atom                        (atom {:x "x" :y "y" :z [1 2 3 4]})
                      :a-seq                       (seq [1 2])
                      :an-object                   (clj->js {:a "a"})
                      :this-is-a-very-long-keyword :g}))

(rum/defc App < rum/reactive [state]
  (let [state (rum/react state)]
    [:div
     (Animals state)
     (d/DataFriskShell
       ;; List of arguments you want to visualize
       state
       {:a :b :c :d :e :f})]))

(defn mount-app-element []
  (when-let [el (js/document.getElementById "app")]
    (rum/mount (App state) el)))

(defn ^:export main []
  (mount-app-element))

(defn on-js-reload []
  (mount-app-element))
