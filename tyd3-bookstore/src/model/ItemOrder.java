/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/


package model;

import java.util.Objects;

/**
 * This program represents an purchase order for an item.
 * 
 * @author Daniel Ty
 * @version Oct 9 2019
 */
public final class ItemOrder {
    
    /**
     * This the item in this order.
     */
    private final Item myItem;
    
    /**
     * This is the quantity of the item in this order.
     */
    private final int myQuantity;

    /**
     * This constructs an item order.
     * 
     * @param theItem item in order
     * @param theQuantity quantity of item in order
     * @throws IllegalArgumentException if theQuantity is < 0.
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        if (theQuantity < 1) {
            throw new IllegalArgumentException();
        }
        myItem = Objects.requireNonNull(theItem);
        myQuantity = theQuantity;
    }

    /**
     * This returns the item in this item order.
     * 
     * @return reference to item
     */
    public Item getItem() {
        return myItem;
    }
    
    /**
     * This returns the quantity of an item in this item order.
     * 
     * @return quantity of item
     */
    public int getQuantity() {
        return myQuantity;
    }

    /**
     * This returns a string representation of this item order. The representation consists
     * of the toString of the Item within this order followed by ", ordered quantity: " and
     * the ordered quantity number.
     * 
     * @return string representation of this item order
     */
    @Override
    public String toString() {
        return myItem + ", ordered quantity: " + myQuantity;
    }

}
