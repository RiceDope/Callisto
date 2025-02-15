package com.rwalker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class HashMapTest {
    
    /**
     * Test the creation of one element
     */
    @Test
    public void testHashOneItem() {
        Map<String, Integer> test = new Map<>();
        test.put("Hello", 100);
        
        assertEquals("[{ Hello : 100 }]", test.toString());
    }

    /**
     * Test the creation of ten items
     */
    @Test
    public void testHashTenItems() {
        Map<String, Integer> test = new Map<>();
        test.put("Hello", 100);
        test.put("Hello1", 101);
        test.put("Hello2", 102);
        test.put("Hello3", 103);
        test.put("Hello4", 104);
        test.put("Hello5", 105);
        test.put("Hello6", 106);
        test.put("Hello7", 107);
        test.put("Hello8", 108);
        test.put("Hello9", 109);

        String testString = "[{ Hello : 100 }, { Hello1 : 101 }, { Hello2 : 102 }, { Hello3 : 103 }, { Hello4 : 104 }, { Hello5 : 105 }, { Hello6 : 106 }, { Hello7 : 107 }, { Hello8 : 108 }, { Hello9 : 109 }]";
        assertEquals(testString, test.toString());
    }

    /**
     * Test replace methods for Map
     */
    @Test
    public void testReplaceFunctions() {
        Map<String, Integer> test = new Map<>();

        // Add some entries
        test.put("Hello", 100);
        test.put("Hello1", 101);
        test.put("Hello2", 102);
        test.put("Hello3", 103);
        test.put("Hello4", 104);
        test.put("Hello5", 105);
        test.put("Hello6", 106);
        test.put("Hello7", 107);
        test.put("Hello8", 108);
        test.put("Hello9", 109);

        test.replace("Hello10", 50); // Should not insert
        assertFalse(test.keyExists("Hello10"));
        test.replace("Hello", 50); // Should change the value
        assertEquals(50, (int) test.get("Hello"));

        test.replace("Hello1", 50, 101); // Should change the value
        assertEquals(50, (int) test.get("Hello1"));
        test.replace("Hello2", 50, 101); // Shouldn't change the value
        assertNotEquals(50, (int) test.get("Hello2"));
    }

    /**
     * Test the putAll method
     */
    @Test
    public void testPutAllMethod() {
        Map<String, Integer> test = new Map<>();
        Map<String, Integer> test1 = new Map<>();

        test.put("list1a", 100);
        test.put("list1b", 150);
        test1.put("list2a", 100);
        test1.put("list2b", 50);

        test.putAll(test1);

        assertEquals("[{ list1a : 100 }, { list1b : 150 }, { list2a : 100 }, { list2b : 50 }]", test.toString());
    }

}
