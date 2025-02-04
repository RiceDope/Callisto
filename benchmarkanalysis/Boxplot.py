import numpy as np
import matplotlib.pyplot as plt
import json

path = "c:/Users/Rhysw/Documents/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/030225-1530.json"
file = open(path, "r")
data = json.loads(file.read())
file.close()

# First join into two seperate lists

benchmarks = []

for benchmark in data:
    rawData = benchmark["primaryMetric"]["rawData"]
    benchmark = []
    for dt in rawData:
        benchmark += dt
    benchmarks.append(benchmark)

del benchmarks [2]

names = list(["Comparator Given", "No Comparator Given"])
plt.boxplot(benchmarks, tick_labels=names)
plt.xlabel("Benchmark")
plt.ylabel("OPS/ms")
plt.ylim(bottom=0)
plt.title("Map Keys Performance")
plt.savefig(f"MapKeysPerformanceSecondWithoutRegSeq.png", bbox_inches="tight")
plt.show()