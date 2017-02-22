# Notes for Paper

## Notation

$$
P=[p_x,p_y] \\
X=[P,b]=[p_x,p_y,b] \\
W=[w_p,w_b]
$$

## Main Points

### 1.1 Introduction

### 1.2 Problem Description

### 1.3 Problem Formalization

### 2 Fiddling with the Reward Function

### 2.1 Basic Reward Function

### 2.2 Branching Reward Function

### 2.3 Bitrate Reward

### 3 Open Question





## Writing the text

The problem consists of a mobile agent that can move freely in a bounded plane. The agent starts at a staring point **A** and needs to reach an end point **B** which will be referred to as the **goal**. The position of the robot is represented by the vector **P**. In addition to the position of the robot there is an internal variable denoted by **b**, which denotes the size of a buffer of bits.  The robot can send out those bits to a nearby **base station**, the robot is only capable of sending bits to the **base station** when it is in range of the base station, and when the robot is in range, the bits are sent according to a **transmission function**. The purpose of the robot is to reach the **goal** having zero bits remaining in its buffer.	

We decided to tackle this problem using Dynamic Programming and Fuzzy Q Iteration. 

The first step was to implement a Markov Decision Process.

By changing the rewards the robot gets, we are able to indirectly influence its behavior by adding weights to certain actions, like getting closer to the **goal** or sending off a number of bits. By modifying the weights and the actions that are to be awarded.

We have come up with three possible reward functions. 

The first one is the most basic form of the reward function. 

Another method is to reward the robot based on the number of bits sent. We do this by calculating the change in bits, and giving a positive reward proportional to it. This means that the weight needs to be large enough to reward the robot for small changes in its buffer.

The third method is a discontinuous one, by adding a large positive reward whenever the robot is transmitting bits, no matter the speed, we can make the robot reliably send all of its bits before reaching the **goal**.
