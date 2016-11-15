(ns iso-7064.system.pure
  (:require [iso-7064.system :refer [Iso7064System]]))

(defn- get-char-value [alphabet ch]
  (let [i (.indexOf (seq alphabet) ch)]
    (if (< i 0)
      (ex-info (str "Character '" ch "' not allowed in alphabet " alphabet) {})
      i)))

(defn- pure-checksum [alphabet modulus rad s]
  (->> (seq s)
       (map (partial get-char-value alphabet))
       (reduce (fn [Pj char-val] (* (+ (mod Pj modulus) char-val) rad)) 0)))

(defn final-step-two [checksum modulus rad]
  (let [Sj (mod checksum modulus)
        Pj+1 (mod (* Sj rad) modulus)
        v (mod (- (inc modulus) Pj+1) modulus)]
    [(quot v rad) (mod v rad)]))

(defn- final-step-one [modulus checksum]
  [(mod (- 1 checksum) modulus)])

(defrecord PureSystem [alphabet modulus rad two-check-characters?]
  Iso7064System
  (-valid? [_ s]
    (let [checksum (pure-checksum alphabet modulus rad s)]
      (= (mod (quot checksum rad) modulus) 1)))
  (-calc-check-character [_ s]
    (let [checksum (pure-checksum alphabet modulus rad s)]
      (->>
        (if two-check-characters?
          (final-step-two checksum modulus rad)
          (final-step-one modulus checksum))
        (map #(get (vec (seq alphabet)) %))
        (apply str)))))
