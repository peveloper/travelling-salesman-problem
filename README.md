# Travelling-Salesman-Problem
A simple Java implementation for the TSP using the ant-colony system.

## Usage
```
$ mvn compile
$ mvn -q exec:java -Dexec.mainClass="tsp.antcolony.Main" -Dexec.args="<PROBLEM_FILENAME.TSP>"
```
Will produce the output in [results](https://github.com/peveloper/travelling-salesman-problem/tree/master/results) directory.

## TODO
- ~~Implement 2-OPT or similar.~~ **Done**
- ~~Implement Ant-Colony.~~ **Done**

## Goal
**Achieve a relative error from the best known results less than 1%**

## License
GNU GPL 2.0 © [peveloper](https://www.github.com/peveloper)
