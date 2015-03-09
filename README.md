# simple-config

Tired of writing the same simple config namespace?  Simple config is designed to do the basics:

* Read a clojure edn file from various location
* Provide functions that throw exceptions if config does not exist

## Usage

```clojure
(require '[simple-config.core :refer [read-config]])

(def conf (read-config))

(:my-property conf)
```

## License

Copyright © 2015 David Smith
