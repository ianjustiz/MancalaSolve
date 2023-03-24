# MancalaSolve
GUI-Based Mancala Solver

MancalaSolve consists of a full implementation of Mancala logic including captures, chained moves, and win conditions.
The board is presented via a Swing GUI allowing entry of present game states. On entry, the current game state is passed to the solving algorithm.

The solver is a simple recursive, greedy algorithm. It strives for a maximum shift in point difference over three moves,
simulating all possible responses to each of its moves. This is quite inefficient, and has an accuracy of approx. 60% relative to a Mancala engine.

This project was made during my senior year at Suncoast Community High and I aim to revisit it in the coming months.
