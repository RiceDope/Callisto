package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * benchmark sequences different strategies against ArrayList in order to see optimal values
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SequenceStrategiesArrayList {

    private Sequence<Integer> sequenceDefault;
    private Sequence<Integer> sequenceRingBuffer;
    private ArrayList<Integer> arrayList;
    private int appendIterations = 1000;

    @Setup(Level.Invocation)
    public void setup() {
        arrayList = new ArrayList<>();
        sequenceDefault = new Sequence<>(SequenceStrategies.DEFAULT);
        sequenceRingBuffer = new Sequence<>(SequenceStrategies.RINGBUFFER);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testArrayListSorted(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            arrayList.add(i);
        }

        arrayList.get(appendIterations/2);

        blackhole.consume(arrayList);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceDefault(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            sequenceDefault.add(i);
        }

        sequenceDefault.get(appendIterations/2);

        blackhole.consume(sequenceDefault);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSequenceRingBuffer(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            sequenceRingBuffer.add(i);
        }

        sequenceRingBuffer.get(appendIterations/2);

        blackhole.consume(sequenceRingBuffer);
    }
}
