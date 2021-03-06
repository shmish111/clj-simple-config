# clj-simple-config

[![Build Status](https://travis-ci.org/shmish111/clj-simple-config.png)](https://travis-ci.org/shmish111/clj-simple-config)

Tired of writing the same simple config namespace?  Simple config is designed to do the basics:

* Read a clojure edn file from `config-path` System property or load `config.edn` from classpath resources
* Merge command line arguments starting `conf.` into config map
* Provide functions `get!` and `get-in!` that throw exceptions if value in map is nil

## Usage

In [Leiningen](http://github.com/technomancy/leiningen/) add the dependency [![Clojars Project](http://clojars.org/clj-simple-config/latest-version.svg)](http://clojars.org/clj-simple-config)

```clojure
(require '[clj-simple-config.core :refer [read-config]])

(System/setProperty "config-path" "my/config/file.edn")

(System/setProperty "conf.myproperty" "myval")

(def conf (read-config))

(:my-property conf)
(:other-property conf)
```

## License

Copyright © 2015 David Smith.

Distributed under the MIT License.