/**
 * Script that implements the basic functionality needed for Sequence
 * 
 * @Author Rhys Walker
 * @Since 30/09/2024
 * @Version 0.1
 */

package ModernCollectionsLibrary.Src;

import java.lang.reflect.Array;

public class Sequence<E> {

    private E[] defaultList;
    private int index = 0;

    public Sequence(Class<E> clazz){
        
        defaultList = (E[]) Array.newInstance(clazz, 100);

    }

    public void append(E item){
        
        defaultList[index] = item;
        index++;
        System.out.println(defaultList);

    }
} 