/*
* TCSS 305 – Autumn 2019
* Assignment 1 – Bookstore
*/

package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import model.Item;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Item class.
 * @author Daniel Ty
 * @version Oct 10 2019
 */
public class ItemTest {

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
    
    /** A negative BigDecimal. */
    private static final BigDecimal NEGATIVE_BIGDECIMAL = BigDecimal.valueOf(-9.99);
    
    /**
     * A NumberFormat used in toString() to display prices.
     */
    private static final NumberFormat CURRENCY_FORMAT =
                    NumberFormat.getCurrencyInstance(Locale.US);
    
    /** 
     * Test fixture.
     */
    private Item myTestItem;
    
    /** 
     * Test fixture. 
     */
    private Item myTestBulkItem;
    

    /**
     * This method runs before EVERY test case. Use it to re-initialize test fixtures. 
     */
    @Before
    public void setUp() {
        myTestItem = new Item(DEFAULT_NAME, DEFAULT_PRICE);
        myTestBulkItem = new Item(DEFAULT_NAME, DEFAULT_PRICE, DEFAULT_BULK_QUANTITY,
                                  DEFAULT_BULK_PRICE);
    }
    
    
    /**
     * Test the Item constructor for a NullPointerException when sending a null name. 
     */
    @Test (expected = NullPointerException.class)
    public void testItemNameNPE() {
        new Item(null, DEFAULT_PRICE);
    }
    
    /**
     * Test the Item constructor for a IllegalArgumentException when sending an empty String. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testItemNameIAE() {
        new Item("", DEFAULT_PRICE);
    }    
    
    /**
     * Test the Item constructor for a NullPointerException when sending a null price. 
     */
    @Test (expected = NullPointerException.class)
    public void testItemPriceNPE() {
        new Item(DEFAULT_NAME, null);
    }
    
    /**
     * Test the Item constructor for a IllegalArgumentException when sending a negative price. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testItemPriceIAE() {
        new Item(DEFAULT_NAME, NEGATIVE_BIGDECIMAL);
    }
    
    /**
     * Test the bulk Item constructor for a NullPointerException when sending a null name. 
     */
    @Test (expected = NullPointerException.class)
    public void testBulkItemNameNPE() {
        new Item(null, DEFAULT_PRICE, DEFAULT_BULK_QUANTITY, DEFAULT_BULK_PRICE);
    }
    
    /**
     * Test the bulk Item constructor for a IllegalArgumentException when sending an empty 
     * String. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBulkItemNameIAE() {
        new Item("", DEFAULT_PRICE, DEFAULT_BULK_QUANTITY, DEFAULT_BULK_PRICE);
    }    
    
    /**
     * Test the bulk Item constructor for a NullPointerException when sending a null price. 
     */
    @Test (expected = NullPointerException.class)
    public void testBulkItemPriceNPE() {
        new Item(DEFAULT_NAME, null, DEFAULT_BULK_QUANTITY, DEFAULT_BULK_PRICE);
    }
    
    /**
     * Test the bulk Item constructor for a IllegalArgumentException when sending a negative
     * price. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBulkItemPriceIAE() {
        new Item(DEFAULT_NAME, NEGATIVE_BIGDECIMAL, DEFAULT_BULK_QUANTITY, DEFAULT_BULK_PRICE);
    }
    
    /**
     * Test the bulk Item constructor for a IllegalArgumentException when sending a negative
     * bulk quantity. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBulkItemQuantityIAE() {
        new Item(DEFAULT_NAME, DEFAULT_PRICE, -1, DEFAULT_BULK_PRICE);
    }
    
    /**
     * Test the bulk Item constructor for a NullPointerException when sending a null bulk 
     * price. 
     */
    @Test (expected = NullPointerException.class)
    public void testBulkItemBulkPriceNPE() {
        new Item(DEFAULT_NAME, DEFAULT_PRICE, DEFAULT_BULK_QUANTITY, null);
    }
    
    /**
     * Test the bulk Item constructor for a IllegalArgumentException when sending a negative
     * bulk price. 
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBulkItemBulkPriceIAE() {
        new Item(DEFAULT_NAME, DEFAULT_PRICE, DEFAULT_BULK_QUANTITY, NEGATIVE_BIGDECIMAL);
    }
    
    /**
     * Test the accessor, getPrice. 
     */
    @Test
    public void testGetPrice() {
        assertEquals("Test the price", DEFAULT_PRICE, myTestItem.getPrice());
    }
    
