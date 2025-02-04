package org.rwalker.benchmarking;

/**
 * Benchmark comparisons between the Set and Sequence
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;
import com.rwalker.*;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SetSequenceComparison {
    
    private Sequence<Integer> seq;
    private Set<Integer> set;
    private Sequence<Integer> seqComp;

    @Setup(Level.Invocation)
    public void setup() {
        seq = new Sequence<>();
        for (int i = 0; i < 1000; i++) {
            seq.add(i);
        }

        set = new Set<>();
        for (int i = 0; i < 1000; i++) {
            set.add(i);
        }

        seqComp = new Sequence<>((a, b) -> a - b);
        for (int i = 0; i < 1000; i++) {
            seqComp.add(i);
        }
        seqComp.sortOnwards();
    }

    /**
     * Benchmark the sequence contains method
     * @param bh
     */
    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void benchmarkSequence(Blackhole bh) {
        for (int i = 0; i < 1000; i++) {
            bh.consume(seq.contains(i));
        }
    }

    /**
     * Benchmark the set contains method
     * @param bh
     */
    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void benchmarkSet(Blackhole bh) {
        for (int i = 0; i < 1000; i++) {
            bh.consume(set.contains(i));
        }
    }

    /**
     * Benchmark the sequence contains method with comparator
     * @param bh
     */
    @Benchmark
    @Fork(value = 3, warmups = 1)
    @Measurement(iterations = 2)
    @Warmup(iterations = 1)
    public void benchmarkSequenceComparator(Blackhole bh) {
        for (int i = 0; i < 1000; i++) {
            bh.consume(seqComp.contains(i));
        }
    }
}
