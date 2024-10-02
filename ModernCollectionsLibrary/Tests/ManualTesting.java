package ModernCollectionsLibrary.Tests;

import ModernCollectionsLibrary.Src.Sequence;

public class ManualTesting {
    
    public static void main(String[] args) {
        
        Sequence<Integer> test = new Sequence<Integer>(10);
        
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(10);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);

        // Example of issue when removing
        System.out.println(test.rawString());
        test.remove(0);
        System.out.println(test.rawString());
        test.remove(1);
        System.out.println(test.rawString());
        test.remove(2);
        System.out.println(test.rawString());
    }

}
