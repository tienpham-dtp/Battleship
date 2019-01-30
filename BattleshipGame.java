package battleship;

import java.util.Scanner;

public class BattleshipGame {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			Ocean ocean = new Ocean();

			//setting up the ships randomly
			ocean.placeAllShipsRandomly();

			//Place the empty board for display
			ocean.emptyBoard();

			int row;
			int column;
			final int rowLowerLimit = 0;
			final int rowUpperLimit = 9;
			final int columnLowerLimit = 0;
			final int columnUpperLimit = 9;

			String userInput;
			String rowString;
			String columnString;

			while(ocean.isGameOver() == false) {
				System.out.println("Enter the row and column with comma between the two values (eg. 1,2): ");
				userInput = sc.nextLine();
				userInput = userInput.replaceAll("\\s+","");
				//strip off white spaces surrounding the input + split the input 

				rowString = userInput.split(",")[0];
				columnString = userInput.split(",")[1];


				try {
					row = Integer.parseInt(rowString);
					column = Integer.parseInt(columnString);
				} catch (final Exception e) {
					System.out.println("Invalid input. Input must be integers");
					continue;
				}


				while ((row < rowLowerLimit) || (row > rowUpperLimit) || (column < columnLowerLimit) || (column > columnUpperLimit)) {
					System.out.println("Invalid input - out of limit. Please re-enter: ");
					userInput = sc.nextLine();
					userInput = userInput.replaceAll("\\s+","");
					//strip off white spaces surrounding the input + split the input 

					rowString = userInput.split(",")[0];
					columnString = userInput.split(",")[1];

					//throw error if the input is not integers
					try {
						row = Integer.parseInt(rowString);
						column = Integer.parseInt(columnString);
					} catch (final Exception e) {
						System.out.println("Invalid input. Input must be integers");
						continue;
					}
				}
				//if the location is of empty sea
				if (ocean.getShipArray()[row][column].getShipType().equals("empty")) {
					ocean.getShipArray()[row][column].shootAtEmptySea(row, column);
					ocean.shootAt(row, column);
				} else {
					ocean.shootAt(row, column);
				}
				ocean.print();
				System.out.println("Number of shots fired: " + ocean.getShotsFired());
				System.out.println("Number of hits: " + ocean.getHitCount());
				System.out.println("Number of ships sunk: " + ocean.getShipsSunk());
			}
			System.out.println("Game over. You won!");
			System.out.println("Play again? (Enter Y for yes, N for no)");
			String answer = sc.next();
			if (answer.equals("Y") || answer.equals("y")){
				continue;
			} else {
				break;
			}
		}
		sc.close();
	}
}