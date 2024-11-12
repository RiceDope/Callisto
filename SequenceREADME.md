# Sequence
Sequence is a data type much like an ArrayList. It is a dynamic collection of items that can function like: ArrayList, SortedList, Queue, Priority Queue, Stack. The data type features equality functions as well as iterator support and toString support. In order for each data type to function correctly the user must be careful with their method selection. This document outlines the key ways to combine methods to achieve functionlity.

## Priority Queue
In order to acheive a priority queue make sure that a comparator is set when defining the type and then declare it to be sorted using the sortOnwards function. Depending on the comparator this will either be ascending or descending order. Then when enqueueing and dequeueing it will be in priority order.

```
Sequence<Integer> test = new Sequence<>((a, b) -> a-b);
test.sortOnwards();
test.enqueue(100);
test.enqueue(99);
test.enqueue(101);
test.dequeue(); // Will dequeue 99
```

Doing this without the sort will make it function as a FIFO queue instead.