package com.doctor.commons.core.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
     * @param set1
     * @param set2
     * @return
     */
    public static <E> Set<E> union(final Set<E> set1, final Set<E> set2) {
        Set<E> set = new HashSet<>(set1);
        set.addAll(set2);
        return Collections.unmodifiableSet(set);
    }

    /**
     * 交集
     * 
     * @param set1
     * @param set2
     * @return
     */
    public static <E> Set<E> intersection(final Set<E> set1, final Set<E> set2) {
        Set<E> set = new HashSet<>(set1);
        set.retainAll(set2);
        return Collections.unmodifiableSet(set);
    }

    /**
     * 差集
     * 
     * @param set1
     * @param set2
     * @return
     */
    public static <E> Set<E> difference(final Set<E> set1, final Set<E> set2) {
        Set<E> set = new HashSet<>(set1);
        set.removeAll(set2);
        return Collections.unmodifiableSet(set);
    }
}
