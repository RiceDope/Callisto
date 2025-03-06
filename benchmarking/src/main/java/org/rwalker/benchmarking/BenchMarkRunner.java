package org.rwalker.benchmarking;

/**
 * All benchmarks can be run from here
 * https://jmh.morethan.io/
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
                .resultFormat(ResultFormatType.JSON)
                .result("130225-1530.json")
                .build();

        new Runner(opt).run();
    }
}
