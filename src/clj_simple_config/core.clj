(ns clj-simple-config.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.string :as s]))

(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*([Ee]\+\d+|[Ee]-\d+|[Ee]\d+)?$" (.trim s))
    (read-string s)))

(defn- string->primitive
  "slightly inaccurate name, tries to parse the string into a number or boolean"
  [v]
  (or
    (parse-number v)
    (cond (= "true" v) true
          (= "false" v) false
          :else v)))

(defn read-config
  "read config from the edn file in the location of system property `config-path` or from the resource config.edn
  Will merge in System properties starting with `conf.`
  Property keys separated by a period will be parsed as nested maps.  E.g. `conf.a.b=1` => {:a {:b 1}}
  System properties values will be coerced into numbers and booloeans if possible."
  []
  (if-let [path (or (System/getProperty "config-path")
                    (io/resource "config.edn"))]
    (with-open [rdr (-> path
                        io/reader
                        java.io.PushbackReader.)]
      (merge (edn/read rdr) (->> (System/getProperties)
                                 (filter (fn [[k v]] (.startsWith k "conf.")))
                                 (map (fn [[k v]] [(.substring k 5) v]))
                                 (map (fn [[k v]]
                                        (let [path (->> (s/split k #"\.")
                                                        (map keyword))]
                                          (assoc-in {} path (string->primitive v)))))
                                 (apply merge))))
    (throw (Exception. "No path set in system property 'config-path' and no config.edn file could be found in resources"))))

(defn get!
  "throws an exception if returned value is nil"
  [m k]
  (or (get m k) (throw (ex-info (format "could not find %s in map" k) m))))

(defn get-in!
  "throws an exception if returned value is nil"
  [m ks]
  (or (get-in m ks) (throw (ex-info (format "could not find %s in map" ks) m))))