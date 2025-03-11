package org.rwalker.benchmarking.FinalBenchmarks;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.Runner;

/**
 * A comprehensive class that features all benchmarks that should be run for the final writeup
 */


public class RunAllWriteupBenchmarks {
    
    public static void main(String[] args) throws Exception {

        // Sorted benchmarks for Sequence
        Options opt = new OptionsBuilder()
                    .include("SortedBenchmarks")
                    .resultFormat(ResultFormatType.JSON)
                    .result("SortedBenchmarks.json")
                    .build();

        new Runner(opt).run();

        // Strategies benchmarking for Sequence
        opt = new OptionsBuilder()
                    .include("SequenceStrategiesArrayList")
                    .resultFormat(ResultFormatType.JSON)
                    .result("SequenceStrategiesArrayList.json")
                    .build();

        new Runner(opt).run();

        // Optimal values for Sequence
        opt = new OptionsBuilder()
                    .include("SequenceOptimalValues")
                    .resultFormat(ResultFormatType.JSON)
                    .result("SequenceOptimalValues.json")
                    .build();
        
        new Runner(opt).run();

        // Stack benchmarks for Sequence
        opt = new OptionsBuilder()
                    .include("Stack")
                    .resultFormat(ResultFormatType.JSON)
                    .result("Stack.json")
                    .build();

        new Runner(opt).run();

        // Queue benchmarks for Sequeunce
        opt = new OptionsBuilder()
                    .include("Queue")
                    .resultFormat(ResultFormatType.JSON)
                    .result("Queue.json")
                    .build();

        new Runner(opt).run();

        // Swapping strategies
        opt = new OptionsBuilder()
                    .include("StrategySwap")
                    .resultFormat(ResultFormatType.JSON)
                    .result("StrategySwap.json")
                    .build();
        
        new Runner(opt).run();

        // Swapping states
        opt = new OptionsBuilder()
                    .include("StateSwapping")
                    .resultFormat(ResultFormatType.JSON)
                    .result("StateSwapping.json")
                    .build();
        
        new Runner(opt).run();

        // Map vs JCF Map
        opt = new OptionsBuilder()
                    .include("MapVJCF")
                    .resultFormat(ResultFormatType.JSON)
                    .result("MapVJCF.json")
                    .build();

        new Runner(opt).run();

        // Map optimal values
        opt = new OptionsBuilder()
                    .include("MapOptimalValues")
                    .resultFormat(ResultFormatType.JSON)
                    .result("MapOptimalValues.json")
                    .build();

        new Runner(opt).run();

        
    }
}
