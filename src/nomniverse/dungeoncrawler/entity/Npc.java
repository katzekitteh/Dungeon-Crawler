package nomniverse.dungeoncrawler.entity;

import nomniverse.dungeoncrawler.Game;
import nomniverse.dungeoncrawler.item.Inventory;
import nomniverse.dungeoncrawler.item.Item;
import nomniverse.dungeoncrawler.list.ItemList;

public class Npc extends Entity {
	
	private Inventory inventory = new Inventory(9);
	private Inventory equipped = new Inventory(2);
	
	private boolean aggressive;

	public Npc(String name, int health, int defense, int attack, boolean aggressive) {
    	super(name, health, defense, attack);
    	this.aggressive = aggressive;
    }
	
	public void checkIfAlive() {
		this.checkHealth();
        if (!alive) {
            Game.print(name + " has died.");
        }
    }
	
	public void checkInventory() {
    	inventory.checkInventory(true);
    }
    
    public void add(int slot, Item item) {
    	inventory.add(slot, item);
    }
    
    public void removeSlot(int slot) {
    	inventory.removeSlot(slot);
    }
    
    public void removeItem(ItemList itemList, String item) {
    	inventory.removeItem(itemList.getItem(item));
    }
    
    public void addItem(ItemList itemList, String item) {
    	inventory.addItem(itemList.getItem(item));
    }
    
    public void checkEquipped() {
    	equipped.checkInventory(true);
    }
	
	public boolean isAggressive() {
		return aggressive;
	}
	
	public void setAggressive(boolean aggressive) {
		this.aggressive = aggressive;
	}
}