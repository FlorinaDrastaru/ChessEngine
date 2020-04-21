

DRASTARU FLORINA CRISTINA 325CA\
VRABIE ANDREEA IRINA 325CA


**

## CHESS ENGINE PROJECT

**

*Initially, we implemented an engine for a minimalist Chess Game in Java, using XBoard platform. For this stage of the project, we improved our engine: we added all the rules of a Chess Game and we used a NegaMax algorithm in order to play better.*

**COMPILING INSTRUCTIONS**

 - The Makefile contains 3 rules: build, run, clean. 
 - To give a command, you have to go in *src* folder. 
 - Then, you have to give the command **make build**.
 -  **make run** will be applied implicitly, when you introduce the testing command.

**PROJECT STRUCTURE**

***The Chess Pieces***

The types of Chess Pieces are stored in *pieces* package. 

Each piece is represented by a class and extends  the *abstract class ChessPiece*.

Each piece has a colour, a worth,  a rating that depends on each possible position on the table and a variable that says if the piece is at its first move or not. For some(Pawn, King, Queen) we also set an index.

Every piece implements the method *getMoves(Position pos)*, with the purpose to return a list with all possible moves that a piece can make from a certain position.

We took into account the Chess rules, including Pawn Promotion, En Passant, Castling.

In parent class *ChessPiece* , the method *move(Position pos)* moves a piece on board: takes it out from its current position and puts it on the new position *pos*.

***<u>Obs</u>***
 - *Position* is represented by 2 coordinates: row and column;
 - *Move* is represented by 2 *Position* objects, that represent the source and the destination.

***The Chess Board***

We implemented the board in *class ChessBoard* as a 8x8 matrix,  using Singleton pattern and we initiated it according to Chess rules.

On the board, we can do several operation ilustrated by the methods *getChessPiece(Position pos)*, *takeOutChessPiece(Position pos)*, *putChessPiece(ChessPiece piece, Position pos)*. 

We also keep track of the position of the Kings and depending on the colour with which the engine plays, we return the King of that colour.

***The Commands***

The commands from Xboard are received in Main and are sent to Solver to be responded.

Every command received from xboard is implemented in a separate class, that implements the *Command* interface.

***The Chess Game***

The flow of the game is described by the implementation of the classes from *game* package. 

<u> **Check** </u>

The methods in this class have the purpose to check the state of a piece from a position. So, we can find out if a piece is attacked and if yes, we can find out which are the moves that can be made in order to save the piece.
 
 The are also methods that move a piece from a position to other one and eventually, undo it, restoring the board to the previous state.

<u> **ChessGame**</u>

We have 2 important methods in this class, that help us realize the connection between receiving a move and making a move on the chess board.

 - In *move* method we apply the negaMax(set for the engine's teams, with depth = 3(verifies 3 steps ahead)) method from MoveAlgorithm class, that returns the best move and after that, we make that move.
 
 - In *oppMove* function we analyze if the move from the xboard is correct and make changes on the board. The take into consideration special cases, such as castling, pawn promotion or en passant In case of moving the king, we update its position.

*<u>Other details about game (first stage of project)</u>*

In order to give a move to xboard or interpret one from it, 
we store the values of the columns in a HashMap. 

The game is also defined by a colour(black is default), that can be 
set by a command(*Black/White*) and it swaps the teams of the xboard and engine. 

The commands *force* and *go* are complementary, 
so we marked them through the same boolean variable.

<u> **MoveAlgorithm**</u>

We followed the steps of a classic **NegaMax** algorithm and we implemented all the necessary methods. We search for the best move from every possible move(we make every move, check the score and then undo it), we take into consideration to keep the king safe, we evaluate the game state, we check if the game is over, we find the winner.

In order to evaluate how good a move is, we set a rating for every chess piece on every spot on the board. We also consider important in this evaluation the safety of the king, the number of pieces left, the number of the moves that the team can make(the more, the better), the points of the opponent.


**DIVISION OF RESPONSABILITIES**

We worked together mostly, we improved each others code and we came up with solutions when it was necessary.

**SOURCES OF INSPIRATION**
- GNU XBoard manual
- https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/

