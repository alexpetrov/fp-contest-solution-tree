(ns fp-contest-solution-tree.core-test
  (:use expectations)
  (:require [fp-contest-solution-tree.core :refer :all]))

(expect  #{{:feature "f1" :answer "1" :alternatives #{0 1}}
          {:feature "f1" :answer "2" :alternatives #{2}}}
         (solve-tree [["f1" ["1" "1" "2"]]]))


(expect #{{:feature "f1" :answer "1" :alternatives #{0 1}
           :children #{{:feature "f2" :answer "yes" :alternatives #{0}}
                       {:feature "f2" :answer "no" :alternatives #{1}}}}
          {:feature "f1" :answer "2" :alternatives #{2}
           :children #{{:feature "f2" :answer "no" :alternatives #{2}}}}}
        (solve-tree [["f1" ["1" "1" "2"]]
                       ["f2" ["yes" "no" "no"]]]))

(expect {"no" #{1 2} "yes" #{0 3}}
        (alternatives ["yes" "no" "no" "yes"]))

(expect {"yes" #{0 3}}
        (alternatives ["yes" "no" "no" "yes"] #{0 3}))

(expect {"yes" #{0 3} "no" #{1 2}}
        (alternatives ["yes" "no" "no" "yes"] nil))

(expect {"yes" #{0} "no" #{1 2}}
        (alternatives ["yes" "no" "no" "-"]))
