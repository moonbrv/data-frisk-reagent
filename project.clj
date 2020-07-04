(defproject data-frisk-rum "0.0.6"
  :description "Explore data in FE Rum application, implementation of data-frisk-reagent for Rum"
  :url "https://github.com/moonbrv/data-frisk-rum"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :min-lein-version "2.7.1"
  :dependencies [[rum "0.12.2"]]
  :plugins [[lein-figwheel "0.5.20"]
            [lein-doo "0.1.11"]
            [lein-ancient "0.6.15"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]
  :source-paths ["src"]

  :figwheel {:http-server-root "public"
             :server-port 3999}

  :aliases {"testing" ["do" ["clean"] ["doo" "chrome-headless" "test" "once"]]}

  :profiles {:dev {:dependencies   [[org.clojure/clojure "1.10.1"]
                                    [org.clojure/clojurescript "1.10.520"]
                                    [doo "0.1.11"]
                                    [com.cemerick/piggieback "0.2.2"]
                                    [figwheel-sidecar "0.5.20"]
                                    [devcards "0.2.7" :exclusions [[cljsjs/react]]]]
                   :source-paths   ["src" "devcards"]
                   :resource-paths ["devresources"]
                   :cljsbuild      {:builds [{:id           "dev"
                                              :source-paths ["src" "dev"]
                                              :figwheel     {:on-jsload "datafrisk.demo/on-js-reload"}
                                              :compiler     {:main       "datafrisk.demo"
                                                             :asset-path "js/out"
                                                             :output-to  "resources/public/js/main.js"
                                                             :output-dir "resources/public/js/out"}}
                                             {:id           "cards"
                                              :source-paths ["src" "devcards"]
                                              :figwheel     {:devcards true}
                                              :compiler     {:main       "datafrisk.cards"
                                                             :asset-path "js/out-cards"
                                                             :output-to  "resources/public/js/cards.js"
                                                             :output-dir "resources/public/js/out-cards"}}
                                             {:id           "test"
                                              :source-paths ["src" "test"]
                                              :compiler     {:output-to     "resources/public/js/compiled/test.js"
                                                             :main          datafrisk.test-runner
                                                             :optimizations :none}}
                                             {:id           "demo-site"
                                              :source-paths ["src" "dev"]
                                              :compiler     {:main          "datafrisk.demo"
                                                             :optimizations :advanced
                                                             :output-to     "demo-site/main.js"
                                                             :output-dir    "demo-site/out"}}]}}}
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :deploy-repositories {"releases" {:url "https://repo.clojars.org" :creds :gpg}})
