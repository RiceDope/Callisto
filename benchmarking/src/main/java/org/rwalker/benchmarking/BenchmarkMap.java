package org.rwalker.benchmarking;

/**
 * Calculate the best parameters for the map class.
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;
import com.rwalker.Map;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkMap {
    
    @Param({"16", "32", "128", "256"})  // 16 is default for now
    private int buckets;
    @Param({"0.50", "0.75", "0.90", "1.0"})
    private float loadFactor;
    @Param({"1.5", "2.0", "2.5", "3.0"})
    private float expansionFactor;

    @Setup(Level.Invocation)
    public void setupMap() {
        Map<Integer, Integer> map = new Map<>();
    }
}
