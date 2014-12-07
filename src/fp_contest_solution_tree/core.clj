(ns fp-contest-solution-tree.core
  (:gen-class)
  (:import [jline.console ConsoleReader])
  (:require [mc.util :refer [reduce-indexed]]
            [clojure.pprint :refer [pprint print-table]]))

(def undefined "—")

(def subject-names ["Ауата сетуньская" "Десятилиньята лепая" "Семипунктата Коха" "Популий грыжомельский" "Гортикола филопетьевая"])

(def subject-matrix  [["Наличие бомбурий",         ["Да",      "Да",        "Нет",       "Да",    "Нет"]],
                      ["Количество клептиконов",   ["1",       "1",         "0",         "3",     "5"]],
                      ["Цвет велория",             ["Красный", "Оранжевый", "Оранжевый", "—",     "Синий"]],
                      ["Наличие пумпеля",          ["Нет",     "Да",        "Да",        "—",     "—"]],
                      ["Величина пумпеля",         ["—",       "Большой",   "Маленький", "—",     "—"]],
                      ["Возможность крокотания",   ["Нет",     "Нет",       "—",         "Да",    "Нет"]],
                      ["Возможность бульботания",  ["Нет",     "Да",        "—",         "Да",    "Нет"]],
                      ["Наличие дуков и труков",   ["—",       "—",         "—",         "—",     "Да"]],
                      ["Цвет лемпелей",            ["Жёлтый",  "Жёлтый",    "Жёлтый",    "Белый", "Белый"]],
                      ["Наличие пильских трапков", ["Да",      "Да",        "Да",        "Да",    "Да"]]])


;; Domain logic
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

(defn get-subtree [tree option]
  (first (filter #(= (:answer %) option) tree)))
;; Domain logic end

;; Console UI functions
(defn print-answers [answers]
  (loop [current-answers answers
         index 1
         indexed-answers {}]
    (let [[feature ans] (first current-answers)]
      (println (str "" index ". " ans))

      (if (nil? (next current-answers))
        (assoc indexed-answers index ans)
        (recur (next current-answers) (inc index) (assoc indexed-answers index ans))))))

(declare ask-question)

(defn repeat-question [answers]
  (println "Please, choose right option from answers. Try one more time")
  (ask-question answers))

(defn ask-question [answers]
  (let [feature (first (first answers))]
    (println "What is the value of feature:" feature "?")
    (let [indexed-answers (print-answers answers)]
      (println "0. Nothing of the kind" )
      (print "Answer (Enter number of the option): ")
      (flush)
      (let [cr (ConsoleReader.)
            user-choise (.readLine cr)]
        (try
          (let [answer-number (Integer/parseInt user-choise)]
            (if (= 0 answer-number)
              (throw (IllegalStateException. (str "subject don't corresponds any of options for feature: " feature))))
            (if-let [answer (indexed-answers answer-number)]
              answer
              (repeat-question answers)))
          (catch NumberFormatException e (repeat-question answers)))))))

(defn answers [tree]
  (into #{}
        (for [{:keys [feature answer]} tree]
          [feature answer])))

(defn find-out [tree]
  (let [chosen-option (ask-question (answers tree))
        chosen-subtree (get-subtree tree chosen-option)]
    (if-let [children (:children chosen-subtree)]
      (find-out children)
      (map #(get subject-names %) (:alternatives chosen-subtree)))))

(defn -main
  [& args]
  (println "Going to build solve tree by subject matrix and profile it:")
  (let [tree (time (solve-tree subject-matrix))]
    (println "Here is a solve tree for subject matrix:")
    (time (pprint tree))
    (println "Here is a source subject matrix:")
    (pprint subject-matrix)
    (println)
    (println "Now let's define what is the subject in front of you by answering questions")
    (try
      (let [alternatives (find-out tree)]
        (if (= (count alternatives) 1)
          (println "Your subject is: " (first alternatives))
          (println "Feature matrix is not unique, so got following alternatives:" alternatives)))
      (catch IllegalStateException e (println "Your subject is not known to scientists, because" (.getMessage e))))))


;; Testing in REPL stuff. It is commented, so it wont be compiled and executed.
(comment
  (get-subtree (solve-tree subject-matrix) "Да")
  (print-table [:value1 :value2] [{:value1 1 :value2 2}{:value1 3 :value2 4}])
  (time (pprint (solve-tree subject-matrix)))
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
