package org.rwalker.benchmarking;

/**
 * Calculate the best parameters for the map class.
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;
import com.rwalker.*;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkMap {

    // @Param({"16", "32", "128", "256"})  // 16 is default for now
    // private int buckets;
    // @Param({"0.50", "0.75", "0.90", "1.0"})
    // private float loadFactor;
    // @Param({"1.5", "2.0", "2.5", "3.0"})
    // private float expansionFactor;

    private Map<Integer, Integer> map;
    private Map<Integer, Integer> mapComp;
    private Map<Integer, Integer> mapNoSetComp;


    @Setup(Level.Invocation)
    public void setupMap() {
        map = new Map<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }
    }

    @Setup(Level.Invocation)
    public void setupMapComparator() {
        mapComp = new Map<>((a, b) -> a - b);
        for (int i = 0; i < 1000; i++) {
            mapComp.put(i, i);
        }
    }

    @Setup(Level.Invocation)
    public void setupMapNoSetComparator() {
        mapNoSetComp = new Map<>(256, (a, b) -> a - b);
        for (int i = 0; i < 1000; i++) {
            mapNoSetComp.put(i, i);
        }
    }

    // @Benchmark
    // @Fork(value = 3, warmups = 1)
    // @Measurement(iterations = 2)
    // @Warmup(iterations = 1)
    // public void testPutAndGet() {
    //     for (int i = 0; i < 1000; i++) {
    //         map.put(i, i);
    //     }
    //     map.get(10);
    // }

    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void testComparatorSpeedUp() {
        mapComp.keyExists(10); // Features some "speedup" from comparator
    }

    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void testDefaultNoSpeedUp() {
        map.keyExists(10); // No speedup from comparator
    }

    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void testNoSetComparatorSpeedUp() {
        mapNoSetComp.keyExists(10); // No set or comparator usage (Sequence is used but is not sorted) Constructor trickery
    }
}
