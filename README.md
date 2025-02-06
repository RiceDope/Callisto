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
- Showcase how to use the Collections
- Continue benchmarking
- List out what needs to be completed by the deadline and the direction that I want to focus on for writeup.
- Issue with RingBuffer where it expands with one slot remaining. Default does not do this (fails test)
- Range of functions that take in standard java collections and turn them into my collection types.
- Allow all data types to shrink if they end up underpopulated
- Overload the sort function to be able to specify a comparator.
- Need to think about determinism (Removing items while an iterator is in effect)
- Spliterator + Consumers
- Seperate strategies for sorting Sequence

# Ideas

Should I add a sorted section to my Set

Should my Map just use set access rather than a sorted Sequence

# Sample project Ideas
- Student grades system (Will demonstrate usefulness of consumers) Side by side with JCF