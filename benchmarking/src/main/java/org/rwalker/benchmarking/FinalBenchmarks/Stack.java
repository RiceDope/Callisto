package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.HowToFunction;
import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Find out the optimal values for the Sequence data structure while acting as a Stack in both RingBuffer and Default strategies
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Stack {

    @Param({"RINGBUFFER", "DEFAULT"})
    private SequenceStrategies strategyName;

    private Sequence<Integer> sequence;

    @Setup(Level.Invocation)
    public void setup() {
        sequence = new Sequence<>(strategyName);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testStack(Blackhole blackhole) {

        for (int i = 0; i < 1000; i++) {
            sequence.push(i);
        }

        sequence.peek(HowToFunction.STACK);

        for (int i = 0; i < 1000; i++) {
            sequence.pop();
        }

        blackhole.consume(sequence);
    }
}
