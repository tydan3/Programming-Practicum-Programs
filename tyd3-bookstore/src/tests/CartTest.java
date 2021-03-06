/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/

package tests;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Item;
import model.ItemOrder;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Item class.
 * @author Daniel Ty
 * @version Oct 10 2019
 */
public class CartTest {
    
    /**
     * This is the default price.
     */
    private static final  BigDecimal DEFAULT_PRICE = new BigDecimal("10.00");
    
    /** 
     * This is the default name.
     */
    private static final String DEFAULT_NAME = "Test Item";
    
    /**
     * This is the default bulk quantity.
     */
    private static final  int DEFAULT_BULK_QUANTITY = 5;
    
    /** 
     * This is the default bulk price.
     */
    private static final BigDecimal DEFAULT_BULK_PRICE = new BigDecimal("5.00");
    
    /**
     * This is the default item.
     */
    private static final Item DEFAULT_ITEM = new Item(DEFAULT_NAME, DEFAULT_PRICE);
    
    /**
     * This is the default bulk item.
     */
    private static final Item DEFAULT_BULK_ITEM = new Item(DEFAULT_NAME, DEFAULT_PRICE,
                                                           DEFAULT_BULK_QUANTITY,
                                                           DEFAULT_BULK_PRICE);
    
    /**
     * This is the default order quantity.
     */
    private static final int DEFAULT_QUANTITY = 15;
    
    /**
     * This is the default item order.
     */
    private static final ItemOrder DEFAULT_ORDER = new ItemOrder(DEFAULT_ITEM, 
                                                                      DEFAULT_QUANTITY);
    
    /**
     * This is the default bulk item order.
     */
    private static final ItemOrder DEFAULT_BULK_ORDER = new ItemOrder(DEFAULT_BULK_ITEM, 
                                                                      DEFAULT_QUANTITY);
    
    /**
     * Test fixture.
     */
    private Cart myTestCart;
    
    /**
     * Test fixture.
     */
    private List<ItemOrder> myTestList;
    
    /**
     * This method runs before EVERY test case. Use it to re-initialize test fixtures. 
     */
    @Before
    public void setUp() {
        myTestCart = new Cart();
        myTestList = new ArrayList<ItemOrder>();
    }
    
    /**
     * This tests the add method when adding a null order.
     */
    @Test (expected = NullPointerException.class)
    public void testAddNPE() {
        myTestCart.add(null);
    }
    
    /**
     * This tests the add method.
     */
    @Test
    public void testAdd() {
        myTestList = new ArrayList<ItemOrder>();
        myTestList.add(DEFAULT_ORDER);
        myTestList.add(DEFAULT_BULK_ORDER);
        myTestCart.add(DEFAULT_ORDER);
        myTestCart.add(DEFAULT_BULK_ORDER);
        assertEquals(myTestCart.toString(), myTestList.toString() + " membership: false");
    }
    
    /**
     * This tests the add method when adding the an order of the same item.
     */
    @Test
    public void testAddSame() {
        myTestList.add(DEFAULT_ORDER);
        myTestCart.add(new ItemOrder(DEFAULT_ITEM, 20));
        myTestCart.add(DEFAULT_ORDER);
        assertEquals(myTestCart.toString(), myTestList.toString() + " membership: false");
    }
    
    /**
     * This tests the setMembership method.
     */
    @Test
    public void testSetMembership() {
        myTestCart.setMembership(true);
        assertEquals(myTestCart.toString(), "[] membership: true");
    }
    
    /**
     * This tests the calculateTotal method for a cart that contains a regular order.
     */
    @Test
    public void testCalculateTotal() {
        myTestCart.add(DEFAULT_ORDER);
        assertEquals(myTestCart.calculateTotal(), new BigDecimal("150.00"));
    }
    
    /**
     * This tests the calculateTotal method for a cart that contains a regular order
     * with membership.
     */
    @Test
    public void testCalculateMemberRegTotal() {
        myTestCart.setMembership(true);
        myTestCart.add(DEFAULT_ORDER);
        assertEquals(myTestCart.calculateTotal(), new BigDecimal("150.00"));
    }    
    /**
     * This tests the calculateTotal method for a cart that contains a bulk order
     * with membership.
     */
    @Test
    public void testCalculateMemberBulkTotal() {
        myTestCart.setMembership(true);
        myTestCart.add(DEFAULT_BULK_ORDER);
        assertEquals(myTestCart.calculateTotal(), new BigDecimal("15.00"));
    }
    
    /**
     * This tests the calculateTotal method for a cart that contains a bulk order, with
     * membership, where the ordered quantity is less than the bulk quantity.
     */
    @Test
    public void testCalculateLessBulkTotal() {
        myTestCart.setMembership(true);
        myTestCart.add(new ItemOrder(DEFAULT_BULK_ITEM, 4));
        assertEquals(myTestCart.calculateTotal(), new BigDecimal("40.00"));
    }
    
    /**
     * This tests the clear method.
     */
    @Test
    public void testClear() {
        myTestCart.add(DEFAULT_ORDER);
        myTestCart.clear();
        assertEquals(myTestCart.toString(), myTestList.toString() + " membership: false");
    }
    
    /**
     * This tests the accessor, getCartSize.
     */
    @Test
    public void testCartSize() {
        myTestCart.add(DEFAULT_ORDER);
        assertEquals(myTestCart.getCartSize(), 1);
    }
}
