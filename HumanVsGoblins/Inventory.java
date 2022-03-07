package genspark.projects.HumanVsGoblins;

import java.util.ArrayList;
// TODO: currently no items to hold; goto HoldableItems
public class Inventory {
    private ArrayList<HoldableItem> items;
    private Integer maxSize;                    // null max size means infinite sized inventory

    public Inventory(Integer maxSize) {
        items = new ArrayList<>();
        if (maxSize == null || maxSize < 0) {
            this.maxSize = null;
        } else {
            this.maxSize = maxSize;
        }
    }

    public boolean addItem(HoldableItem item) {
        if(maxSize == null || items.size() < maxSize) {
            items.add(item);
            return true;
        } else {
            return false;
        }
    }
}
