

import java.util.ArrayList;

public class Solver {
	
	public static Game makeCopy(Game g)
	{
		Game temp = new Game(4);
		boolean turn;
		int[][] newBoard = new int[2][7];
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				newBoard[i][j] = g.getBoard()[i][j];
			}
		}
		
		if(g.getTurn()) turn = true;
		else turn = false;
		
		temp.setBoard(newBoard);
		
		temp.setTurn(turn);
		return temp;
	}
	
//	public static MoveSets simulateGame(Game g)
//	{
//		MoveSets ms = new MoveSets();
//		for(int i = 0; i < 6; i++)
//		{
//			Game tempG = makeCopy(g);
//			Move tempMove = tempG.move(i);
//			
//			if(tempMove.getValid())
//			{
//				ms.addMove(tempMove);
//				
//				if(tempMove.getMoveagain())
//				{
//					ms.getMove(tempMove.space).addRemove(simulateGame(tempMove.getGame()));
//				}
//			}
//		}
//		return ms;
//	}
	
	public static MoveSets simulateGame(Game g)
	{
			
		MoveSets ms = new MoveSets();
		Game tempG = makeCopy(g);
		Move tempMove;
		for(int i = 0; i < 6; i++)
		{
			tempG = makeCopy(g);
			tempMove = tempG.move(i);
			
			if(tempMove.getValid())
			{
				if(tempMove.getMoveagain())
				{
					//issue here  - not working !!!!
					tempMove.addRemove(simulateGame(tempMove.getGame()));
					ms.addMove(tempMove);
				}
				else
				{
					ms.addMove(tempMove);
				}
			}
		}
		return ms;
	}
	
	public static ArrayList<Game> createGameResponse(MoveSets ms)
	{
		ArrayList<Game> gl = new ArrayList<>();
		
		for(Move m : ms.getMoves())
		{
			if(m.getMoveagain())
			{
				for(Move s : m.getRemove().getMoves())
				{
					gl.add(s.getGame());
				}
			}
			else
			{
				gl.add(m.getGame());
			}
		}
		
		return gl;
	}
	
	public static void addMoves(Move r)
	{
		ArrayList<Game> passOne, passTwo = new ArrayList<>(), passThree = new ArrayList<>(), passFour = new ArrayList<>();

		passOne = new ArrayList<>(createGameResponse(simulateGame(r.getGame())));

		for(Game g : passOne)
		{
			passTwo.addAll(createGameResponse(simulateGame(g)));
		}

		for(Game g : passTwo)
		{
			passThree.addAll(createGameResponse(simulateGame(g)));
		}

		for(Game g : passThree)
		{
			passFour.addAll(createGameResponse(simulateGame(g)));
		}

		r.endStates.addAll(passFour);
	}

	public static int[] findOptimal(Game g)
	{
		int[] values = new int[6];
		int[] best = new int[5], repeatMove = new int[3];

		for(int i = 0; i < 6; i++)
		{
			Game k = makeCopy(g);
			Move m = k.move(i);
			if(!m.getValid())
			{
				values[i] = -1;
			}
			else if(m.getMoveagain())
			{
				int[] nestedMove = new int[6];
				for(int j = 0; j < 6; j++)
				{
					Move firstPass = makeCopy(m.getGame()).move(j);
					addMoves(firstPass);
					nestedMove[j] = scoreAlg(firstPass);
				}

				int bestTotal = 0;
				for(int j = 0; j < 6; j++)
				{

					if(nestedMove[j] >= bestTotal)
					{
						repeatMove[0] = i;
						repeatMove[1] = j;
						repeatMove[2] = nestedMove[j];
						bestTotal = nestedMove[j];
					}
				}

				values[i] = repeatMove[2];

			}
			else
			{
				addMoves(m);
				values[i] = scoreAlg(m);
			}
		}

		int bestTotal = 0;
		for(int i = 0; i < 6; i++)
		{
			if(values[i] >= bestTotal)
			{
				bestTotal = values[i];
				best[0] = i;
				if(repeatMove[0] == i)
				{
					best[1] = repeatMove[1];
				}
				else
				{
					best[1] = -1;
				}
			}
		}
		best[4] = bestTotal;
		return best;
	}

	public static int scoreAlg(Move m)
	{
		int max = 0, count = 0;
		for(Game h : m.getEndStates())
		{
			if(h.getBoard()[0][6] > m.getGame().getBoard()[0][6] + 1)
			{
				count++;
			}

			if(h.getBoard()[1][6] > m.getGame().getBoard()[1][6] + 1)
			{
				count--;
			}

			if(h.getBoard()[0][6] > max)
			{
				max = h.getBoard()[0][6];
			}
		}
		return (count + max);
	}

	
	public static Move bestMove(Game g)
	{
		MoveSets passOne = simulateGame(g);
		
		Move moveAgain;
		
		int best = 0, sum = 0, total = 0;
		Move bestMove = null, bestMoveTwo = null;
		
		for(int i = 0; i < passOne.getMoves().size(); i++)
		{
			sum = 0;
			total = 0;
			
			for(Move m : passOne.getMoves())
			{
				addMoves(m);
			}
			
			for(Move m : passOne.getMoves())
			{
				if(m.getMoveagain())
				{
					for(Move r : m.getRemove().getMoves())
					{
						bestMove(r.getGame());
					}
				}
				for(Game t : m.endStates)
				{
					sum += t.getBoard()[1][6];
					total++;
				}
			}
		}
		
		return bestMove;
	}
	
	public static void main(String[] args)
	{
		
		
		
		
		
	}
}
