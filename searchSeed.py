#!/usr/bin/python

import sys
import os
import time

problem = sys.argv[1]
output = sys.argv[2]

bestSeed = -1
bestErr = 2
bestTime = 1


def notify(title, subtitle, message):
    t = '-title {!r}'.format(title)
    s = '-subtitle {!r}'.format(subtitle)
    m = '-message {!r}'.format(message)
    os.system('terminal-notifier {}'.format(' '.join([m, t, s])))


def printFile():
    notify("Seed Search", "new seed", str(bestErr)+" - "+bestSeed+" - "+ str(bestTime))
    with open(output, "a") as file:
        file.write(str(bestErr)+" - "+bestSeed+" - "+str(bestTime)+"\n")

with open(output, "a") as file:
    file.write("\n----------------------------------------------\n")

while True:
    start = time.time()
    retValues = os.popen("java -cp target/classes/antcolony Main " + problem ).readlines()
    end = time.time()
    err = float(retValues[0].strip())
    seed = retValues[1].strip()
    if err == bestErr:
        if bestTime > end-start:
            bestTime = end-start
            bestSeed = seed
            printFile()
    if err < bestErr:
        bestErr = err
        bestTime = end-start
        bestSeed = seed
        printFile()
