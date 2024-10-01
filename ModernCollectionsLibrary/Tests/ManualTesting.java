package ModernCollectionsLibrary.Tests;

import ModernCollectionsLibrary.Src.Sequence;

public class ManualTesting {
    
    public static void main(String[] args) {
        
        Sequence<Integer> test = new Sequence<Integer>(10);
        
        test.append(5);
        test.append(2);
        test.append(2);
        test.append(10);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        System.out.println(test.get(3));
        test.append(2);

        System.out.println(test.get(0));
        test.replace(12, 11);
        System.out.println(test.get(11));
    }

}
