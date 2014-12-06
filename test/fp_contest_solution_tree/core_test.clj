(ns fp-contest-solution-tree.core-test
  (:use expectations)
  (:require [fp-contest-solution-tree.core :refer :all]))

(expect  #{{:feature "f1" :answer "1" :alternatives #{0 1}}
          {:feature "f1" :answer "2" :alternatives #{2}}}
         (level-zero-forest ["f1" ["1" "1" "2"]]))

(expect {"no" #{1 2}, "yes" #{0 3}}
        (alternatives ["yes" "no" "no" "yes"]))
