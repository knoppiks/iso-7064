(ns iso-7064.test_util
  (:require [iso-7064.system :refer [Iso7064System -calc-check-character]]
            [clojure.test.check.generators :as gen]
            [iso-7064.core :refer :all])
  (:import (com.github.danieltwagner.iso7064 Mod11_2 Mod37_2 Mod97_10 Mod661_26 Mod1271_36)
           (de.knoppiks.sandbox.iso7064 CustomPureSystem)))

(defrecord JavaWrapper [sys two-check-characters?]
  Iso7064System
  (-valid? [_ s]
    (.verify sys s))
  (-calc-check-character [_ s]
    (->> (seq (.compute sys s))
         (take-last (if two-check-characters? 2 1))
         (apply str))))

(defn- wrap [obj two-check-characters?]
  (->JavaWrapper obj two-check-characters?))

(def ref-mod-11-2
  (-> (Mod11_2.)
      (wrap false)))

(def ref-mod-37-2
  (-> (Mod37_2.)
      (wrap false)))

(def ref-mod-97-10
  (-> (Mod97_10.)
      (wrap true)))

(def ref-mod-661-36
  (-> (Mod661_26.)
      (wrap true)))

(def ref-mod-1271-36
  (-> (Mod1271_36.)
      (wrap true)))

(defn ref-custom [alphabet modulus rad two-check-characters?]
  (-> (CustomPureSystem. alphabet modulus rad two-check-characters?)
      (wrap two-check-characters?)))

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

(def systems
  {:mod-11-2
   {:impl mod-11-2 :ref ref-mod-11-2
    :examples ["898901"] :allowed-chars "0123456789"}

   :mod-31-2
   {:impl (pure-system "0123456789ABCDEFGHJKLMPQRTUWXYZ" 31 2 false)
    :ref (ref-custom "0123456789ABCDEFGHJKLMPQRTUWXYZ" 31 2 false)
    :examples ["A82KP"] :allowed-chars "0123456789ABCDEFGHJKLMPQRTUWXYZ"}

   :mod-37-2
   {:impl mod-37-2 :ref ref-mod-37-2
    :examples [] :allowed-chars "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"}

   :mod-97-10
   {:impl mod-97-10 :ref ref-mod-97-10
    :examples [] :allowed-chars "0123456789"}

   :mod-661-26
   {:impl mod-661-26 :ref ref-mod-661-36
    :examples ["BAISDLAFK"] :allowed-chars "ABCDEFGHIJKLMNOPQRSTUVWXYZ"}

   :mod-1271-36
   {:impl mod-1271-36 :ref ref-mod-1271-36
    :examples ["ISO79"] :allowed-chars "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"}})

(defn gen-char-from [chars]
  "Generate characters from given list of characters."
  (gen/fmap #(get (vec (seq chars)) %) (gen/choose 0 31)))

(defn gen-string-from [chars]
  "Generate strings from given list of characters."
  (gen/fmap clojure.string/join (gen/vector (gen-char-from chars))))
