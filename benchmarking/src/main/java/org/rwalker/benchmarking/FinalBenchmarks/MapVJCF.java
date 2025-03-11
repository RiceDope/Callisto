package org.rwalker.benchmarking.FinalBenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.rwalker.Map;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;

/**
 * Benchmark the Map class against the JCF Map classes
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MapVJCF {

    private Map<String, Integer> map;
    private HashMap<String, Integer> hashMap;
    private TreeMap<String, Integer> treeMap;
    private LinkedHashMap<String, Integer> linkedHashMap;

    private int appendIterations = 1000;

    private static final String key1 = "key10";
    private static final String key2 = "key500";
    private static final String key3 = "key100";


    @Setup(Level.Invocation)
    public void setup() {
        map = new Map<>();
        hashMap = new HashMap<>();
        treeMap = new TreeMap<>();
        linkedHashMap = new LinkedHashMap<>();
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testMap(Blackhole blackhole) {
            
            for (int i = 0; i < appendIterations; i++) {
                map.put("key" + i, i);
            }
    
            map.get(key1);
            map.get(key2);
            map.get(key3);
    
            blackhole.consume(map);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testHashMap(Blackhole blackhole) {
            
            for (int i = 0; i < appendIterations; i++) {
                hashMap.put("key" + i, i);
            }
    
            hashMap.get(key1);
            hashMap.get(key2);
            hashMap.get(key3);
    
            blackhole.consume(hashMap);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testTreeMap(Blackhole blackhole) {
            
            for (int i = 0; i < appendIterations; i++) {
                treeMap.put("key" + i, i);
            }
    
            treeMap.get(key1);
            treeMap.get(key2);
            treeMap.get(key3);
    
            blackhole.consume(treeMap);
    }

    @Benchmark
    @Fork(value = 2, warmups = 2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 3)
    public void testLinkedHashMap(Blackhole blackhole) {
            
            for (int i = 0; i < appendIterations; i++) {
                linkedHashMap.put("key" + i, i);
            }
    
            linkedHashMap.get(key1);
            linkedHashMap.get(key2);
            linkedHashMap.get(key3);
    
            blackhole.consume(linkedHashMap);
    }

}
