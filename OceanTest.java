package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {
	Ocean ocean;
	Ship battleship;
	Ship cruiser;
	Ship cruiser1;
	Ship cruiser2;
	Ship destroyer;
	Ship destroyer1;
	Ship destroyer2;
	Ship destroyer3;
	Ship submarine;
	Ship submarine1;
	Ship submarine2;
	Ship submarine3;
	Ship submarine4;


	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
		battleship = new Battleship();
		cruiser = new Cruiser();
		cruiser1 = new Cruiser();
		cruiser2 = new Cruiser();
		destroyer = new Destroyer();
		destroyer1 = new Destroyer();
		destroyer2 = new Destroyer();
		destroyer3 = new Destroyer();
		submarine = new Submarine();
		submarine1 = new Submarine();
		submarine2 = new Submarine();
		submarine3 = new Submarine();
		submarine4 = new Submarine();

	}

	@Test
	void testOcean() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				assertTrue(this.ocean.getShipArray()[i][j].getShipType() == "empty");
			}
		}
		assertTrue(this.ocean.getHitCount() == 0);
		assertTrue(this.ocean.getShipsSunk() == 0);
		assertTrue(this.ocean.getShotsFired() == 0);
	}


	@Test
	void testIsOccupied() {
		this.cruiser.placeShipAt(0, 0, true, ocean);
		assertTrue(this.ocean.isOccupied(0, 0) == true);
		assertTrue(this.ocean.isOccupied(0, 1) == true);
		assertTrue(this.ocean.isOccupied(0, 2) == true);

		this.destroyer.placeShipAt(2, 2, false, ocean);
		assertTrue(this.ocean.isOccupied(2, 2) == true);
		assertTrue(this.ocean.isOccupied(3, 2) == true);
	}

	@Test
	void testShootAt() {
		//shoot at empty sea
		this.ocean.shootAt(0, 0);
		assertEquals(this.ocean.getShotsFired(), 1);
		assertTrue(this.ocean.getHitCount() == 0);
		assertTrue(this.ocean.getShipsSunk() == 0);

		//placing a ship and check
		this.cruiser.placeShipAt(0, 6, true, ocean);
		this.ocean.shootAt(0, 6);
		assertEquals(this.ocean.getShotsFired(), 2);
		assertTrue(this.ocean.getHitCount() == 1);
		assertTrue(this.ocean.getShipsSunk() == 0);

		//shoot the rest and check ship sunk
		this.ocean.shootAt(0, 7);
		this.ocean.shootAt(0, 8);
		assertEquals(this.ocean.getShotsFired(), 4);
		assertTrue(this.ocean.getHitCount() == 3);
		assertTrue(this.ocean.getShipsSunk() == 1);
	}

	@Test
	void testGetShotsFired() {
		//shoot at empty sea
		this.ocean.shootAt(0, 0);
		assertEquals(this.ocean.getShotsFired(), 1);

		this.ocean.shootAt(0, 9);
		assertEquals(this.ocean.getShotsFired(), 2);
		this.ocean.shootAt(0, 1);
		//repeat and count
		this.ocean.shootAt(0, 2);
		this.ocean.shootAt(0, 2);
		assertEquals(this.ocean.getShotsFired(), 5);

	}

	@Test
	void testGetHitCount() {
		//initially not ship were shot
		assertTrue(this.ocean.getHitCount() == 0);

		//when shoot at a ship
		this.cruiser.placeShipAt(0, 0, true, ocean);
		this.cruiser.shootAt(0, 0);
		System.out.println(this.ocean.getHitCount());
	}

	@Test
	void testGetShipsSunk() {
		//initially no ships were sunk
		assertTrue(this.ocean.getShipsSunk() == 0);

		//placing ship and sink it
		this.cruiser.placeShipAt(0, 1, true, ocean);
		this.ocean.shootAt(0, 1);
		this.ocean.shootAt(0, 2);
		this.ocean.shootAt(0, 3);
		assertTrue(this.ocean.getShipsSunk() == 1);

	}


	@Test
	void testIsGameOver() {
		//shoot at all places of the ocean
		this.battleship.placeShipAt(0, 0, true, ocean);
		this.cruiser1.placeShipAt(2, 2, false, ocean);
		this.cruiser2.placeShipAt(0, 9, false, ocean);
		this.destroyer1.placeShipAt(4, 6, true, ocean);
		this.destroyer2.placeShipAt(8, 7, true, ocean);
		this.destroyer3.placeShipAt(1, 7, false, ocean);
		this.submarine1.placeShipAt(9, 9, true, ocean);
		this.submarine2.placeShipAt(8, 5, true, ocean);
		this.submarine3.placeShipAt(1, 5, true, ocean);
		this.submarine4.placeShipAt(9,0 , true, ocean);

		for( int i = 0; i < 10 ; i++){
			for(int j =0; j < 10; j++) {
				ocean.shootAt(i, j);
			}
		}
		//all ships are sunk
		assertTrue(ocean.isGameOver() == true);;
	}

	@Test
	void getShipArray() {
		this.cruiser.placeShipAt(0, 0, true, ocean);
		assertEquals("cruiser", this.ocean.getShipArray()[0][0].getShipType());
		this.destroyer2.placeShipAt(8, 7, true, ocean);
		assertEquals("destroyer", this.ocean.getShipArray()[8][7].getShipType());
		this.submarine3.placeShipAt(1, 5, true, ocean);
		assertEquals("submarine", this.ocean.getShipArray()[1][5].getShipType());
		this.battleship.placeShipAt(6, 3, true, ocean);
		assertEquals("battleship", this.ocean.getShipArray()[6][3].getShipType());
	}

}
