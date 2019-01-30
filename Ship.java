package battleship;



public class Ship {
	/**
	 *  the row that contains the bow (front part of the ship)
	 */
	private int bowRow;
	/**
	 * the column that contains the bow
	 */
	private int bowColumn;
	/**
	 *  the length of the ship
	 */
	private int length;
	/**
	 * a boolean that represents whether the ship is going to be placed horizontally or vertically
	 */
	private boolean horizontal;
	/**
	 * any array of 4 booleans that indicate whether that part  of the ship has been hit or not
	 */
	private boolean[] hit = {false, false, false, false}; 

	//methods

	/**
	 * get the length of a given ship
	 * @return
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * get the bow of the given vertical ship
	 * @return
	 */
	public int getBowRow() {
		return this.bowRow;
	}

	/**
	 * get the given bow of a given horizontal ship
	 * @return
	 */
	public int getBowColumn() {
		return this.bowColumn;
	}

	/**
	 * identify the location that get hit
	 */
	public boolean[] getHit() {
		return this.hit;
	}

	/**
	 * see if the direction of the ship is horizontal
	 * @return
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * get the type of the given ship
	 * @return
	 */
	public String getShipType() {
		return "";
	}


	/**
	 * set the length of ship
	 * @param x
	 */
	public void setLength(int x) {
		this.length = x;
	}


	/**
	 * sets the value of bowRow	
	 * @param row
	 */
	public void setBowRow(int row) {
		this.bowRow = row;
	}

	/**
	 * sets the value of bowColumn
	 * @param column
	 */
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}

	/**
	 * sets the value of the instance variable horizontal
	 * @param horizontal
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * * based on the given row, column, and orientation, returns true if it is okay to put a ship length
	 * with its bow in this location; false otherwise
	 * @param row between 0 and 9
	 * @param column between 0 and 9
	 * @param horizontal: ship placed horizontally or vertically
	 * @param ocean: an array 
	 * @return true or false - determines if it is legal to place the ship at the position
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int rowBeginning = 1;
		int rowEnd = 1;
		int colBeginning = 1; 
		int colEnd = 1;

		if (row == 0) {
			rowBeginning = 0;
		}
		if (column == 0) {
			colBeginning = 0;
		}
		if (row == 9) {
			rowEnd = 0;
		}
		if (column == 9) {
			colEnd = 0;
		}


		//in the case, the ship is submarine
		if (this.getLength() == 1) {
			if( this.getLength() + row > 10 || this.getLength() + column > 10) 
				return false;
		}

		else{
			//horizontal ship
			if (horizontal == true) {

				if(column + this.getLength()  > 10) {
					return false;}
				if(column + this.getLength() == 10) {
					colEnd = this.getLength() - 1;}
				else {
					colEnd = this.getLength();}

			}
			//vertical ship
			else {
				if (row + this.getLength() > 10) {
					return false;}
				if (row + this.getLength() == 10) {
					rowEnd = this.getLength() - 1;}
				else { 
					rowEnd = this.getLength();}
			}
		}
		for (int i = row - rowBeginning; i <= row + rowEnd; i++) {//loop through the row
			for (int j = column - colBeginning; j <= column + colEnd; j++) {//loop through the column

				if(ocean.getShipArray()[i][j].getShipType().equals("empty") == false) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 'puts' the ship in the ocean
	 * @param row: bowRow values
	 * @param column: bowColumn, values
	 * @param horizontal: horizontal instance the ship
	 * @param ocean: putting reference to the ship in each of 1 or more locations (up to 4) in the ships array
	 */

	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {

		this.setBowColumn(column);
		this.setBowRow(row);
		this.setHorizontal(horizontal);
		int shipLength = this.getLength();


		//horizontal ship
		if(horizontal == true) {
			for(int i= column ;i < column + shipLength ;i++){
				ocean.getShipArray()[row][i]=this;
			}	
		}
		//vertical ship
		else{
			for(int j= row; j < row + shipLength; j++){
				ocean.getShipArray()[j][column]=this;
			}


		}

	}


	/**
	 * if a part of the ship occupies given row and column, and the ship  hasn't been sunk, mark that
	 * part of the ship as 'hit' 
	 * @param row
	 * @param column
	 * @return
	 */
	boolean shootAt( int row, int column) {
		if (!this.isSunk()) {
			//horizontal ship
			if (this.horizontal) {
				// identify the index of the hit value 
				hit[column - this.bowColumn] = true;
				return true;

				//vertical ship
			} else {
				hit[row - this.bowRow] = true;
				return true;
			}
		} else {
			return false;
		}
	}


	//check if a given location has been shot - check the hit array
	boolean getHitLocation(int row, int column) {
		int distanceShot = 0;
		if (this.isHorizontal() == true) {
			distanceShot = column - this.getBowColumn();
			return this.getHit()[distanceShot];
		} else if (this.isHorizontal() == false){
			distanceShot = row - this.bowRow;
			return this.getHit()[distanceShot];
		}
		return false;
	}

	/**
	 * returns true if every part of the ship has been shot, otherwise it is false
	 * @return
	 */	

	boolean isSunk() {
		int count = 0;
		for (int i = 0; i < this.getLength(); i++) {
			if (this.hit[i] == true) {
				count++;
			}
		}
		if (count == this.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns a single character String to use in the Ocean's print method
	 * returns 'x' if the ship has been sunk
	 * returns 'S' if the ship is partially sunk
	 */
	public String toString() {
		if (this.isSunk() == true) {
			return "x";
		} 
		else {
			return "S";
		}
	}

	/**
	 * when user shoots at empty sea
	 * @param row
	 * @param column
	 */
	void shootAtEmptySea(int row, int column) {
		this.hit[0] = true;
	}


}

