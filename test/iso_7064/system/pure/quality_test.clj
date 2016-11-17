(ns iso-7064.system.pure.quality-test
  (:require [clojure.test :refer :all]
            [iso-7064.core :refer :all]
            [iso-7064.test_util :refer :all]
            [clojure.test.check.generators :as gen :refer [generate sample]]))

(def ^:private error-scenarios
  {:substitute-1
   (fn [allowed string]
     (let [char (generate (gen-char-from allowed))
           place (rand-int (count string))]
       (apply str (-> string (seq) (vec) (update place (constantly char))))))

   :transpose-d=1
   (fn [_ string]
     (let [place (rand-int (dec (count string)))
           strv (vec (seq string))]
       (apply str
         (-> strv
             (update place (constantly (get strv (inc place))))
             (update (inc place) (constantly (get strv place)))))))

   :transpose-d=2
   (fn [_ string]
     (let [place (rand-int (dec (dec (count string))))
           strv (vec (seq string))]
       (apply str
         (-> strv
             (update place (constantly (get strv (inc (inc place)))))
             (update (inc (inc place)) (constantly (get strv place)))))))

   :substitute-2
   (fn [allowed string]
     (apply str
       (-> string (seq) (vec)
           (update (rand-int (count string)) (constantly (generate (gen-char-from allowed))))
           (update (rand-int (count string)) (constantly (generate (gen-char-from allowed)))))))

   :circular-shift
   (fn [_ string]
     (apply str
       (loop [times (rand-int 10)
              strv (vec (seq string))]
         (if (> 1 times)
           strv
           (recur (dec times) (conj (butlast strv) (last strv)))))))})

(defn- undetected-modification
  "Checks whether a string is valid but its modification is not valid if both
  aren't equal, i.e. a modification was not detected by the system."
  [impl protected modified]
  #_(prn {:protected {:value protected :valid? (valid? impl protected)}
          :modified {:value modified :valid? (valid? impl modified)}})
  (and
    (valid? impl protected)
    (not (= protected modified))
    (valid? impl modified)))

(defn- test-single [impl modify-fn string]
  (let [check (calc-check-character impl string)
        protected (str string check)
        modified (modify-fn protected)]
    (when (undetected-modification impl protected modified)
      {:protected {:value protected :valid? (valid? impl protected)}
       :modified {:value modified :valid? (valid? impl modified)}})))

(defn- test-system [samples modify-fn [system {:keys [impl allowed-chars]}]]
  (let [modify-fn (partial modify-fn allowed-chars)
        undetected (->> (gen/sample (gen-string-from allowed-chars) samples)
                        (mapv (partial test-single impl modify-fn))
                        (remove nil?))]
    {system
     {:samples samples :num-undetected (count undetected)}}))

(defn- do-test [modify-fn samples]
  (->> pure-systems
       (map (partial test-system samples modify-fn))
       (apply merge)))

(defn test-all []
  (->> error-scenarios
       (map (fn [[key modify-fn]] {key (do-test modify-fn 1000)}))
       (apply merge)))

(comment
  (test-all))
