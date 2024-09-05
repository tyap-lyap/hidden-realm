package ru.pinkgoosik.hiddenrealm.data;

import net.minecraft.item.Item;

public class Trade {

	public Item item;
	public int count;
	public int price;
	public boolean renewable;

	public Trade(Item item, int count, int price, boolean renewable) {
		this.item = item;
		this.count = count;
		this.price = price;
		this.renewable = renewable;
	}

}
