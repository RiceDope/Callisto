package org.rwalker.benchmarking;

/**
 * Benchmark the Sequence class
 */

import com.rwalker.Sequence;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMarkSequence {

    private Sequence<Integer> sequence;
    private Sequence<Integer> sequenceSize;

    /*
     * Options for running the benchmarks
     * using arrayListAppend is just an instantited array ready to go
     */
    private int appendIterations = 1000;
    @Param({"10", "100", "500"})
    private int initialSize;

    /*
     * Any setup functions needed to run the benchmarks
     * Comparator specified for sorting operations
     */
    @Setup(Level.Invocation)
    public void setupArrayList() {
        sequence = new Sequence<>((a, b) -> a - b);
    }

    @Setup(Level.Invocation)
    public void setUpArrayListWIthSize() {
        sequenceSize = new Sequence<>(initialSize, (a, b) -> a - b);
    }

    /**
     * Benchmarks the append method in the sequence without any prior knowledge
     * about the size of final sequence.
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceAppend(Blackhole blackhole) {
        for (int i = 0; i < appendIterations; i++) {
            sequence.add(i);
        }
        blackhole.consume(sequence);
    }

    /**
     * Benchmarks the Sequence with the necessary code to keep the sequence sorted at all times
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceSorted(Blackhole blackhole) {

        sequence.sortOnwards();
        for (int i = 0; i < appendIterations; i++) {
            sequence.add(i);
        }


        blackhole.consume(sequence);
    }

    /**
     * Benchmarks the Sequence with some prior knowledge about final size
     * 100, 500, 1000
     * @param blackhole
     */
    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceAppend1000Size(Blackhole blackhole) {
        for (int i = 0; i < appendIterations; i++) {
            sequenceSize.add(i);
        }
        blackhole.consume(sequenceSize);
    }

}
