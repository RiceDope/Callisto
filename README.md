# Project explanation

## moderncollections
The collections library being developed

## benchmarking
Benchmarking of the collections library using JMH

## benchmarkanalysis
Python programs being used in order to produce graphs and analyse the data from the benchmarks

# Plan

- Conceptualise the design
- Make slides describing the implementation and design (Also explaining choices)
- Look at old projects that could be sample projects:
    - Encryption Year 2 (Security)
    - League table Year 2 (Algorithms)
    - Student grades management system (Especially after work with consumer (Calculate a new Map or Sequence based on students percentages))
- Continue benchmarking
- Range of functions that take in standard java collections and turn them into my collection types.
- Allow all data types to shrink if they end up underpopulated
- Overload the sort function to be able to specify a comparator.
- Need to think about determinism (Removing items while an iterator is in effect)
- Seperate strategies for sorting Sequence
- Null support for Map
- Correct null support for Set
- Add more functionality to Queue and Dequeue Operations

# Ideas

Should I add a sorted section to my Set

Should my Map just use set access rather than a sorted Sequence

# Sample project Ideas
- Student grades system (Will demonstrate usefulness of consumers) Side by side with JCF