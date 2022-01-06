/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/
package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This program represents a cart of customer's overall purchase.
 * 
 * @author Daniel Ty
 * @version Oct 9 2019
 */
public class Cart {
    
    /**
     * This is a list of each ItemOrder in this cart.
     */
    private final List<ItemOrder> myShoppingCart;
    
    /**
     * This indicates whether customer has a membership.
     */
    private boolean myMembership;
    
    /**
     * This constructs an empty shopping cart.
     */
    public Cart() {
        // creates empty ArrayList representing shopping cart
        myShoppingCart = new ArrayList<ItemOrder>(0);
    }
    
    /**
     * This adds an ItemOrder into the shopping cart. If an ItemOrder of the same
     * Item is already in the shopping cart, this replaces the existing ItemOrder with the
     * passed in ItemOrder.
     * 
     * @param theOrder the ItemOrder to be added in
     */
    public void add(final ItemOrder theOrder) {
        int index = 0;
        boolean isItemSame = false;
        for (int i = 0; i < myShoppingCart.size(); i++) {
            if (myShoppingCart.get(i).getItem() == Objects.requireNonNull(theOrder).
                                                                          getItem()) {
                isItemSame = true;
                index = i;
            }
        }
        if (isItemSame) {
            myShoppingCart.set(index, Objects.requireNonNull(theOrder));
        } else {
            myShoppingCart.add(Objects.requireNonNull(theOrder));
        }
    }

    /**
     * This sets the customer's membership to the passed in truth value.
     * @param theMembership indicates whether customer has membership or not.
     */
    public void setMembership(final boolean theMembership) {
        myMembership = theMembership;
    }

    /**
     * This calculates the total price of all items in this cart.
     * 
     * @return total price of all items in this cart
     */
    public BigDecimal calculateTotal() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (final ItemOrder i : myShoppingCart) {
            final BigDecimal quantity = new BigDecimal(i.getQuantity());
            final BigDecimal bulk = new BigDecimal(i.getItem().getBulkQuantity());
            if (myMembership && i.getItem().isBulk() && quantity.compareTo(bulk) != -1) {
                final BigDecimal[] quotientAndRemain = quantity.divideAndRemainder(bulk);
                totalPrice = totalPrice.add(quotientAndRemain[0].multiply
                                                (i.getItem().getBulkPrice()));
                totalPrice = totalPrice.add(quotientAndRemain[1].multiply
                                                (i.getItem().getPrice()));
            } else {
                totalPrice = totalPrice.add(i.getItem().getPrice().multiply(quantity));
            }
        }
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
        return totalPrice;
    }
    
    /**
     * This clears the shopping cart's list of all item orders.
     */
    public void clear() {
        myShoppingCart.clear();
    }
    
    /**
     * This returns the number of orders within the shopping cart.
     * @return number of orders within the shopping cart
     */
    public int getCartSize() {
        return myShoppingCart.size();
    }

    /**
     * This returns a string representation of this cart. The string representation consists
     * of a list of elements from the List myShoppingCart enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     * 
     * @return string representation of this cart
     */
    @Override
    public String toString() {
        return myShoppingCart.toString() + " membership: " + myMembership;
    }

}
