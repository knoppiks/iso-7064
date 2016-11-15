(ns iso-7064.core
  (:require [iso-7064.system :refer [Iso7064System -valid? -calc-check-character]]
            [iso-7064.system.hybrid :refer [->HybridSystem]]
            [iso-7064.system.pure :refer [->PureSystem]]))

(defn pure-system [alphabet modulus rad two-check-characters?]
  (->PureSystem alphabet modulus rad two-check-characters?))

(defn valid? [sys s]
  (-valid? sys s))

(defn calc-check-character [sys s]
  (-calc-check-character sys s))
