package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.HowToFunction;
import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Consecutive enqueue dequeue operations to see if RingBuffer improvement
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class QueueConsecutive {

    @Param({"RINGBUFFER", "DEFAULT"})
    private SequenceStrategies strategyName;

    private Sequence<Integer> sequence;

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>(130, strategyName);

        for (int i = 0; i < 100; i++) {
            sequence.enqueue(i);
        }
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testQueue(Blackhole blackhole) {

        for (int i = 0; i < 1000; i++) {
            sequence.enqueue(i);
            sequence.dequeue();
        }

        blackhole.consume(sequence);
    }
    
}
