(ns datafrisk.ui-card
  (:require [devcards.core]
            [datafrisk.view :refer [Root]])
  (:require-macros [devcards.core :as dc :refer [defcard]]))

(defcard modifiable-data
         "When the data you are watching is swappable, you can edit it."
         (Root
           (atom 3)
           "root"
           (atom {})))

(defcard modifiable-nested-data
         "When the data you are watching is nested in a swappable, you can edit the values."
         (Root
           (atom {:foo 2
                  3    "bar"})
           "root"
           (atom {})))

(defcard data-types
         (Root
           {:a             "I'm a string"
            :b             :imakeyword
            :c             [1 2 3]
            :d             '(1 2 3)
            :e             #{1 2 3}
            :f             (clj->js {:i-am "an-object"})
            "g"            "String key"
            0              nil
            "not a number" js/NaN}
           "root"
           (atom {})))

(defcard- first-level-expanded
          (Root
            {:a "a"
             :b [1 2 3]
             :c :d}
            "root"
            (atom {:data-frisk {"root" {:metadata-paths {[] {:expanded? true}}}}})))

(defcard second-level-expanded
         (Root {:a "a"
                :b [1 2 3]
                :c :d}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]   {:expanded? true}
                                                            [:b] {:expanded? true}}}}})))

(defcard empty-collections
         (Root {:set  #{}
                :vec  []
                :list '()}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[] {:expanded? true}}}}})))

(defcard nil-in-collections
         (Root {:set  #{nil}
                :vec  [nil]
                :list '(nil nil)}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]      {:expanded? true}
                                                            [:set]  {:expanded? true}
                                                            [:vec]  {:expanded? true}
                                                            [:list] {:expanded? true}}}}})))

(defcard list-of-maps
         (Root {:my-list '("a string" [1 2 3] {:name "Jim" :age 10} {:name "Jane" :age 7})}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]         {:expanded? true}
                                                            [:my-list] {:expanded? true}}}}})))

(defcard list-of-lists
         (Root '(1 (1 2 3))
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]         {:expanded? true}
                                                            [:my-list] (:expanded? true)}}}})))

(defcard set-with-list
         (Root #{1 '(1 2 3) [4 5 6]}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]         {:expanded? true}
                                                            [:my-list] (:expanded? true)}}}})))

(defcard meta-data
         (Root {:a 1 :b 2}
               "root"
               (atom {:data-frisk {"root" {:metadata-paths {[]   {:error     "bad stuff"
                                                                  :expanded? true}
                                                            [:a] {:error "very bad stuff"}}}}})))