package battleship;

public class Battleship extends Ship{
static final int b_length = 4;


	@Override
	public int getLength() {
		return b_length;
	}

	@Override
	public String getShipType() {
		// TODO Auto-generated method stub
		return "battleship";
	}
}


