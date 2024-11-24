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

### 