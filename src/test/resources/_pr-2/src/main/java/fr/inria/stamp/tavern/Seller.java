package fr.inria.stamp.tavern;

import java.util.List;
import java.util.Optional;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 05/09/17
 */
public class Seller {

	private int gold;

	private List<Item> items;

	public Seller(int gold, List<Item> itemsToSell) {
		this.gold = gold;
		this.items = itemsToSell;
	}

	public Item sellItem(String s, Player p) {
        final Optional<Item> first = this.items.stream()
                .filter(item -> item.getName().equals(s))
                .findFirst();
        if (!first.isPresent()) {
            return null;
        } else {
            Item i = first.get();
            if (i != null) {
                final Integer g_p = p.getGold();
                final int value = g_p.compareTo(i.price);
                if (value >= 0) {
                    this.gold = this.gold + i.price;
                    p.giveGold(i.price);
                    return i;
                }
            }
        }
		return null;
	}

	public int getGold() {
		return gold;
	}

	public List<Item> getItems() {
		return items;
	}
}
