

import java.util.ArrayList;

public class Move {
	int score, space;
	boolean valid;
	boolean moveagain;
	Game g;
	MoveSets ms = new MoveSets();
	ArrayList<Game> endStates = new ArrayList<>();
	
	public Move(int score, boolean valid, boolean moveagain, int space, Game g)
	{
		this.score = score;
		this.valid = valid;
		this.moveagain = moveagain;
		this.g = g;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public boolean getValid()
	{
		return valid;
	}
	
	public boolean getMoveagain()
	{
		return moveagain;
	}
	
	public int getSpace()
	{
		return space;
	}
	
	public void addRemove(MoveSets ms)
	{
		this.ms = ms;
	}
	
	public MoveSets getRemove()
	{
		return ms;
	}
	
	public Game getGame()
	{
		return g;
	}

	public ArrayList<Game> getEndStates() {return endStates;}

	public void setEndStates(ArrayList<Game> es) {this.endStates = es;}
}
