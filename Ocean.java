package battleship;
import java.util.Random;

public class Ocean {

	//used to quickly determine which ship is, given any location
	private Ship[][] ships = new Ship[10][10]; 
	//total number of shots fired by the user
	private int shotsFired; 
	//the number of times a shot hit a ship. if the user shoots the same part of a ship more than once. still count the shots
	private int hitCount; 
	//number of ships sunk
	private int shipsSunk;


	/**
	 * constructor for the ocean class
	 */
	public Ocean(){
		// initialize values of shotsFired, hitCount, shipsSunk
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++ ) {
				EmptySea emptysea = new EmptySea();
				this.ships[i][j] = emptysea;
				//initialize the bow of the ship in the ocean if ship is vertical
				emptysea.setBowRow(i);
				//initialize the bow of the sip in the ocean if ship is horizontal
				emptysea.setBowColumn(j);
				emptysea.setHorizontal(true);
			}
		}
	}

	/**
	 * Place all 10 ships randomly on the ocean.Place larger ships before the smaller ones
	 */
	void placeAllShipsRandomly() {
		// place the battleship

		Random random = new Random();
		int rRow = random.nextInt(10);
		int rCol = random.nextInt(10);
		boolean rHorizontal = true;

		//Battleship
		Ship battleship = new Battleship();
		while (battleship.okToPlaceShipAt(rRow, rCol, rHorizontal, this) == false) {
			if (random.nextInt(2) == 1) {
				rHorizontal = true;
			} else {
				rHorizontal = false;
			}
			rRow = random.nextInt(10);
			rCol = random.nextInt(10);
		}
		battleship.placeShipAt(rRow, rCol, rHorizontal, this);

		//Cruiser
		for (int i = 1; i <= 2; i++) {
			Ship cruiser = new Cruiser();
			while (cruiser.okToPlaceShipAt(rRow, rCol, rHorizontal, this) == false) {
				if (random.nextInt(2) == 1) {
					rHorizontal = true;
				} else {
					rHorizontal = false;
				}
				rRow = random.nextInt(10);
				rCol = random.nextInt(10);
			}
			cruiser.placeShipAt(rRow, rCol, rHorizontal, this);
		}

		//Destroyer
		for (int i = 1; i <= 3; i++) {
			Ship destroyer = new Destroyer();
			while (destroyer.okToPlaceShipAt(rRow, rCol, rHorizontal, this) == false) {
				if (random.nextInt(2) == 1) {
					rHorizontal = true;
				} else {
					rHorizontal = false;
				}
				rRow = random.nextInt(10);
				rCol = random.nextInt(10);
			}
			destroyer.placeShipAt(rRow, rCol, rHorizontal, this);
		}

		//Submarine
		for (int i = 1; i <= 4; i++) {
			Ship submarine = new Submarine();
			while (submarine.okToPlaceShipAt(rRow, rCol, rHorizontal, this) == false) {
				if (random.nextInt(2) == 1) {
					rHorizontal = true;
				} else {
					rHorizontal = false;
				}
				rRow = random.nextInt(10);
				rCol = random.nextInt(10);
			}
			submarine.placeShipAt(rRow, rCol, rHorizontal, this);
		}

	}


	/**
	 * 
	 * @param row
	 * @param column
	 * @return true if the given location contains a ship, false if does not
	 */
	boolean isOccupied(int row, int column){
		if(this.ships[row][column].getShipType().equals("empty")){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @return true of the given location contains "real" ship, still afloat. false if it does not
	 * updates the number of shots that have been fired, and the number of hits
	 */
	boolean shootAt(int row, int column) {
		this.shotsFired += 1;

		//shooting at empty sea
		if (this.ships[row][column].getShipType().equals("empty")) {
			return false;
		} else {
			if (this.ships[row][column].getHitLocation(row, column)) {
				return false;
			}
			this.hitCount += 1;
			this.ships[row][column].shootAt(row, column);
			if (this.ships[row][column].isSunk() == true) {
				this.shipsSunk++;}
			return true;
		}


	}

	/**
	 * returns the number of shots fried in the game
	 * @return
	 */
	int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * returns the number of hits recorded. all hits are counted, not just the first time given square is hit
	 * @return
	 */
	int getHitCount() {
		return this.hitCount;
	}

	/**
	 * returns the number of ships sunk
	 * @return
	 */
	int getShipsSunk() {
		return this.shipsSunk;

	}
	/**
	 * return if all ships are sunk, else return false
	 * @return
	 */
	boolean isGameOver() {
		if (this.getShipsSunk() == 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns 10x10 array of ships
	 * @return
	 */
	Ship[][] getShipArray(){
		return this.ships;
	}


	/**
	 * create an empty board 
	 */
	void emptyBoard() {
		//print the column numbers
		String [][] emptyboard = new String[11][11];
		emptyboard[0][0] = " ";

		for (int i = 1; i < 11; i++) {
			emptyboard[0][i] = " " + (i - 1);
			emptyboard[i][0] = (i - 1) + "";
			for (int j = 1; j < emptyboard.length; j++) {
				emptyboard[i][j] = " " + ".";
			}

		}
		for (int i = 0; i < emptyboard.length; i++) {
			for (int j = 0; j < emptyboard.length; j++) {
				System.out.print(emptyboard[i][j]);
			}
			System.out.println("");
		}
	}


	/**
	 * use to print the ocean
	 * 'S': ship got hit
	 * '-' shoot at water
	 * 'x' ship fully sunk
	 */

	void print() {
		//print out the column numbers
		System.out.print("  ");
		for (int i = 0; i < this.ships.length; i++) {
			System.out.print(i + " ");
		}

		//print out next line
		System.out.println("");

		//print out the row numbers
		for (int i = 0; i < this.ships.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < this.ships.length; j ++) {
				if (this.ships[i][j].isSunk() || this.ships[i][j].getHitLocation(i, j)) {
					System.out.print(this.ships[i][j].toString() + " ");
				}
				else {
					System.out.print("." + " ");
				}

			}
			System.out.println("");
		}


	}
}

