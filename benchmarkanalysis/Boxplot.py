import numpy as np
import matplotlib.pyplot as plt
import json

path = "c:/Users/Rhysw/Documents/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/040225-1040.json"
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

names = list(["Sequence","Sorted Sequence", "Set"])
plt.boxplot(benchmarks, tick_labels=names)
plt.xlabel("Benchmark")
plt.ylabel("OPS/ms")
plt.ylim(bottom=0)
plt.title("Contains method performance")
plt.grid()
plt.savefig(f"SetSequenceContainsPerformance.png", bbox_inches="tight")
plt.show()