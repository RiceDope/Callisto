# Modern Collections Library
This is a Collections library that is being developed as a part of my dissertation at the University of Kent.

## Be aware

### EnforceSort
Please be aware that when enforcing sort how you sort the array and whether you give a comparator. Explanation below
```
        // Instantiate with a comparator sorting ascending
        Sequence<Student> test = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());

        // Add some Students
        test.append(new Student(20, "a"));
        test.append(new Student(15, "b"));
        test.append(new Student(30, "c"));

        // Enforce sort on the array
        test.setEnforceSort(true);

        // Now sort in decending order
        test.sort((a, b) -> b.getAge() - a.getAge());

        // Now do what was needed with descending order

        // These will insert into the wrong position
        test.append(new Student(13, "d"));
        test.append(new Student(35, "e"));

        // OUTPUT = "[Age 13 Name d , Age 30 Name c , Age 20 Name a , Age 15 Name b , Age 35 Name e ]"
        System.out.println(test.toString()); // Not correctly sorted

        // Call now with default comparator that was set at instantiation
        test.sort();

        // OUTPUT = "[Age 13 Name d , Age 15 Name b , Age 20 Name a , Age 30 Name c , Age 35 Name e ]"
        System.out.println(test.toString()); // Now correctly sorted
```
