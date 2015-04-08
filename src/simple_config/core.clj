(ns simple-config.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn read-config
  "read config from the edn file in the location of system property `config-path` or from the resource config.edn"
  []
  (if-let [path (or (System/getProperty "config-path")
                    (io/resource "config.edn"))]
    (with-open [rdr (-> path
                        io/reader
                        java.io.PushbackReader.)]
      (edn/read rdr))
    (throw (Exception. "No path set in system property 'config-path' and no config.edn file could be found in resources"))))

(defn get!
  "throws an exception if returned value is nil"
  [m k]
  (or (get m k) (throw (ex-info (format "could not find %s in map" k) m))))

(defn get-in!
  "throws an exception if returned value is nil"
  [m ks]
  (or (get-in m ks) (throw (ex-info (format "could not find %s in map" ks) m))))