(ns clj-simple-config.core-test
  (:require [midje.sweet :refer :all]
            [clj-simple-config.core :refer :all]))

(fact "should throw exception if no config path found"
      (read-config) => (throws Exception "No path set in system property 'config-path' and no config.edn file could be found in resources"))

(fact "should load config from provided path"
      (System/setProperty "config-path" (.getAbsolutePath (java.io.File. "test-config.edn")))
      (read-config) => {:test {:map 1}})

(facts "get!"
       (fact "should get value in map"
             (get! {:a 1} :a) => 1)
       (fact "should throw exception if key missing"
             (get! {:a 1} :b) => (throws Exception))
       (fact "should throw exception if value nil"
             (get! {:a nil} :a) => (throws Exception)))

(facts "get-in!"
       (fact "should get value in map"
             (get-in! {:a {:b 1}} [:a :b]) => 1)
       (fact "should throw exception if key missing"
             (get-in! {:a {:b 1}} [:a :c]) => (throws Exception))
       (fact "should throw exception if value nil"
             (get-in! {:a {:b nil}} [:a :b]) => (throws Exception)))