import numpy as np
import matplotlib.pyplot as plt

# Data
categories = ["Total Time", "Average Time", "Fastest Time", "Slowest Time"]
sequence_map = [59120, 11824, 4758, 33625]
arraylist_hashmap = [27826, 5565, 2307, 13139]

# Number of groups and bar width
x = np.arange(len(categories))  # X locations for the groups
width = 0.3  # Width of the bars

# Plot bars
fig, ax = plt.subplots()
bars1 = ax.bar(x - width/2, sequence_map, width, label="Sequence, Map", color='tab:blue')
bars2 = ax.bar(x + width/2, arraylist_hashmap, width, label="ArrayList, HashMap", color='tab:orange')

# Labels and formatting
ax.set_xlabel("Time Metrics")
ax.set_ylabel("Time (µs)")
ax.set_title("Benchmark Performance Comparison")
ax.set_xticks(x)
ax.set_xticklabels(categories)
ax.legend()

# Display the chart
plt.savefig("c:/Users/Rhysw/Documents/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/GroupedBarChartForJsonParse.png")
plt.show()
