import json
import matplotlib.pyplot as plt

# Filepath to the JSON file
file_path = 'h:/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/231224-2200.json'

x = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

# Read the JSON file
with open(file_path, 'r') as file:
    data = json.load(file)

for benchmark in data:
    name = str(benchmark["benchmark"]).split(".")[-1] + " " + benchmark["params"]["initialSize"]
    results = benchmark['primaryMetric']['rawData'][0] + benchmark['primaryMetric']['rawData'][1]
    plt.plot(x, results, label=name)

# Set labels and title
plt.xlabel('Run')
plt.ylabel('Score (ops/ms)')
plt.title('Benchmark Results')

# Position the legend off to the side
plt.legend(loc='upper center', bbox_to_anchor=(0.5, -0.15), ncol=2)

# Adjust layout to make room for the legend
plt.tight_layout(rect=[0, 0, 1, 0.95])

# Save the plot
plt.savefig(f"tbn.png")

# Show the plot
plt.show()