DRASTARU FLORINA CRISTINA 325CA\
VRABIE ANDREEA IRINA 325CA

**

## CHESS ENGINE PROJECT

**

*For this stage of the project, we implemented an engine for a minimalist Chess Game in Java, using XBoard platform.* 

**COMPILING INSTRUCTIONS**

 - The Makefile contains 3 rules: build, run, clean. 
 - To give a command, you have to go in *src* folder. 
 - Then, you have to give the commands
   **make build** and **make run**.

**PROJECT STRUCTURE**

The types of Chess Pieces are stored in *pieces* package. 
Each piece is represented by a class, having an index, a colour, 
an initial position and extends the *abstract class ChessPiece*.

We implemented the board in *class ChessBoard* as a 8x8 matrix, 
using Singleton pattern and we initiated it according to Chess rules.

Every command received from xboard is implemented in a separate class, that implements the *Command interface*.

The flow of the game is described in *class Chess Game*, where we implemented:

 -  *move* function - the move that the engine makes after it receives the move of the xboard;
 - *oppMove* function - analyze if the move from the xboard is correct and make changes on the board.

In order to give a command to xboard or interpret one from it, 
we store the values of the columns in a HashMap. 

The game is also defined by a colour(black is default), that can be 
set by a command(*Black/White*) and it swaps the teams of the xboard and engine. 

The commands *force* and *go* are complementary, 
so we marked them through the same boolean variable.

***Obs**-implementation is just for the **Pawn** piece at this stage of the project.*

**DIVISION OF RESPONSABILITIES**

We worked together mostly, we improved each others code and 
we came up with solutions when it was necessary.

 - Florina - Contribution to the implemantation  of the board, the commands, the game
 - Irina - Contribution to the implementation of the game, the pieces, the commands

**SOURCES OF INSPIRATION**\
-GNU XBoard manual
