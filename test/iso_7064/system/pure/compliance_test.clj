(ns iso-7064.system.pure.compliance_test
  "Check the iso 7064 pure system implementation for Compliance
  with the standard using examples from the standard itself."
  (:require [clojure.test :refer :all]
            [iso-7064.test_util :refer :all]
            [iso-7064.core :refer :all]))

(deftest check-examples-from-standard
  (testing "MOD 11-2 example"
    (is (= "X" (calc-check-character mod-11-2 "079")))
    (is (valid? mod-11-2 "079X"))

    (is (= "0" (calc-check-character mod-11-2 "0794")))
    (is (valid? mod-11-2 "07940"))

    (is (= "44" (calc-check-character mod-97-10 "794")))
    (is (valid? mod-97-10 "79444"))

    (is (= "3W" (calc-check-character mod-1271-36 "ISO79")))
    (is (valid? mod-1271-36 "ISO793W"))))

(deftest check-hand-calculated-examples
  (testing "MOD 11-2 examples"
    (is (= "6" (calc-check-character mod-11-2 "1794")))
    (is (valid? mod-11-2 "17946"))

    (is (= "8" (calc-check-character mod-11-2 "1734")))
    (is (valid? mod-11-2 "17348"))

    (is (= "Y" (calc-check-character mod-37-2 "G123489654321")))
    (is (valid? mod-37-2 "G123489654321Y"))

    (is (= "01" (calc-check-character mod-97-10 "901809")))
    (is (valid? mod-97-10 "90180901"))

    (is (= "BM" (calc-check-character mod-661-26 "BAISDLAFK")))
    (is (valid? mod-661-26 "BAISDLAFKBM"))))
