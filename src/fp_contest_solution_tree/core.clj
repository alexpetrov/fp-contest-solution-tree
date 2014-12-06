(ns fp-contest-solution-tree.core
  (:require [mc.util :refer :all]))

(defn alternatives [feature-values]
  (reduce-indexed (fn [acc idx itm]
                    (assoc acc itm (conj (get acc itm #{}) idx))) {} feature-values))

(defn level-zero-forest
  [[feature-name feature-values]]
  (let [alternatives (alternatives feature-values)]
    (into #{}
        (for [answer (keys alternatives)]
          {:feature feature-name :answer answer :alternatives (get alternatives answer)}))))



(comment

(solve-forest ["f1" ["yes" "yes" "no" "no"]])

(def f1 ["f1" ["1" "1" "2"]])
(def values ["yes" "no" "no" "yes"])
f1
values
(into #{} values)
(get {:a 1} :b #{})
(reduce (fn [acc itm]
          (conj acc itm)) #{} [1 2 3])
(reduce-indexed (fn [acc idx itm]
                  (println (str "acc: " acc " idx: " idx " itm: " itm))
                  (assoc acc itm (conj (get acc itm #{}) idx))) {} values)



)
