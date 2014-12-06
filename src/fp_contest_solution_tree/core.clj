(ns fp-contest-solution-tree.core
  (:require [mc.util :refer :all]))

(def undefined "-")

(defn alternatives
  ([feature-values]
     (alternatives feature-values nil))
  ([feature-values indexes]
     (reduce-indexed
      (fn [acc idx itm]
        (if (and (or (nil? indexes) (indexes idx))
                 (not= undefined itm))
          (assoc acc itm (conj (get acc itm #{}) idx))
          acc)) {} feature-values)))

(defn solve-forest
  ([features] (solve-forest features nil))
  ([[[feature-name feature-values] & more] constraints]
     (let [alternatives (alternatives feature-values constraints)]
       #_(println "more: " more)
       (into #{}
             (for [answer (keys alternatives)]
               (let [constr (get alternatives answer)
                     node {:feature feature-name :answer answer
                           :alternatives constr}]
                 (if more
                   (assoc node :children (solve-forest more constr))
                   node)
                 ))))))


(comment

  (solve-forest [["f1" ["yes" "yes" "no" "no"]] ["f2" ["1" "2" "2" "-"]]] nil)

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
