package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Test swapping states under load and empty and in both strategies and once when specifiying comparator
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StateSwapping {

    @Param({"RINGBUFFER", "DEFAULT"})
    private SequenceStrategies strategyName;
    private Sequence<Integer> sequence;
    private Sequence<Integer> sequenceLoad;

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>((a, b)-> a - b, strategyName);
        sequenceLoad = new Sequence<>((a, b)-> a - b, strategyName);

        for (int i = 0; i < 1000; i++) {
            sequenceLoad.add(i);
        }

    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapEmpty(Blackhole blackhole) {

        sequence.sortOnwards();

        blackhole.consume(sequence);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapLoad(Blackhole blackhole) {

        sequenceLoad.sortOnwards();

        blackhole.consume(sequenceLoad);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapEmptySpecify(Blackhole blackhole) {

        sequence.sortOnwards((a, b)->b-a);

        blackhole.consume(sequence);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapLoadSpecify(Blackhole blackhole) {

        sequenceLoad.sortOnwards((a, b)->b-a);

        blackhole.consume(sequenceLoad);
    }


}
