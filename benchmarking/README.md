# Benchmarking

This maven project is focused on benchmarking and recording the benchamarks of different parts of the modern collections library.

## How this works
Each benchmark is split into multiple files. These are tagged as so (date-time-test.json) So (201124-1400-Instantiation) will test purely the instantiation of the classes

## Results and some information

### 201124-1400 (before naming scheme became common)
This tested the instantiation of the Sequence class against the ArrayList class. The ArrayList class performed on average about 6.5x better. The data was tested on a throughput basis so there was 6.5 ArrayList instantiations for every single Sequence instantiation.
This was tested on the code pre-optimisations on instantiation. SaveCode 00001
Tests carried out:
- Instantiate and append 1 item in Sequence
- Instantiate and append 1 item in ArrayList

### 241124-2330 (Carried out 4 benchmarks)
- Insert 1000 items in ascending order into ArrayList
- Insert 1000 items in ascending order into Sequence
- Insert 1000 items in ascending order into ArrayList and call Collections.Sort (Mimicks a sorted array with ArrayList)
- Insert 1000 items in ascending order into Sequence after calling sortOnwards() (Keeps sequence in sorted insertion order)

### 231224-1730 (Carried out 1 benchmark)
- Insert 1000 items in ascending order using Binary search to find location for insertion (ArrayList)

### 231224-2200 (Carried out 2 benchmarks)
- Insert 1000 items into ArrayList with a size specified of 100, 500 and 1000
- Same as above but for Sequence

### 030225-1430 (Carried out 1 benchmark)
- Put 1000 items into Map
- Different parameters given 64 benchmarks ran in total
- 1 iteration for each measurement
- different starting buckets (16, 32, 128, 256)
- differne expansion factors (1.5, 2.0, 2.5, 3.0)
- different load factors (0.5, 0.75, 0.9, 1.0)

### 030225-1530 (Carried out 2 benchmark)
- Put 1000 items in the Map<Integer, Integer> done in Setup
- Testing whether adding a Comparator sped things up
- This was tested on the keyExists function
- keyExists is used all over in order to get these "speed boosts"

LATER ADDITION:
- Test from 030225-1730 this is a test of performance with just a linear Sequence for the KeyExists method (Data no longer exists inside of that file)

### 030225-1730 (Carried out 3 benchmark)
- Overwritten from the previous mentioned instance of this ID
- Exact same benchmark just ran at the same time. Gave a much more reasonable OPS/ms that when ran alone

### 030225-1740 (Carried out 3 benchmark)
- Same concept as above benchmarks but the Map class changed back to default.
- The final benchmark that used the unsorted sequence was changed back to use a sorted sequence
- This was to see if the constructors made a huge difference

### 040225-1030 (Carried out 2 benchmark)
- Directly testing the methods used within the Map class to store keys
- Sequence contains method tested
- Set contains method tested
- Both had 1000 terms in ascending order. They are aware of nothing of its order
- Contains method is ran 1000 times in each benchmark

### 040225-1040 (carried out 3 benchmark)
- Same as above with 1 extra
- Sequence was sorted initially when the contains method was run 1000 times