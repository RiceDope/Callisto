import matplotlib.pyplot as plt
import numpy as np
import json

path = "c:/Users/Rhysw/Documents/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/221224-2330.json"
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

fig, axs = plt.subplots(ncols=2)
fig.suptitle("ArrayList vs Sequence Performance")

print(benchmarks)

unsorted = []
unsorted.append(benchmarks[0])
unsorted.append(benchmarks[2])
unsortedNames = ["ArrayList", "Sequence"]

sorted = []
sorted.append(benchmarks[1])
sorted.append(benchmarks[3])
sortedNames = ["ArrayList", "Sequence"]

# Now plot a boxplot on the first axis
axs[0].boxplot(unsorted)
axs[0].set_title("Unsorted")
axs[0].set_ylim(bottom=0)  # Set y-axis to start from 0
axs[0].set_xticklabels(unsortedNames)
axs[0].set_ylabel("OPS/ms")
axs[0].grid()
axs[1].boxplot(sorted)
axs[1].set_title("Sorted")
axs[1].set_ylim(bottom=0)  # Set y-axis to start from 0
axs[1].set_xticklabels(sortedNames)
axs[1].set_ylabel("OPS/ms")
axs[1].grid()
fig.tight_layout()
plt.savefig(f"ArrayListSequencePerformance.png")
plt.show()