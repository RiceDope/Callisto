package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Set;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedHashSet;

/**
 * Benchmark Set against JCF
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SetVJCF {

    private Set<Integer> set;
    private HashSet<Integer> hashSet;
    private TreeSet<Integer> treeSet;
    private LinkedHashSet<Integer> linkedHashSet;

    private int appendIterations = 1000;

    @Setup(Level.Invocation)
    public void setup() {
        set = new Set<>();
        hashSet = new HashSet<>();
        treeSet = new TreeSet<>();
        linkedHashSet = new LinkedHashSet<>();
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testSet(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            set.add(i);
        }

        set.contains(100);

        blackhole.consume(set);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testHashSet(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            hashSet.add(i);
        }

        hashSet.contains(100);

        blackhole.consume(hashSet);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testTreeSet(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            treeSet.add(i);
        }

        treeSet.contains(100);

        blackhole.consume(treeSet);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testLinkedHashSet(Blackhole blackhole) {

        for (int i = 0; i < appendIterations; i++) {
            linkedHashSet.add(i);
        }

        linkedHashSet.contains(100);

        blackhole.consume(linkedHashSet);
    }

}