    /**
     * Test the accessor, getBulkQuantity. 
     */
    @Test
    public void testGetBulkQuantity() {
        assertEquals("Test the bulk quantity", DEFAULT_BULK_QUANTITY, 
                     myTestBulkItem.getBulkQuantity());
    }
    /**
     * Test the accessor, getBulkPrice.  
     */
    @Test
    public void testGetBulkPrice() {
        assertEquals("Test the bulk price", DEFAULT_BULK_PRICE, myTestBulkItem.getBulkPrice());
    }
    /**
     * Test the accessor, isBulk.
     */
    @Test
    public void testIsBulk() {
        assertEquals("Test if the item is bulk", true, myTestBulkItem.isBulk());
    }

    
    /** 
     * Test case for .equals looking only at the cases where a Non-Item object is passed. 
     */
    @Test
    public void testEqualsFalseNonItemCases() {
        final Item test = null;
        assertNotEquals(myTestItem, test);
        assertNotEquals(myTestItem, DEFAULT_NAME);
        assertNotEquals(myTestItem, DEFAULT_PRICE);
    }
    
    /**
     * Test case for .equals looking only at false cases.
     */
    @Test
    public void testEqualsFalseCases() {
        final Item differentName = new Item("Different", DEFAULT_PRICE);
        final Item differentPrice = new Item(DEFAULT_NAME, new BigDecimal("99.99"));
        assertNotEquals(myTestItem, differentName);
        assertNotEquals(myTestItem, differentPrice);
    }
    /** 
     * Test case for bulk item for .equals looking only at false cases. 
     */
    @Test
    public void testBulkEqualsFalseCases() {
        final Item differentName = new Item("Different", DEFAULT_PRICE,
                                                     DEFAULT_BULK_QUANTITY,
                                                     DEFAULT_BULK_PRICE);
        final Item differentPrice = new Item(DEFAULT_NAME, new BigDecimal("99.99"), 
                                                     DEFAULT_BULK_QUANTITY,
                                                     DEFAULT_BULK_PRICE);
        final Item differentBulkQuantity = new Item(DEFAULT_NAME, DEFAULT_PRICE, 
                                                    99, DEFAULT_BULK_PRICE);
        final Item differentBulkPrice = new Item(DEFAULT_NAME, DEFAULT_PRICE, 
                                                 DEFAULT_BULK_QUANTITY,
                                                 new BigDecimal("99.99"));
        assertNotEquals(myTestBulkItem, differentName);
        assertNotEquals(myTestBulkItem, differentPrice);
        assertNotEquals(myTestBulkItem, differentBulkQuantity);
        assertNotEquals(myTestBulkItem, differentBulkPrice);        
    }    

    /** 
     * Test case for .equals looking only at the true cases. 
     */
    @Test
    public void testEqualsTrueCases() {
        assertEquals(myTestItem, myTestItem);
        final Item differentButTheSame = new Item(DEFAULT_NAME, DEFAULT_PRICE);
        assertEquals(myTestItem, differentButTheSame);
    }
    
    /** 
     * Test case for bulk item for .equals looking only at the true cases. 
     */
    @Test
    public void testBulkEqualsTrueCases() {
        assertEquals(myTestBulkItem, myTestBulkItem);
        final Item differentBulkButTheSame = new Item(DEFAULT_NAME, DEFAULT_PRICE,
                                                  DEFAULT_BULK_QUANTITY, DEFAULT_BULK_PRICE);
        assertEquals(myTestBulkItem, differentBulkButTheSame);
    }
    
    /**
     * Test for the hash code.
     */
    @Test
    public void testHashCode() {
        assertEquals(myTestItem.hashCode(), myTestItem.hashCode());
        final Item differentButTheSame = new Item(DEFAULT_NAME, DEFAULT_PRICE);
        assertEquals(myTestItem.hashCode(), differentButTheSame.hashCode());
    }
    
    /**
     * Test toString. 
     */
    @Test
    public void testToString() {
        final String expected = DEFAULT_NAME + ", " + CURRENCY_FORMAT.format(DEFAULT_PRICE);
        assertEquals(expected, myTestItem.toString());
    }
    
    /**
     * Test bulk item toString. 
     */
    @Test
    public void testBulkToString() {
        final String expected = DEFAULT_NAME + ", " + CURRENCY_FORMAT.format(DEFAULT_PRICE)
                                + " (" + DEFAULT_BULK_QUANTITY + " for " 
                                + CURRENCY_FORMAT.format(DEFAULT_BULK_PRICE) + ")";
        assertEquals(expected, myTestBulkItem.toString());
    }

}