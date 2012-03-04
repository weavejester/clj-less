(ns clj-less.core
  (:use [evaljs core rhino])
  (:require [clojure.java.io :as io]))

(defn- read-source [src]
  (if (string? src)
    src
    (slurp src)))

(defn less [source]
  (with-context (rhino-context {:source source})
    (evaljs (io/resource "clj_less/less-rhino.js"))
    (evaljs
     "var output = null
      var parser = new less.Parser()
      parser.parse(source, function(err, tree) {
        if (err) {
          output = {error: err}
        } else {
          output = {result: tree.toCSS()}
        }
      })
      output")))
