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

(def big-content-map {:x-named-key           "x"
                      :y-named-key           "y"
                      :z-named-key           [1 2 3 4]
                      :other-key-1           {:nested "data"}
                      :other-key-2           "data here"
                      :other-key-3           33
                      :key-4                 #{1 2 3}
                      :key-5                 "info string"
                      :key-with-long-name-6  "info string"
                      :key-with-long-name-7  "info string"
                      :key-with-long-name-8  "info string"
                      :key-with-long-name-9  "info string"
                      :key-with-long-name-10 "info string"
                      :key-with-long-name-11 "info string string string string string string string string string string string string"
                      :key-with-long-name-12 "info string string string string string string string string string"
                      :key-with-long-name-13 "info string"
                      :key-with-long-name-14 "info string"
                      :key-with-long-name-15 "info string"
                      :key-with-long-name-16 "info string"
                      :key-with-long-name-17 "info string string string string string string string string string string string string"
                      :key-with-long-name-18 "info string string string string string string string string string"
                      :key-with-long-name-19 "info string"
                      :key-with-long-name-20 "info string"
                      :key-with-long-name-21 "info string"
                      "this is a string key" (clj->js {:a "a" :b 2 :c 4 "e" "e"})})

(def state (atom {:animals                     '({:animal "Monkey", :age 22222}
                                                 {:animal "Giraffe", :age 45}
                                                 {:animal "Zebra" :age 3}
                                                 {:animal "Monkey" :age 2}
                                                 {:animal "Dog" :age 3})
                  :some-string                 "a"
                  :vector-with-map             [1 2 3 3 {:a "a" :b "b"}]
                  :a-set                       #{1 2 3}
                  :a-map                       big-content-map
                  "string key"                 big-content-map
                  :atom                        (atom {:x "x" :y "y" :z [1 2 3 4]})
                  :a-seq                       (seq [1 2])
                  :an-object                   (clj->js {:a "a"})
                  :this-is-a-very-long-keyword :g}))

(def state-second (atom "this is the value"))

(rum/defc App < rum/reactive [state]
  (let [state (rum/react state)
        other-state (rum/react state-second)]
    [:div
     (Animals state)
     [:input {:value     other-state
              :on-change #(->> %
                               .-target
                               .-value
                               (reset! state-second))}]
     (d/DataFriskShell
       ;; List of arguments you want to visualize
       state
       other-state
       {:a :b :c :d :e :f})]))

(defn mount-app-element []
  (when-let [el (js/document.getElementById "app")]
    (rum/mount (App state) el)))

(defn ^:export main []
  (mount-app-element))

(defn on-js-reload []
  (mount-app-element))
