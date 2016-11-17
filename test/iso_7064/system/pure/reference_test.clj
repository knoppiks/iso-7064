(ns iso-7064.system.pure.reference-test
  "Checks the iso 7064 pure system implementation referencing a java
  implementation by danieltwagner https://github.com/danieltwagner/iso7064."
  (:require [clojure.test :refer :all]
            [iso-7064.core :refer :all]
            [iso-7064.test_util :refer :all]
            [clojure.test.check.generators :as gen]))

(deftest check-example-cases
  (->> pure-systems
       (mapv
         (fn [[key {:keys [impl ref examples]}]]
           (testing (name key)
             (->> examples
                  (mapv #(is (= (calc-check-character impl %)
                                (calc-check-character ref %))))))))))

(deftest generated-cases
  (->> pure-systems
       (mapv
         (fn [[key {:keys [impl ref allowed-chars]}]]
           (testing (name key)
             (->> (gen/sample (gen-string-from allowed-chars) 100)
                  (mapv #(is (= (calc-check-character impl %)
                                (calc-check-character ref %))))))))))
