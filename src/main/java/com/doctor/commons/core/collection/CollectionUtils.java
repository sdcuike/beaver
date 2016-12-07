package com.doctor.commons.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.08
 *         <p>
 */
@ThreadSafe
public final class CollectionUtils {

    /**
     * 并集
     * 
     * @param coll1
     * @param coll2
     * @return
     */
    public static <E> List<E> union(final Collection<E> coll1, final Collection<E> coll2) {
        List<E> list = new ArrayList<>(coll1);
        list.addAll(coll2);
        return list;
    }

    public static <E> List<E> unionWithoutDuplication(final Collection<E> coll1, final Collection<E> coll2) {
        List<E> list1 = new ArrayList<>(coll1);
        List<E> list2 = new ArrayList<>(coll2);

        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }

    /**
     * 交集
     * 
     * @param coll1
     * @param coll2
     * @return
     */
    public static <E> List<E> intersection(final Collection<E> coll1, final Collection<E> coll2) {
        List<E> list = new ArrayList<>(coll1);
        list.retainAll(coll2);
        return list;
    }

    /**
     * 差集
     * 
     * @param coll1
     * @param coll2
     * @return
     */
    public static <E> List<E> subtract(final Collection<E> coll1, final Collection<E> coll2) {
        List<E> list = new ArrayList<>(coll1);
        list.removeAll(coll2);
        return list;
    }

    public static void main(String[] args) {
        Collection<Integer> coll2 = new ArrayList<>();
        coll2.add(1);
        coll2.add(1);
        coll2.add(2);
        Collection<Integer> coll1 = new ArrayList<>();
        coll1.add(2);
        coll1.add(2);
        System.out.println(union(coll1, coll2));
        System.out.println(unionWithoutDuplication(coll1, coll2));
    }
}
