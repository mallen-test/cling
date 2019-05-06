package org.mallen.test.cling.dms;

import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.Item;

public class ContentNode {
    private Container container;
    private Item item;
    private String id;
    private String fullPath;
    private boolean isItem;


    public ContentNode(String id, Container container) {
        this.container = container;
        this.id = id;
        this.fullPath = null;
        this.isItem = false;
    }

    public ContentNode(String id, Item item, String fullPath) {
        this.item = item;
        this.id = id;
        this.fullPath = fullPath;
        this.isItem = true;
    }

    public Container getContainer() {
        return container;
    }

    public Item getItem() {
        return item;
    }

    public String getId() {
        return id;
    }

    public String getFullPath() {
        return fullPath;
    }

    public boolean isItem() {
        return isItem;
    }
}
