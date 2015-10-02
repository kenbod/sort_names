# sort-names

This is a simple example of writing a program in Clojure along with
a set of test cases.

## Overview

This program takes an input file of first and last names that appear in
unsorted order and creates an output file that contains the names
in sorted order by last name and then first name.

## Input/Output

The input file should have the following structure:

```
First Last\n
First Last\n
First Last\n
First Last\n
```

For instance:

```
Sam Gamshee
Frodo Baggins
Bilbo Baggins
Gandalf Gray
Legolas Elf
Gimle Dwarf
```

but not:

```
Sam Gamshee
Frodo Baggins
Bilbo Baggins
Gandalf the Gray
Legolas the Elf
Gimle the Dwarf
```

That is, each line can only have a first and last name separated
by a space.

Given the above valid input file, this program will produce a file
that contains the following:

```
Bilbo Baggins
Frodo Baggins
Gimle Dwarf
Legolas Elf
Sam Gamshee
Gandalf Gray
```

## Additional Information

The program expects to be invoked with two command line arguments. The
first argument is the name of the input file. The second argument is the
name of the output file to be created (or overwritten).

The program is not very robust. It will fail if the command line arguments
are absent or fail to refer to files correctly. It will also fail if
the structure of the input file is not correct.

The purpose of this example, however, is **not** to create a program that
is impervious to failures but to show the basics for creating a
Clojure program that accepts input, transforms it, and produces output.
It also shows the basics of how to construct and document functions
and how to write test cases that can be applied to the code.

## Usage

`lein run <input> <output>`

The command above will execute the program, sort the input file, and
store the results in the output file. This repo ships with an example
file of names called names.txt. So, one example of running the program
is:

`lein run names.txt sorted.txt`

## Testing

`lein test`

The command above will execute the test cases defined in the
`test/sort_names` directory.

## Clojure Information

The code makes use of a lot of different features of Clojure:
`map` and `reduce`, anonymous functions, the `str` function to create
strings, `slurp` to read files and `spit` to write them, and a
technique we haven't seen yet called destructuring. Take a look at the
definition of the `get-key` function:

```clojure
(defn get-key [[first last]]
  (str last first))
```

The syntax `[[first last]]` is where destructuring is occuring. What
this syntax means is that:

1. We expect our one and only argument to be a vector. 
2. We also expect that vector to have only two elements.
3. For our function body, bind the symbol `first` to the first
   element in that vector and bind the symbol `last` to the
   second element.

If we didn't do destructuring, we would have had to define `get-key` like
this:

```clojure
(defn get-key [name]
  (def first-name (first name)) 
  (def last-name (first (rest name)))
  (str last-name first-name))
```

We can skip these extra steps of pulling the vector apart by having
clojure automatically destructure the vector into the two named
symbols `first` and `last`. The resulting code is more concise
but does take some getting used to.

## License

The MIT License (MIT)

Copyright © 2015 Kenneth M. Anderson

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the
Software, and to permit persons to whom the Software is furnished to
do so, subject to the following conditions:

The above copyright notice and this permission notice shall
be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
