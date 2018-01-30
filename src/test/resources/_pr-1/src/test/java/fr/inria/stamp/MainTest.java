package fr.inria.stamp;

import fr.inria.stamp.tavern.Item;
import fr.inria.stamp.tavern.Player;
import fr.inria.stamp.tavern.Seller;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 05/09/17
 */
public class MainTest {

	@Test
	public void test() throws Exception {
		Seller seller = new Seller(100, Collections.singletonList(new Item("Potion", 5)));
		Player player = new Player("Timoleon", 1000);

		assertEquals(1000, player.getGold());
		assertEquals(0, player.getItems().size());
		assertEquals(100, seller.getGold());

		player.buyItem("Potion", seller);

		assertEquals(995, player.getGold());
		assertEquals(1, player.getItems().size());
		assertEquals(105, seller.getGold());
	}

}
