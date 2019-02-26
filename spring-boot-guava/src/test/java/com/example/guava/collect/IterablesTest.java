package com.example.guava.collect;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class IterablesTest {

    @Test
    public void create() {
        Iterable<String> iterable1 = Collections.emptySet();

        Iterable<Integer> iterable2 = () -> Arrays.asList(0, 1).iterator();
        Iterator<Integer> iterator = Arrays.asList(0, 1).iterator();

        Iterable<String> iterable3 = Sets.newHashSet("a", null, "b");

        Iterable<String> iterable4 = Collections.singleton("a");

        Iterable<String> iterable5 = Collections.singletonList("foo");
    }

    @Test
    public void getOnlyElement() {
        Iterable<String> iterable = Collections.emptySet();
        System.out.println(Iterables.getOnlyElement(iterable, "bar")); // bar
    }

    @Test
    public void and_all_find_tryFind() {
        List<String> list = newArrayList("cool", "pants");
        Predicate<String> predicate = Predicates.equalTo("pants");

        System.out.println(Iterables.any(list, predicate));     // true
        System.out.println(Iterables.all(list, predicate));     // false

        System.out.println(Iterables.find(list, predicate));    // pants
        System.out.println(Iterables.find(list, Predicates.equalTo("aaa"), "bbb"));  // bbb
        System.out.println(Iterables.find(list, Predicates.alwaysFalse(), "bbb"));         // bbb
        System.out.println(Iterables.find(list, Predicates.alwaysTrue(), "bbb"));          // cool

        System.out.println(Iterables.tryFind(list, predicate));                         // Optional.of(pants)
        System.out.println(Iterables.tryFind(list, Predicates.equalTo("aaa")));  // Optional.absent()
        System.out.println(Iterables.tryFind(list, Predicates.alwaysFalse()));          // Optional.absent()
        System.out.println(Iterables.tryFind(list, Predicates.alwaysTrue()));           // Optional.of(cool)
    }

    @Test
    public void filter() {
        List<Object> list = newArrayList("A", "B", "C", "A", null, 1, null, 2);
        Iterable<Object> iterable = Iterables.filter(list, Predicates.equalTo("A"));
        System.out.println(iterable);   // [A, A]

        Iterable<Integer> filter = Iterables.filter(list, Integer.class);
        System.out.println(filter);     // [1, 2]

        System.out.println(Iterables.filter(list, Predicates.notNull()));  // [A, B, C, A, 1, 2]
    }

    @Test
    public void toArray() {
        Iterable<String> iterable = Collections.singletonList("a");
        String[] array = Iterables.toArray(iterable, String.class);
        System.out.println(Arrays.toString(array));         // [a]
    }


    @Test
    public void transform() {
        List<String> input = asList("1", "2", "3");
        Iterable<Integer> result = Iterables.transform(input, from -> Integer.valueOf(from));

        List<Integer> actual = newArrayList(result);
        System.out.println(actual);     // [1, 2, 3]

        List<String> input2 = asList("a", "b", "c");
        Iterable<String> result2 = Iterables.transform(input2, from -> from.toUpperCase());
        System.out.println(result2);    // [A, B, C]
    }

    @Test
    public void cycle() {
        // 循环迭代
        Iterable<String> cycle = Iterables.cycle("a", "b", "c");
        System.out.println(cycle);  // [a, b, c] (cycled)

        int i = 0;
        for (String str : cycle) {
            i++;
            if (i > 20) {
                break;
            }
            System.out.println(str);
        }
    }

    @Test
    public void concat() {
        List<Integer> list1 = newArrayList(1);
        List<Integer> list2 = newArrayList(4);

        List<List<Integer>> input = newArrayList(list1, list2);
        Iterable<Integer> result = Iterables.concat(input);
        System.out.println(result);     // [1, 4]

        List<Integer> list3 = newArrayList(7, 8);
        List<Integer> list4 = newArrayList(9);
        List<Integer> list5 = newArrayList(10);
        result = Iterables.concat(list1, list2, list3, list4, list5);
        System.out.println(result);     // [1, 4, 7, 8, 9, 10]
    }

    @Test
    public void concat2() {
        Iterable<Integer> iterable = asList(1, 2, 3);
        int n = 4;
        Iterable<Integer> repeated = Iterables.concat(Collections.nCopies(n, iterable));
        System.out.println(repeated);   // [1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3]
    }

    @Test
    public void partition() {
        Iterable<Integer> source = Collections.emptySet();
        Iterable<List<Integer>> partitions = Iterables.partition(source, 1);
        System.out.println(partitions);                         // []
        System.out.println(Iterables.isEmpty(partitions));      // true

        List<Integer> list = asList(1, 2, 3, 4, 5);
        partitions = Iterables.partition(list, 2);
        System.out.println(partitions);                         // [[1, 2], [3, 4], [5]]
        System.out.println(Iterables.size(partitions));         // 3
    }

    @Test
    public void paddedPartition() {
        List<Integer> list = asList(1, 2, 3, 4, 5);
        Iterable<List<Integer>> partitions = Iterables.paddedPartition(list, 2);
        System.out.println(partitions);                         // [[1, 2], [3, 4], [5, null]]
        System.out.println(Iterables.getLast(partitions));      // [5, null]
        System.out.println(Iterables.size(partitions));         // 3
    }

    @Test
    public void addAll() {
        List<String> alreadyThere = newArrayList("already", "there");
        List<String> freshlyAdded = newArrayList("freshly", "added");

        boolean changed = Iterables.addAll(alreadyThere, freshlyAdded);
        System.out.println(changed);        // true
        System.out.println(alreadyThere);   // [already, there, freshly, added]
        System.out.println(freshlyAdded);   // [freshly, added]
    }

    @Test
    public void elementsEqual(){
        Iterable<?> a;
        Iterable<?> b;

        // A few elements.
        a = asList(4, 8, 15, 16, 23, 42);
        b = asList(4, 8, 15, 16, 23, 42);
        System.out.println(Iterables.elementsEqual(a, b));  // false

        // An element differs.
        a = asList(4, 8, 15, 12, 23, 42);
        b = asList(4, 8, 15, 16, 23, 42);
        System.out.println(Iterables.elementsEqual(a, b));  // false

        // null versus non-null.
        a = asList(4, 8, 15, null, 23, 42);
        b = asList(4, 8, 15, 16, 23, 42);
        System.out.println(Iterables.elementsEqual(a, b));  // false
        System.out.println(Iterables.elementsEqual(b, a));  // false

        // Different lengths.
        a = asList(4, 8, 15, 16, 23);
        b = asList(4, 8, 15, 16, 23, 42);
        System.out.println(Iterables.elementsEqual(a, b));  // false
        System.out.println(Iterables.elementsEqual(a, b));  // false
    }

    @Test
    public void limit() {
        Iterable<String> iterable = newArrayList("foo", "bar", "baz");
        Iterable<String> limited = Iterables.limit(iterable, 2);
        System.out.println(limited);        // [foo, bar]
    }

    @Test
    public void skip() {
        Collection<String> set = ImmutableSet.of("a", "b", "c", "d", "e");
        System.out.println(Iterables.skip(set, 2));     // [c, d, e]
        System.out.println(Iterables.skip(set, 20));
    }

    @Test
    public void get() {
        ArrayList<String> list = newArrayList("a", "b", "c");
        System.out.println(Iterables.get(list, 1, "d"));    // b
        System.out.println(Iterables.get(list, 5, "d"));    // d
    }

    @Test
    public void getFirst_getLast() {
        Iterable<String> iterable = Collections.emptyList();
        System.out.println(Iterables.getFirst(iterable,null));      // null
        System.out.println(Iterables.getFirst(iterable, "bar"));    // bar

        iterable = asList("foo", "bar");
        System.out.println(Iterables.getFirst(iterable,null));      // foo
        System.out.println(Iterables.getLast(iterable));                        // bar
        System.out.println(Iterables.getLast(iterable,"xxx"));      // bar
    }

    @Test
    public void unmodifiableIterable() {
        List<String> list = newArrayList("a", "b", "c");
        Iterable<String> iterable = Iterables.unmodifiableIterable(list);
        Iterable<String> iterable2 = Iterables.unmodifiableIterable(iterable);

        System.out.println(iterable);       // [a, b, c]
        System.out.println(iterable2);      // [a, b, c]
    }

    @Test
    public void frequency() {
        Multiset<String> multiset = ImmutableMultiset.of("a", "b", "a", "c", "b", "a");
        Set<String> set = Sets.newHashSet("a", "b", "c");
        List<String> list = newArrayList("a", "b", "a", "c", "b", "a");

        System.out.println(Iterables.frequency(multiset, "a"));     // 3
        System.out.println(Iterables.frequency(multiset, "b"));     // 2
        System.out.println(Iterables.frequency(multiset, "d"));     // 0
        System.out.println(Iterables.frequency(multiset, 1.2));     // 0
        System.out.println(Iterables.frequency(multiset, null));    // 0
    }

    @Test
    public void removeAll() {
        List<String> list = newArrayList("a", "b", "c", "d", "e");

        System.out.println(Iterables.removeAll(list, newArrayList("b", "d", "f")));     // true
        System.out.println(list);       // [a, c, e]
        System.out.println(Iterables.removeAll(list, newArrayList("x", "y", "z")));     // false
        System.out.println(list);       // [a, c, e]
    }

    @Test
    public void retainAll() {
        List<String> list = newArrayList("a", "b", "c", "d", "e");
        System.out.println(Iterables.retainAll(list, newArrayList("b", "d", "f")));     // true
        System.out.println(list);       // [b, d]
        System.out.println(Iterables.retainAll(list, newArrayList("b", "e", "d")));     // false
        System.out.println(list);       // [b, d]
    }

    @Test
    public void removeIf() {
        List<String> list = newArrayList("a", "b", "c", "d", "e");

        boolean removeIf = Iterables.removeIf(list, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.equals("b") || input.equals("s");
            }
        });

        System.out.println(removeIf);   // true
    }

}
