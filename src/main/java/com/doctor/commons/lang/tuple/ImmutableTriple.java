package com.doctor.commons.lang.tuple;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月12日
 *         <p>
 */
public final class ImmutableTriple<L, M, R> {
    private final L left;
    private final M middle;
    private final R right;

    public static <L, M, R> ImmutableTriple<L, M, R> newInstance(L left, M middle, R right) {
        return new ImmutableTriple<>(left, middle, right);
    }

    public ImmutableTriple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((middle == null) ? 0 : middle.hashCode());
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
        ImmutableTriple other = (ImmutableTriple) obj;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (middle == null) {
            if (other.middle != null)
                return false;
        } else if (!middle.equals(other.middle))
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
        return "ImmutableTriple [left=" + left + ", middle=" + middle + ", right=" + right + "]";
    }
}
