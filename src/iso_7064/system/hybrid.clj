(ns iso-7064.system.hybrid
  (:require [iso-7064.system :refer [Iso7064System -calc-check-character]]))

(defn- hybrid-checksum [alphabet m s]
  (->> (seq s)
       (map (fn [ch] (.indexOf (seq alphabet) ch)))
       (reduce (fn [p a]
                 (let [Sj|m (mod (+ p a) m)
                       Sj||m (if (= Sj|m 0) m Sj|m)]
                   (mod (* Sj||m 2) (inc m)))) m)))

(defrecord HybridSystem [alphabet m]
  Iso7064System
  (-valid? [_ s]
    (let [checksum (hybrid-checksum alphabet m s)]
      (= (/ checksum 2) 1)))
  (-calc-check-character [_ s]
    (let [checksum (hybrid-checksum alphabet m s)]
      (str (get alphabet (mod (- (inc m) checksum) m))))))
