package com.doctor.commons.lang.tuple;

import com.doctor.beaver.annotation.NotThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月12日
 *         <p>
 */
@NotThreadSafe
public final class Pair<L, R> {

    private L left;
    private R right;

    /**
     * 增加工厂函数
     * 
     * @param left
     * @param right
     * @return {@code Pair<L, R>}
     */
    public static <L, R> Pair<L, R> newPair(L left, R right) {
        return new Pair<L, R>(left, right);
    }

    public Pair() {
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pair [left=" + left + ", right=" + right + "]";
    }
}
