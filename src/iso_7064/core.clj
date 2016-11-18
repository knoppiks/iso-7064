(ns iso-7064.core
  (:require [iso-7064.system :refer [Iso7064System -valid? -calc-check-character]]
            [iso-7064.system.hybrid :refer [->HybridSystem]]
            [iso-7064.system.pure :refer [->PureSystem]]))

(defn pure-system [alphabet modulus rad two-check-characters?]
  (->PureSystem alphabet modulus rad two-check-characters?))

(def mod-11-2
  (pure-system "0123456789X" 11 2 false))

(def mod-37-2
  (pure-system "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ*" 37 2 false))

(def mod-97-10
  (pure-system "0123456789" 97 10 true))

(def mod-661-26
  (pure-system "ABCDEFGHIJKLMNOPQRSTUVWXYZ" 661 26 true))

(def mod-1271-36
  (pure-system "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" 1271 36 true))

(defn hybrid-system [alphabet]
  (->HybridSystem alphabet (count alphabet)))

(def mod-1110
  (hybrid-system "0123456789"))

(def mod-2726
  (hybrid-system "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(def mod-3736
  (hybrid-system "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(defn valid? [sys s]
  (-valid? sys s))

(defn calc-check-character [sys s]
  (-calc-check-character sys s))
