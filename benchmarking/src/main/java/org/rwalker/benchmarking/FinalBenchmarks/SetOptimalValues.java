package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Set;

/**
 * Get optimal values for the Set
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SetOptimalValues {

    @Param({"16", "32", "64", "500", "1000"})
    private int size;
    @Param({"0.25", "0.5", "0.75", "0.9"})
    private double loadFactor;
    @Param({"1.5", "2.0", "2.5", "3.0"})
    private double growthFactor;

    private Set<Integer> set;
    private int appendIterations = 1000;

    @Setup(Level.Invocation)
    public void setup() {
        set = new Set<>(size, loadFactor, growthFactor);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testOptimalValues(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            set.add(i);
        }

        set.contains(100);

        blackhole.consume(set);
    }

}
