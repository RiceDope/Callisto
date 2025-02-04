import numpy as np
import json

path = "c:/Users/Rhysw/Documents/Year Three/Dissertation/COMP6200-Modern-Collections-Library/benchmarking/030225-1430.json"
file = open(path, "r")
data = json.loads(file.read())
file.close()

# create dictionary to store the best value for each key
# Key will be indexed via bucketSize/expansionFactor/loadFactor
# b-e-l
bestValues = {}

# iterate through each data set
for dataSet in data:
    # get the key for the data set
    key = dataSet["params"]["buckets"] + "-" + dataSet["params"]["expansionFactor"] + "-" + dataSet["params"]["loadFactor"]
    # check if the key is in the dictionary
    if key in bestValues:
        # check if the value is greater than the current value
        if dataSet["primaryMetric"]["score"] > bestValues[key]:
            # update the value
            bestValues[key] = dataSet["primaryMetric"]["score"]
    else:
        # add the key and value to the dictionary
        bestValues[key] = dataSet["primaryMetric"]["score"]

sorted = dict(sorted(bestValues.items(), key=lambda item: item[1]))
print(sorted)
sortKeys = list(sorted.keys())

counter = len(sorted)
for i in range(10):
    counter -= 1
    key = sortKeys[counter]
    print(key + ": " + str(sorted[key]))