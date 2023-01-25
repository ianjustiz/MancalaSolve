import java.io.IOException;
import java.util.ArrayList;


public class MoveSets {
	ArrayList<Move> moves = new ArrayList<>();
	
	public void addMove(Move move)
	{
		moves.add(move);
	}
	
	public Move getMove(int pos)
	{
		for(Move m : moves)
		{
			if(m.space == pos)
			{
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Move> getMoves()
	{
		return moves;
	}
}
