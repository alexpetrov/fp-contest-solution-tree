(ns fp-contest-solution-tree.core
  (:gen-class)
  (:require [mc.util :refer :all]
            [clojure.pprint :refer [pprint print-table]]))

(def undefined "—")

(def beatles-matrix  [["Наличие бомбурий",         ["Да",      "Да",        "Нет",       "Да",    "Нет"]],
                      ["Количество клептиконов",   ["1",       "1",         "0",         "3",     "5"]],
                      ["Цвет велория",             ["Красный", "Оранжевый", "Оранжевый", "—",     "Синий"]],
                      ["Наличие пумпеля",          ["Нет",     "Да",        "Да",        "—",     "—"]],
                      ["Величина пумпеля",         ["—",       "Большой",   "Маленький", "—",     "—"]],
                      ["Возможность крокотания",   ["Нет",     "Нет",       "—",         "Да",    "Нет"]],
                      ["Возможность бульботания",  ["Нет",     "Да",        "—",         "Да",    "Нет"]],
                      ["Наличие дуков и труков",   ["—",       "—",         "—",         "—",     "Да"]],
                      ["Цвет лемпелей",            ["Жёлтый",  "Жёлтый",    "Жёлтый",    "Белый", "Белый"]],
                      ["Наличие пильских трапков", ["Да",      "Да",        "Да",        "Да",    "Да"]]])

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

(defn solve-tree
  ([features] (solve-tree features nil))
  ([[[feature-name feature-values] & more] constraints]
     (let [alternatives (alternatives feature-values constraints)]
       (into #{}
             (for [answer (keys alternatives)]
               (let [constr (get alternatives answer)
                     node {:feature feature-name :answer answer
                           :alternatives constr}]
                 (if more
                   (let [children (solve-tree more constr)]
                     (if (empty? children)
                       node
                       (assoc node :children children)))
                   node)))))))

(defn -main
  [& args]
  (time (pprint (solve-tree beatles-matrix))))

(comment

  (print-table [:value1 :value2] [{:value1 1 :value2 2}{:value1 3 :value2 4}])
  (time (pprint (solve-tree beatles-matrix)))
  (solve-tree [["f1" ["yes" "yes" "no" "no"]] ["f2" ["1" "2" "2" "—"]]] nil)

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
