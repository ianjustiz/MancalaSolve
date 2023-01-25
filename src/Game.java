public class Game {
	//Side 1(0) is for first player
	//Side 2(1) is for second player
	//6 is the 'store' which determines score.
	int[][] board = new int[2][7];
	int numInitialPieces;
	
	//Possibly select game rule (enum?)
	String gameRule;
	
	//True means player 1 goes, False means player 2
	boolean currTurn;
	
	public Game(int initialPieces)
	{
		numInitialPieces = initialPieces;
		currTurn = true;
		
		makeBoard();
	}
	
	public void makeBoard()
	{
		for(int i = 0; i < (2); i++)
		{
			for(int j = 0; j < 6; j++)
			{
				board[i][j] = numInitialPieces;
			}
		}
	}
	
	public void setBoard(int[][] board)
	{
		this.board = board;
	}
	
	public int[][] getBoard()
	{
		return this.board;
	}
	
	public void setTurn(boolean turn)
	{
		currTurn = turn;
	}
	
	public boolean getTurn()
	{
		return currTurn;
	}
	

	
	public Move move(int space)
	{
		int initSpot = space;
		int preScore = board[turnInt(currTurn)][6];
		int mover = board[turnInt(currTurn)][space];
		int movingSide = turnInt(currTurn);
		boolean reMove = false;
		
		board[turnInt(currTurn)][space] = 0;

		if(mover == 0) return new Move(0, false, false, space, this);
		
		int i = space + 1;
		for(; mover > 0; mover--)
		{
			if(i == 6)
			{
				if(movingSide != turnInt(currTurn))
				{
					if(movingSide == 1) movingSide = 0;
					else movingSide = 1;
					i = 0;
				}
			}
			if(i > 6)
			{
				if(movingSide == 1) movingSide = 0;
				else movingSide = 1;
				i = 0;
			}
			board[movingSide][i] += 1;
			i++;

		}
		
		if(movingSide == turnInt(currTurn) && i-1 == 6)
		{
			reMove = true;
		}
		else if(board[movingSide][i-1] == 1 && i-1 != 6)
		{
			if(movingSide == turnInt(currTurn))
			{
				board[movingSide][6] += board[turnInt(!currTurn)][6-(i-1)];
				board[turnInt(!currTurn)][6-(i-1)] = 0;
			}
		}
		
		this.currTurn = !currTurn;
		return new Move(board[turnInt(currTurn)][6] - preScore, true, reMove, initSpot, this);
	}
	
	private int turnInt(boolean turn)
	{
		if(turn) return 0;
		return 1;
	}
	
	public void printBoard()
	{
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				System.out.print(board[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		int[][] temp = {{0,0,0,0,4,4,4},{4,4,4,4,4,4,4}};
		Game test = new Game(4);
		//test.move(2);

		//Game elle = new Game(4);
		test.setBoard(temp);
		test.printBoard();
		//elle.printBoard();
		int[] solved = Solver.findOptimal(test);
		System.out.println(solved[0]);
		System.out.println(solved[1]);
		System.out.println(solved[4]);

	}
}
