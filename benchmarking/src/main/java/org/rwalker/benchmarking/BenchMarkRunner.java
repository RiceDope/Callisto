package org.rwalker.benchmarking;

/**
 * All benchmarks can be run from here
 */

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchMarkRunner {

    /**
     * Run the benchmarks
     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include("BenchMarkSequence.testSequenceAppend1000Size")
                .include("BenchMarkArrayList.testArrayListAppend1000WithSize")
                .resultFormat(ResultFormatType.JSON)
                .result("231224-2200.json")
                .build();

        new Runner(opt).run();
    }
}
