/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/

package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import model.Item;
import model.ItemOrder;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the ItemOrder class.
 * @author Daniel Ty
 * @version Oct 10 2019
 */
public class ItemOrderTest {
    
    /** 
     * This is the default name.
     */
    private static final String DEFAULT_NAME = "Test Item";
    
    /**
     * This is the default price.
     */
    private static final  BigDecimal DEFAULT_PRICE = new BigDecimal("10.00");    
    
    /**
     * This is the default item.
     */
    private static final Item DEFAULT_ITEM = new Item(DEFAULT_NAME, DEFAULT_PRICE);
    
    /**
     * This is the default order quantity.
     */
    private static final int DEFAULT_QUANTITY = 5;
   
    /**
     * Test fixture.
     */
    private ItemOrder myTestOrder;
    

    /**
     * This method runs before EVERY test case. Use it to re-initialize test fixtures. 
     */
    @Before
    public void setUp() {
        myTestOrder = new ItemOrder(DEFAULT_ITEM, DEFAULT_QUANTITY);
    }

    /**
     * Test the ItemOrder constructor for a NullPointerException when sending a null object. 
     */
    @Test (expected = NullPointerException.class)
    public void testOrderNPE() {
        new ItemOrder(null, DEFAULT_QUANTITY);
    }
    
    /**
     * Test the ItemOrder constructor for a IllegalArguementException when sending a negative
     * quantity. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testOrderIAE() {
        new ItemOrder(DEFAULT_ITEM, -1);
    }

    /**
     * Test the accessor, getItem.
     */
    @Test
    public void testGetItem() {
        assertEquals(DEFAULT_ITEM, myTestOrder.getItem());
    }
    
    /**
     * Test the accessor, getQuantity.
     */
    @Test
    public void testGetQuantity() {
        assertEquals(DEFAULT_QUANTITY, myTestOrder.getQuantity());
    }

    /**
     * Test toString.
     */
    @Test
    public void testToString() {
        final String expected = DEFAULT_ITEM + ", ordered quantity: " +  DEFAULT_QUANTITY;
        assertEquals(expected, myTestOrder.toString());
    }

}
