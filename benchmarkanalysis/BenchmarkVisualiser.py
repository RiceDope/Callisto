import matplotlib.pyplot as plt
import numpy as np
import json
import os

# Program will visualise a standard benchmark from the JMH benchmarking tool
# It will dynamically read each benchmark name and show the raw data
# Ignore the Y axis label if each benchmark uses a different unit of measurement (For best results keep them consistent)

def pullJsonData():
    # Pull the data from file
    cwd = os.path.abspath(os.getcwd())
    cwd = cwd + "/benchmarking"
    cwd = cwd + "/" + input("Enter the filen name: \n")
    print(cwd)

    # Open the file
    file = open(cwd, "r")
    json_data = json.loads(file.read())

    return json_data

def visualiseSelects(json_data):
    print(f"There are {len(json_data)} benchmarks to choose from, Type how many you would like to compare")
    toCompare = int(input())

    # Get the names of the benchmarks
    benchmarkNames = []
    for x in range(len(json_data)):
        benchmarkNames.append(json_data[x]["benchmark"])
        benchmarkNames[x] = benchmarkNames[x].split(".")[len(benchmarkNames[x].split("."))-1]

        # Get the name of the specific benchmark being used
        benchmarkName = json_data[x]["benchmark"]
        benchmarkName = benchmarkName.split(".")[len(benchmarkName.split("."))-1]
        if "params" in json_data[x]:
            benchmarkName += "("
            for param in json_data[x]["params"].keys():
                benchmarkName += f" {param} {json_data[x]['params'][param]}"
            benchmarkName += ")"
    
    print("The benchmarks are:")
    print(benchmarkNames)
    print("The program will ask you as many times as each benchmark you would like. Type its index and it will be added to the graph")

    benchmarks = ""

    rawDatas = [] # Will be a 2D array

    for x in range(toCompare):
        indexToPlot = int(input("Enter the index of the benchmark you would like to plot:"))
        rawData = json_data[indexToPlot]["primaryMetric"]["rawData"]
        collectedList = []
        for this_list in rawData:
            collectedList += this_list

        rawDatas.append(collectedList)

        # Get the name of the specific benchmark being used
        benchmarkName = json_data[indexToPlot]["benchmark"]
        benchmarkName = benchmarkName.split(".")[len(benchmarkName.split("."))-1]
        benchmarks += benchmarkName + " "

        # Plot the data against the number of total iterations here
        plt.plot(list(range(1, len(collectedList)+1)), collectedList, label=benchmarkName)

    # Give textual output of important data

    # Calculate average difference between each run
    if len(rawDatas) == 2:
        differences = np.abs(np.array(rawDatas[0]) - np.array(rawDatas[1]))
        average_difference = np.mean(differences)
    else:
        average_difference = "N/A"
    print(f"The average difference between the two benchmarks is {average_difference}")

    # Give the graph a name of the comparisons being benchmarked
    plt.legend()
    plt.xlabel("Run number")
    plt.ylabel("Ops/ms")
    if len(benchmarks) + len(os.path.abspath(os.getcwd())+"/benchmarks") > 260:
        benchmarks = benchmarks[:250-len(os.path.abspath(os.getcwd())+"/benchmarks")]
    plt.savefig(f"{benchmarks}.png")
    plt.show()

    

def visualiseSeparately(json_data):
    # Check how many benchmarks we have to visualise
    benchmarks = len(json_data)
    for x in range (benchmarks):
        rawData = json_data[x]["primaryMetric"]["rawData"]
        collectedList = []
        for this_list in rawData:
            collectedList += this_list

        # Get the name of the specific benchmark being used
        benchmarkName = json_data[x]["benchmark"]
        benchmarkName = benchmarkName.split(".")[len(benchmarkName.split("."))-1]

        # Get the name of the specific benchmark being used
        benchmarkName = json_data[x]["benchmark"]
        benchmarkName = benchmarkName.split(".")[len(benchmarkName.split("."))-1]
        if "params" in json_data[x]:
            benchmarkName += "("
            for param in json_data[x]["params"].keys():
                benchmarkName += f" {param} {json_data[x]['params'][param]}"
            benchmarkName += ")"

        # Plot the data against the number of total iterations here
        plt.plot(list(range(1, len(collectedList)+1)), collectedList)
        plt.xlabel("Run number")
        plt.ylabel("Ops/ms")
        plt.title(benchmarkName)
        plt.show()


def visualiseTogether(json_data):
    # Check how many benchmarks we have to visualise
    benchmarks = len(json_data)
    benchmarkNames = ""
    for x in range (benchmarks):
        rawData = json_data[x]["primaryMetric"]["rawData"]
        collectedList = []
        for this_list in rawData:
            collectedList += this_list

        # Get the name of the specific benchmark being used
        benchmarkName = json_data[x]["benchmark"]
        benchmarkName = benchmarkName.split(".")[len(benchmarkName.split("."))-1]
        if "params" in json_data[x]:
            benchmarkName += "("
            for param in json_data[x]["params"].keys():
                benchmarkName += f" {param} {json_data[x]['params'][param]}"
            benchmarkName += ")"
        benchmarkNames += benchmarkName + " "

        # Plot the data against the number of total iterations here
        plt.plot(list(range(1, len(collectedList)+1)), collectedList, label=benchmarkName)

    plt.legend(bbox_to_anchor=(1, 1))
    plt.xlabel("Run number")
    plt.ylabel("Ops/ms")
    plt.tight_layout()
    if len(benchmarkNames) + len(os.path.abspath(os.getcwd())+"/benchmarks") > 260:
        benchmarkNames = benchmarkNames[:250-len(os.path.abspath(os.getcwd())+"/benchmarks")]
    plt.savefig(f"{benchmarkNames}.png", bbox_inches="tight")
    plt.show()

def visualiseMultipleFiles():
    howManyFiles = int(input("Enter how many files you would like to visualise:"))
    json_data = []

    for x in range(howManyFiles):
        json_data += pullJsonData()

    visualiseSelects(json_data)

# PROGRAM MAIN LOOP

# Give a choice to the user (1 to visualise on one graph, 2 to step through visualisations)
choice = int(input("Enter 1 to visualise all benchmarks on one graph, or 2 to step through them or 3 to select, 4 allows for multiple more files to be specified:"))

if choice == 1:
    json_data = pullJsonData()
    visualiseTogether(json_data)
elif choice == 2:
    json_data = pullJsonData()
    visualiseSeparately(json_data)
elif choice == 3:
    json_data = pullJsonData()
    visualiseSelects(json_data)
elif choice == 4:
    visualiseMultipleFiles()
