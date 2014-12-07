# Functional Programming December 2014 Contest by Roman Dushkin

A Clojure application builds solve tree by features subjects, and using this tree to figure out which kind of subject is in front of user by asking questions.

Check out `solve-tree` and `alternatives` functions in [core.clj](https://github.com/alexpetrov/fp-contest-solution-tree/blob/master/src/fp_contest_solution_tree/core.clj). Them builds actual solve tree. Everything else is Console UI of expert system which traverses solve tree.

Check out tests to see basic examples of what algorithm is about.

## Usage

To run program enter following in console:

```
./run.sh
```

It will compile program to one uberjar and run it.

## License

Copyright © 2014 Alexander Petrov (a.k.a. Lysenko by passport)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
