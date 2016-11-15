(ns iso-7064.system)

(defprotocol Iso7064System
  (-valid? [this s])
  (-calc-check-character [this s]))
