package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Test swapping strategies under load and empty
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StrategySwap {

    private Sequence<Integer> sequence;
    private Sequence<Integer> sequenceLoad;

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>(SequenceStrategies.DEFAULT);
        sequenceLoad = new Sequence<>(SequenceStrategies.DEFAULT);

        for (int i = 0; i < 1000; i++) {
            sequenceLoad.add(i);
        }

    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapEmpty(Blackhole blackhole) {

        sequence.swapStrategies(SequenceStrategies.RINGBUFFER);

        blackhole.consume(sequence);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapLoad(Blackhole blackhole) {

        sequenceLoad.swapStrategies(SequenceStrategies.RINGBUFFER);

        blackhole.consume(sequenceLoad);
    }
}
