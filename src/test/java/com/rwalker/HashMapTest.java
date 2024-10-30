package com.rwalker;

import static org.junit.Assert.assertEquals;
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

}
