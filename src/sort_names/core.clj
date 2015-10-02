(ns sort-names.core)

(defn read-file
  "Returns the contents of file f as a string."
  [f]
  (slurp f))

(defn lines
  "Splits a string by line endings and returns a sequence of lines."
  [body]
  (clojure.string/split-lines body))

(defn parts
  "
Takes a sequence of strings containing first and last names
and returns a sequence of vectors with the names split into
separate entries." 
  [lines]
  (map #(clojure.string/split % #" ") lines))

(defn names
  "
Reads the file and splits it into lines. Each line is then split
into an array containing the first and last name."
  [file]
  (parts (lines (read-file file))))

(defn get-key
  "
Given a vector containing a first and last name, create a string
that lists the last name first followed by the first name. For
instance, if the array [\"Ken\" \"Anderson\"] is passed in,
the key \"AndersonKen\" is returned." 
  [[first last]]
  (str last first))

(defn prepare-names
  "
Given a vector of names where each name is itself a vector containing
a first name and a last name, transform the input vector such that
the output vector is a sequence of vectors where each vector has
a key as its first element and a vector containing a first and
last name as its second element. In other words, if the input vector
looks like this:

[[\"Ken\" \"Anderson\"] [\"Bilbo\" \"Baggins\"]]

then produce an output vector that looks like this:

[[\"AndersonKen\" [\"Ken\" \"Anderson\"]] [\"BagginsBilbo\" [\"Bilbo\" \"Baggins\"]]]"
  [names]
  (map (fn [name] [(get-key name) name]) names))

(defn extract-names
  "
Given an input vector in the format produced by prepare-names,
produce an output vector where each element is simply a vector
containing a first and last name. In other words, if the input
vector looks like this:

[[\"AndersonKen\" [\"Ken\" \"Anderson\"]] [\"BagginsBilbo\" [\"Bilbo\" \"Baggins\"]]]

then produce an output vector that looks like this:

[[\"Ken\" \"Anderson\"] [\"Bilbo\" \"Baggins\"]]"
  [sorted-names]
  (map (fn [[key name]] name) sorted-names))

(defn process
  "Process knits the functions above to sort the names."
  [names]
  (extract-names (sort (prepare-names names))))

(defn create-string
  "
Given an input vector in the format produced by extract-names,
produce a single string where each name appears on its own
line and each line is separated by a new line character. Thus,
if the input vector looks like this:

[[\"Ken\" \"Anderson\"] [\"Bilbo\" \"Baggins\"]]

then produce an output string that looks like this:

\"Ken Anderson\\nBilbo Baggins\\n\""
  [sorted-names]
  (reduce
    (fn [contents [first last]] (str contents first " " last "\n"))
    ""
    sorted-names))

(defn sort-names
  "
The main program:

  1. Determine the input and output file names.
  2. Read the names into a sequence of vectors of first and last names.
  3. Prepare the names, sort them, and then extract the sorted names.
  4. Create an output string.
  5. Write the output string to disk and print a message that we're done."
  [args]
  (def input-name (first args))
  (def output-name (first (rest args)))
  (def items (names input-name))
  (def sorted (process items))
  (def output (create-string sorted))
  (spit output-name output)
  (println (str "Wrote sorted names to \"" output-name "\"")))

(defn -main
  "Pass the arguments to sort-names. Time the whole operation."
  [& args]
  (time (sort-names args)))
