package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.annotations.*;

import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceState;

/**
 * All benchmarks that should be run on a sorted class set to be run here
 * 
 * @author Rhys Walker
 * @since 11/03/2025
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SortedBenchmarks {

    private Sequence<Integer> sequence; // Just an instantiated array ready to go
    private ArrayList<Integer> arrayList; // Just an instantiated array ready to go
    private int appendIterations = 1000;

    /*
     * Any setup functions needed to run the benchmarks
     */
    @Setup(Level.Invocation)
    public void setup() {
        arrayList = new ArrayList<>();
        sequence = new Sequence<>((a, b) -> a - b, SequenceState.SORTED);
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
     * Benchmark Sequeunce class with the necessary code to keep the array sorted at all times
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceSorted(Blackhole blackhole){

        for (int i = 0; i < appendIterations; i++) {
            sequence.add(i);
        }
    
        blackhole.consume(sequence);
    }

}
