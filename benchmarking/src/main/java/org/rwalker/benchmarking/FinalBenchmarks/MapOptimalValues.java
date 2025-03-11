package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Map;

/**
 * get the optimal values for the Map class
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MapOptimalValues {

    @Param({"16", "32", "128", "256"})  // 16 is default for now
    private int buckets;
    @Param({"0.50", "0.75", "0.90", "1.0"})
    private float loadFactor;
    @Param({"1.5", "2.0", "2.5", "3.0"})
    private float expansionFactor;

    Map<String, Integer> map;

    @Setup(Level.Invocation)
    public void setupMap() {
        map = new Map<>(buckets, loadFactor, expansionFactor);
    }

    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void testPutAndGet(Blackhole blackhole) {
        for (int i = 0; i < 1000; i++) {
            map.put("Key"+i, i);
        }
        map.get("Key10");

        blackhole.consume(map);
    }
    
}
