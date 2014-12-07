(defproject fp-contest-solution-tree "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [net.mikera/mikera-clojure "1.0.4"]
                 [expectations "2.0.9"]
                 [jline "2.12"]]
  :main ^:skip-aot fp-contest-solution-tree.core
  :profiles {:uberjar {:aot :all}})
