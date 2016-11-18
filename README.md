# Clojure Implementation of ISO 7064

Clojure implementation of [ISO 7064 - Check check character systems](1).
Providing all Pure and Hybrid systems listed in the standard under 5.4.1.
Moreover custom check character systems can be created.

## Install

To install, just add the following to your project dependencies:
```
[org.clojars.knoppiks/iso-7064 "0.1.0"]
```

## Usage

```
(require 'iso-7064.core)

(iso-7064.core/calc-check-character iso-7064.core/mod-11-2 "079")
(iso-7064.core/valid? iso-7064.core/mod-11-2 "079X")
```

### Creating a custom system

```
(require 'iso-7064.core)

(def sys (iso-7064.core/pure-system "0123456789ABC" 13 2 false))
(iso-7064.core/calc-check-character sys "05BC")
(iso-7064.core/valid? sys "05BCA")
```
Note: Custom systems may be useless, if arguments are chosen unwisely.
For more information refer to the [standard](1) or other sources on check
character systems. 

## License

Copyright © 2016 Jonas Wagner

Distributed under the Eclipse Public License, the same as Clojure.

[1]: https://www.iso.org/obp/ui/#iso:std:iso-iec:7064:ed-1:v1:en
