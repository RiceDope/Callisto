package org.rwalker.benchmarking;

/**
 * benchmarking the instantiation of my classes alongside ArrayList and HashMap
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.rwalker.Sequence;
import com.rwalker.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class BenchMarkInstantiation {

    /**
     * Run the benchmarks
     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(BenchMarkInstantiation.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .resultFormat(ResultFormatType.JSON)
                .result("NONE.json")
                .build();

        new Runner(opt).run();
    }
    
    /**
     * Benchmark the instantiation of the Sequence class with a length call
     * @return
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 5, warmups = 5)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public Object testSequenceInstantiation() {
        Sequence<Integer> sequence = new Sequence<>();
        sequence.length();
        return hashCode();
    }

    /**
     * Benchmark the instantiation of the HashMap class with a size call
     * @return
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 5, warmups = 5)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public Object testHashMapInstantiation() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.size();
        return hashCode();
    }

    /**
     * Benchmark the instantiation of the ArrayList class with a length call
     * @return
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 5, warmups = 5)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public Object testArrayListInstantiation() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.size();
        return hashCode();
    }

    /**
     * Benchmark the instantiaiton of the Map class with a size call
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 5, warmups = 5)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public Object testMapInstantiation() {
        Map<Integer, Integer> map = new Map<>();
        map.size();
        return hashCode();
    }

}
