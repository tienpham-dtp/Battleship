package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
	Ocean ocean;
	Ship emptysea;
	Ship ship;
	Ship battleship;
	Ship cruiser;
	Ship destroyer;
	Ship submarine;
	boolean hitArray[];


	@BeforeEach
	void setUp() throws Exception {
		ship = new Ship();
		ocean = new Ocean();
		battleship = new Battleship();
		submarine = new Submarine();
		cruiser = new Cruiser();
		destroyer = new Destroyer();
		emptysea = new EmptySea();
	}

	/**
	 * testing the constructor
	 */
	@Test
	void testShip() {
		//all ships are initialized as not hit yet
		hitArray = new boolean[] {false, false,false, false};
		assertTrue(Arrays.equals(this.battleship.getHit(),hitArray));
		assertTrue(Arrays.equals(this.cruiser.getHit(),hitArray));
		assertTrue(Arrays.equals(this.destroyer.getHit(),hitArray));
		assertTrue(Arrays.equals(this.submarine.getHit(),hitArray));
		assertTrue(Arrays.equals(this.ship.getHit(),hitArray));

	}

	@Test
	void testGetLength() {
		//emptysea's length is 1 (in emptySea class)
		assertTrue(this.emptysea.getLength() == 1);
		assertTrue(this.battleship.getLength() == 4);
		assertTrue(this.cruiser.getLength() == 3);
		assertTrue(this.destroyer.getLength() == 2);
		assertTrue(this.submarine.getLength() == 1);
	}


	@Test
	void testGetBowRow() {
		battleship.placeShipAt(1,1, true, ocean);
		submarine.placeShipAt(5,7, false, ocean);
		assertTrue(this.battleship.getBowRow() == 1);
		assertTrue(this.submarine.getBowRow() == 5);
	}

	@Test
	void testGetBowColumn() {
		battleship.placeShipAt(1,1, true, ocean);
		submarine.placeShipAt(5,7, false, ocean);
		assertTrue(this.battleship.getBowColumn() == 1);
		assertTrue(this.submarine.getBowColumn() == 7);
	}

	@Test
	void testGetHit() {
		hitArray = new boolean[] {false, false, false, false};
		assertTrue(Arrays.equals(this.ship.getHit(), hitArray));
		assertTrue(Arrays.equals(this.battleship.getHit(), hitArray));
		assertTrue(Arrays.equals(this.cruiser.getHit(), hitArray));
		assertTrue(Arrays.equals(this.destroyer.getHit(), hitArray));
		assertTrue(Arrays.equals(this.submarine.getHit(), hitArray));
	}

	@Test
	void testIsHorizontal() {
		this.ship.setHorizontal(true);
		assertTrue(this.ship.isHorizontal() == true);
		this.ship.setHorizontal(false);
		assertTrue(this.ship.isHorizontal() == false);
	}

	@Test
	void testGetShipType() {
		assertEquals("battleship", this.battleship.getShipType());
		assertEquals("cruiser", this.cruiser.getShipType());
		assertEquals("destroyer", this.destroyer.getShipType());
		assertEquals("submarine", this.submarine.getShipType());
	}


	@Test
	void testOkToPlaceShipAt() {
		//checking possible position on the edge using battleship
		assertTrue(this.battleship.okToPlaceShipAt(0, 0, true, ocean) == true);
		assertTrue(this.battleship.okToPlaceShipAt(0, 0, false, ocean) == true);
		assertTrue(this.battleship.okToPlaceShipAt(0, 9, true, ocean) == false);
		assertTrue(this.battleship.okToPlaceShipAt(0, 9, false, ocean) == true);
		assertTrue(this.battleship.okToPlaceShipAt(9, 0, true, ocean) == true);
		assertTrue(this.battleship.okToPlaceShipAt(9, 0, false, ocean) == false);
		assertTrue(this.battleship.okToPlaceShipAt(9, 9, true, ocean) == false);
		assertTrue(this.battleship.okToPlaceShipAt(9, 9, false, ocean) == false);

		//		//check if a position has been occupied, another ship should not be placed there
		this.battleship.placeShipAt(0, 0, true, ocean);
		assertTrue(this.submarine.okToPlaceShipAt(0, 0, true, ocean) == false);
		assertTrue(this.destroyer.okToPlaceShipAt(0, 1, false, ocean) == false);
		assertTrue(this.cruiser.okToPlaceShipAt(0, 2, true, ocean) == false);
	}


	@Test
	void testShootAt() {
		//place cruiser vertically and shoot
		this.cruiser.placeShipAt(0, 0, false, ocean);
		assertEquals(true, this.cruiser.shootAt(0, 0));
		assertEquals(true, this.cruiser.shootAt(1, 0));
		assertEquals(true, this.cruiser.shootAt(2, 0));
		assertEquals(false, this.cruiser.shootAt(3, 0));
	}

	@Test	
	void testGetHitLocation() {
		this.destroyer.placeShipAt(0, 0, false, ocean);
		this.destroyer.shootAt(0, 0);
		assertTrue(this.destroyer.getHitLocation(0, 0) == true);
	}

	@Test
	void testIsSunk() {
		this.battleship.placeShipAt(0, 0, true, ocean);
		assertTrue(this.battleship.isSunk() == false);
		this.battleship.shootAt(0, 0);
		assertTrue(this.battleship.isSunk() == false);
		this.battleship.shootAt(0, 1);
		this.battleship.shootAt(0, 2);
		this.battleship.shootAt(0, 3);
		assertTrue(this.battleship.isSunk() == true);

		this.submarine.placeShipAt(9, 0, true, ocean);
		assertTrue(this.submarine.isSunk() == false);
		this.battleship.shootAt(9, 0);
		assertTrue(this.battleship.isSunk() == true);

		emptysea = this.ocean.getShipArray()[2][3];
		this.emptysea.shootAt(2,3);
		assertTrue(this.emptysea.isSunk() == false);

	}

	@Test
	void testToString() {
		this.cruiser.placeShipAt(0, 0, true, ocean);
		this.cruiser.shootAt(0,0);
		assertTrue(this.cruiser.toString() == "S");
		this.cruiser.shootAt(0, 1);
		this.cruiser.shootAt(0, 2);
		assertTrue(this.cruiser.toString() == "x");

		this.emptysea.placeShipAt(0, 0, false, ocean);
		this.emptysea.shootAt(0, 0);
		assertTrue(this.emptysea.toString() == "-");
	}

}
