# Place this in mermaidchart.com

flowchart 
    Q[Queue] -->|Pop / Push| S[Stack]
    Q --> |SortOnwards| PQ[PriorityQueue]
    Q --> |Append| ARR

    S --> |Enqueue / Dequeue| Q
    S --> |SortOnwards| PS
    S --> |Append| ARR

    PQ --> |StopSorting| Q
    PQ --> |Pop / Push| PS[PriorityStack]

    PS --> |Enqueue / Dequeue| PQ
    PS --> |StopSorting| S

    ARR[Array] --> |Push / Pop| S
    ARR --> |Enqueue / Dequeue| Q
    ARR --> |SortOnwards| SARR[SortedArray]
    
    SARR --> |StopSorting| ARR
    SARR --> |Push / Pop| PS
    SARR --> |Enqueue / Dequeue| PQ
