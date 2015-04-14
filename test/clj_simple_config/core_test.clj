(ns clj-simple-config.core-test
  (:require [midje.sweet :refer :all]
            [clj-simple-config.core :refer :all]))

(fact "should throw exception if no config path found"
      (System/clearProperty "config-path")
      (read-config) => (throws Exception "No path set in system property 'config-path' and no config.edn file could be found in resources"))

(fact "should load config from provided path"
      (System/setProperty "config-path" (.getAbsolutePath (java.io.File. "test-config.edn")))
      (System/clearProperty "conf.test.a-decimal")
      (System/clearProperty "conf.test.an-int")
      (System/clearProperty "conf.test.a-bool")
      (System/clearProperty "conf.test.an-ip")
      (read-config) => {:test {:map 1}})

(fact "should parse system property numbers and booleans"
      (System/setProperty "config-path" (.getAbsolutePath (java.io.File. "test-config.edn")))
      (System/setProperty "conf.test.a-decimal" "1.0")
      (System/setProperty "conf.test.an-int" "1")
      (System/setProperty "conf.test.a-bool" "true")
      (System/setProperty "conf.test.an-ip" "1.2.3.4")
      (read-config) => {:test {:map       1
                               :a-decimal 1.0
                               :an-int    1
                               :a-bool    true
                               :an-ip     "1.2.3.4"}})

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