import matplotlib.pyplot as plt
import numpy as np
import json
import os

# Pull the data from file
cwd = os.path.abspath(os.getcwd())
cwd = cwd + "/benchmarking"
cwd = cwd + "/" + input("Enter the filen name: \n")
print(cwd)

# Open the file
file = open(cwd, "r")
json_data = json.loads(file.read())

# First line is ArrayList instantiation
# Second line is HashMap instantiation
# Third line is Sequence instantiation
# Fourth line is Map instantiation

# Get the data
array_list = json_data[0]["primaryMetric"]["rawData"][0]
hash_map = json_data[1]["primaryMetric"]["rawData"][0]
sequence = json_data[2]["primaryMetric"]["rawData"][0]
map = json_data[3]["primaryMetric"]["rawData"][0]
print(sequence)

xPlot = [1, 2, 3, 4, 5]

# Plot the data
plt.plot(xPlot, array_list, label="ArrayList")
plt.plot(xPlot, hash_map, label="HashMap")
plt.plot(xPlot, sequence, label="Sequence")
plt.plot(xPlot, map, label="Map")
plt.xlabel("Run number")
plt.ylabel("Ops/s")
plt.legend()
plt.show()