# Question and Answers - Assignment 1 2022

Q1: Should the assignment code run without changes

**A1:** No the assignment code has been stripped of many necessary functions.  You need to fill these in to get it working. The tutorial will help showing you working code that is similar.

Q2: There appears to be an error in the data in the `stop_pattern.txt` file there is a stop listed that is not in the `stop.txt`.  What do I do?

**A2:** This is common in real world data.  You have options, which you would offer to the client, 1. Skip the stop and assume and continue with the `stop_pattern` (This is what I did). 2, Skip the entire stop pattern. 3, Stop the program working.  Option 3 is really only if the missing data is critical and would cause system failure in the real world if the error was allowed to persist.

Q3: Does the code run out of the box?

**A3:** No.  You need to add the data structures to the code in the Graph, the parsers and the drawing area.

Q4: Do the edges only go in one direction

**A4:** Yes.  The stop_pattern has the order of the stops and they run in that direction.  This is a directed graph not an undirected graph.

Q5: When highlighting trips that go through a selected stop, should everything remain highlighted if you zoom in or out or move the view? Or is it okay if it stops being highlighted?

**A5:** It if fine to have it highlighted or to have it turn off.

Q6: Should right and left move our view of the map or should it move the map itself?

**A6:** The view of the map. Ie the origin


