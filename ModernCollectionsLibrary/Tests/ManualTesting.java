package ModernCollectionsLibrary.Tests;

import ModernCollectionsLibrary.Src.Sequence;

public class ManualTesting {
    
    public static void main(String[] args) {
        
        Sequence<Integer> test = new Sequence<Integer>(10);
        
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        test.append(2);
        System.out.println(test.toString());
        test.append(2);

        System.out.println(test.toString());
        
    }

}
