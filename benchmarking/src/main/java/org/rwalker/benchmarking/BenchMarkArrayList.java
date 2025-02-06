package org.rwalker.benchmarking;

/**
 * Benchmark the ArrayList class
 */

import java.util.ArrayList;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;
import java.util.Collections;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMarkArrayList {

    private ArrayList<Integer> arrayList; // Just an instantiated array ready to go
    private ArrayList<Integer> arrayListSize;

    /*
     * Options for running the benchmarks
     * using arrayListAppend is just an instantited array ready to go
     */
    private int appendIterations = 1000;
    @Param({"100", "500", "1000"})
    private int initialSize;

    /*
     * Any setup functions needed to run the benchmarks
     */
    @Setup(Level.Invocation)
    public void setupArrayList() {
        arrayList = new ArrayList<>();
    }

    @Setup(Level.Invocation)
    public void setUpArrayListWIthSize() {
        arrayListSize = new ArrayList<>(initialSize);
    }

    /**
     * Benchmarks the add method of the ArrayList without any prior knowledge
     * about the size of final array.
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testArrayListAppend(Blackhole blackhole) {
        for (int i = 0; i < appendIterations; i++) {
            arrayList.add(i);
        }
        blackhole.consume(arrayList);
    }

    /**
     * Benchmarks the ArrayList with the necessary code to keep the array sorted at all times
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testArrayListSorted(Blackhole blackhole) {
        for (int i = 0; i < appendIterations; i++) {
            arrayList.add(i);
            Collections.sort(arrayList);
        }
        blackhole.consume(arrayList);
    }

    /**
     * Benchmarks the ArrayList with the necessary code to keep the array sorted at all times
     * Uses binary search to find the index to insert the element
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testArrayListSortedBinarySearch(Blackhole blackhole){


        for (int i = 0; i < appendIterations; i++) {
            int index = Collections.binarySearch(arrayList, i);
            if (index < 0) {
                index = -index - 1;
            }
            arrayList.add(index, i);
        }

        
        blackhole.consume(arrayList);
    }

    /**
     * Benchmarks the add method of the ArrayList with prior knowledge
     * about the size of final array.
     * Will benchmark with size of 100, 500, 1000
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testArrayListAppend1000WithSize(Blackhole blackhole) {
        for (int i = 0; i < appendIterations; i++) {
            arrayListSize.add(i);
        }
        blackhole.consume(arrayListSize);
    }
}
