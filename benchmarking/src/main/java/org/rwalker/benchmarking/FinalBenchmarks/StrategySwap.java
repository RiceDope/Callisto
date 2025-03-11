package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.HowToFunction;
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

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>(SequenceStrategies.DEFAULT);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSwapEmpty(Blackhole blackhole) {

        

    }

}
