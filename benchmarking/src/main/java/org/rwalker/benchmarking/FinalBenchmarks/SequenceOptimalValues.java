package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Find out the optimal values for the Sequence data structure
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SequenceOptimalValues {

    @Param({"10", "50", "100", "1000"})
    private int initialSize;
    @Param({"1.5", "2.0", "2.5", "3.0"})
    private float growthRate;
    @Param({"RINGBUFFER", "DEFAULT"})
    private SequenceStrategies strategyName;

    private Sequence<Integer> sequence;

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>(initialSize, growthRate, strategyName);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceOptimalValues(Blackhole blackhole) {

        for (int i = 0; i < initialSize; i++) {
            sequence.add(i);
        }

        sequence.get(initialSize/2);

        blackhole.consume(sequence);
    }
    
}
