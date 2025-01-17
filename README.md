# Project explanation

## moderncollections
The collections library being developed

## contactsapplication
A sample program using a sequence in order to store contacts

## benchmarking
Benchmarking of the collections library using JMH

## benchmarkanalysis
Python programs being used in order to produce graphs and analyse the data from the benchmarks

# Plan

- Conceptualise the design
- Make slides describing the implementation and design (Also explaining choices)
- Learn about long term strategies
- Look at old projects that could be sample projects:
    - Encryption Year 2 (Security)
    - League table Year 2 (Algorithms)
- Showcase how to use the Collections
- Continue benchmarking
- An interface that all Collections implement which exposes the iterator. For addAll methods and such
- List out what needs to be completed by the deadline and the direction that I want to focus on for writeup.
- Give Sets a guarantee of ordering whilst keeping the buckets system the same as currently are. "Linked List"

# Ideas

All of my collections will implement a single interface that allows them to interact with eachother. Such as containsAll operations and such.

Should I allow purely my collection types or all collection types for methods which require such.

Change the map over to use a Set as it would be more efficient for Key processing