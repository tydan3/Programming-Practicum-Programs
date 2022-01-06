/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/

package model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * This program represents an item.
 * 
 * @author Daniel Ty
 * @version Oct 9 2019
 */
public final class Item {
    
    /**
     * This is the name of the item.
     */
    private final String myName;
    
    /**
     * This is the price of the item.
     */
    private final BigDecimal myPrice;
    
    /**
     * This is the bulk quantity of the item.
     */
    private final int myBulkQuantity;
    
    /**
     * This is the bulk price of the item.
     */
    private final BigDecimal myBulkPrice;
    
    /**
     * This is if the item is a bulk item.
     */
    private final boolean myIsBulk;

    /**
     * This constructs an item.
     * 
     * @param theName name of item
     * @param thePrice price of item
     * @throws NullPointerException if theName or thePrice is null
     * @throws IllegalArgumentException if theName is an emptyString or if
     *         thePrice < 0.
     */
    public Item(final String theName, final BigDecimal thePrice) {
        if (Objects.requireNonNull(theName).length() == 0
                        || Objects.requireNonNull(thePrice).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        myName = theName;
        myPrice = thePrice;
        myBulkQuantity = 0;
        myBulkPrice = BigDecimal.ZERO;
        myIsBulk = false;
    }

    /**
     * This constructs an item that can be bought in bulk.
     * 
     * @param theName name of item
     * @param thePrice price of item
     * @param theBulkQuantity bulk quantity of item
     * @param theBulkPrice bulk price of item
     * @throws NullPointerException if theName or thePrice or theBulkPrice is null
     * @throws IllegalArgumentException if theName is an emptyString or if
     *         thePrice < 0 or BulkQuantity < 0 or BulkPrice < 0.
     */
    public Item(final String theName, final BigDecimal thePrice, final int theBulkQuantity,
                final BigDecimal theBulkPrice) {
        if (Objects.requireNonNull(theName).length() == 0
                        || Objects.requireNonNull(thePrice).compareTo(BigDecimal.ZERO) < 0
                        || theBulkQuantity < 0
                        || Objects.requireNonNull(theBulkPrice).compareTo(BigDecimal.ZERO) 
                        < 0) {
            throw new IllegalArgumentException();
        }
        myName = theName;
        myPrice = thePrice;
        myBulkQuantity = theBulkQuantity;
        myBulkPrice = theBulkPrice;
        myIsBulk = true;
    }

    /**
     * This returns the price of the item.
     * 
     * @return price of item
     */
    public BigDecimal getPrice() {
        return myPrice;
    }

    /**
     * This returns the bulk quantity of the item.
     * 
     * @return bulk quantity of item
     */
    public int getBulkQuantity() {
        return myBulkQuantity;
    }

    /**
     * This returns the bulk price of the item.
     * 
     * @return bulk price of item
     */
    public BigDecimal getBulkPrice() {
        return myBulkPrice;
    }

    /**
     * This indicates whether the item is a bulk item or not.
     * 
     * @return true if item is a bulk item; false otherwise
     */
    public boolean isBulk() {
        return myIsBulk;
    }

    /**
     * This returns a string representation of this item. The representation consists of the 
     * item's name followed by a ", " and the item's price. If the item is a bulk item, the
     * string representation is also followed by bulk quantity " for " bulk price enclosed
     * in "()".
     * 
     * @return string representation of this item
     */
    @Override
    public String toString() {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US); 
        final String nameAndPrice = myName + ", " + nf.format(myPrice);
        final StringBuffer representItem = new StringBuffer(nameAndPrice);
        if (myIsBulk) {
            representItem.append(" (" + myBulkQuantity + " for "
                            + nf.format(myBulkPrice) + ")"); 
        }
        return new String(representItem);
    }

    @Override
    public boolean equals(final Object theOther) {
        boolean returnMe = false;
        if (theOther != null && getClass().equals(theOther.getClass())) {
            final Item otherItem = (Item) theOther;
            if (Objects.equals(myName, otherItem.myName) 
                && Objects.equals(myPrice, otherItem.myPrice) 
                && Objects.equals(myBulkQuantity, otherItem.myBulkQuantity) 
                && Objects.equals(myBulkPrice, otherItem.myBulkPrice)) {
                returnMe = true;
            }
        }
        return returnMe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myPrice, myBulkQuantity, myBulkPrice);
    }

}
