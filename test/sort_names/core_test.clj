(ns sort-names.core-test
  (:require [clojure.test :refer :all]
            [sort-names.core :refer :all]))

(deftest lines-test
  (testing "sort-names can split up the file into lines"
    (is
      (= 
        ["Bilbo Baggins" "Ken Anderson"]
        (lines "Bilbo Baggins\nKen Anderson\n")))))

(deftest parts-test
  (testing "sort-names can split up lines into individual names"
    (is
      (=
        [["Bilbo" "Baggins"] ["Ken" "Anderson"]]
        (parts ["Bilbo Baggins" "Ken Anderson"]))))) 

(deftest key-test
  (testing "sort-names can generate a key for each name vector"
    (is
      (=
        "AndersonKen"
        (get-key ["Ken" "Anderson"])))))
  
(deftest prepare-test
  (testing "sort-names can prepare a list of names for sorting"
    (is
      (=
        [["BagginsBilbo" ["Bilbo" "Baggins"]] ["AndersonKen" ["Ken" "Anderson"]]]
        (prepare-names [["Bilbo" "Baggins"] ["Ken" "Anderson"]])))))

(deftest extract-test
  (testing "sort-names can extract a list of names after sorting"
    (is
      (=
        [["Bilbo" "Baggins"] ["Ken" "Anderson"]]
        (extract-names [["BagginsBilbo" ["Bilbo" "Baggins"]] ["AndersonKen" ["Ken" "Anderson"]]])))))

(deftest sort-test
  (testing "sort-names can sort a list of names. That's the whole point."
    (is
      (=
        [["Ken" "Anderson"] ["Bilbo" "Baggins"]]
        (process [["Bilbo" "Baggins"] ["Ken" "Anderson"]])))))

(deftest create-string-test
  (testing "sort-names can create an output string from a list of names."
    (is
      (=
        "Ken Anderson\nBilbo Baggins\n"
        (create-string [["Ken" "Anderson"] ["Bilbo" "Baggins"]])))))
