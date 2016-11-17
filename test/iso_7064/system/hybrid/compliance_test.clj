(ns iso-7064.system.hybrid.compliance_test
  "Check the iso 7064 hybrid system implementation for Compliance
  with the standard using examples from the standard itself."
  (:require [clojure.test :refer :all]
            [iso-7064.test_util :refer :all]
            [iso-7064.core :refer :all]))

(def ^:private mod-11*10
  (hybrid-system "0123456789"))

(deftest check-examples-from-standard
  (testing "MOD 11,10"
    (is (= "5" (calc-check-character mod-11*10 "0794")))
    (is (valid? mod-11*10 "07945"))))
