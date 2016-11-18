(defproject iso-7064 "0.1-SNAPSHOT"
  :description "Implementation of ISO 7064"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]

  :profiles {:dev
             {:source-paths ["dev"]
              :dependencies [[org.clojure/tools.namespace "0.2.4"]
                             [org.clojure/test.check "0.9.0"]
                             [de.knoppiks.sandbox/iso7064 "0.1-SNAPSHOT"]]
              :global-vars {*print-length* 20}}})
