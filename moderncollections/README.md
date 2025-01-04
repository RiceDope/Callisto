# Modern Collections Library
This is a Collections library that is being developed as a part of my dissertation at the University of Kent.

## Be aware
No current advisories

## Work-Ons
1. A system for the Sequence that after a sort has been called that all actions that do modify the array can be done in sorted order. This is undone as soon as a modifying method is called. Unless that is just a remove which will maintain the order. Any insertion or swap will violate this.

## Sequence

- ArrayList:
    - insert
    - append
    - appendAll
    - remove
    - get
    - replace
    - sort
    - sortCopy
    - clear
    - contains
    - firstIndexOf
    - length
- SortedArrayList
    - As above
    - sortOnwards
- Queue FIFO
    - Peek
    - enqueue
    - dequeue
    - size
- Queue LIFO
    - Just a stack
- Priority Queue
    - As above
    - sortOnwards
- Stack
    - Push
    - Pop
    - Peek