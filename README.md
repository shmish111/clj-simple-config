# simple-config

Tired of writing the same simple config namespace?  Simple config is designed to do the basics:

* Read a clojure edn file from `config-path` System property or load `config.edn` from classpath resources
* Provide functions `get!` and `get-in!` that throw exceptions if value in map is nil

## Usage

```clojure
(require '[simple-config.core :refer [read-config]])

(System/setProperty "config-path" "my/config/file.edn")

(def conf (read-config))

(:my-property conf)
```

## License

Copyright © 2015 David Smith

Distributed under the MIT License.