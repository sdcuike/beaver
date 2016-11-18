package com.doctor.commons.core.reflection.property;

import java.util.Iterator;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * 字符串表达的属性工具类
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月24日
 *         <p>
 */
@ThreadSafe
public class PropertyTokenizer implements Iterable<PropertyTokenizer> {
    //person[0].birthday.date
    private String name;     //person
    private String indexName;//person[0]
    private String index;    //0
    private String children; //birthday.date

    public PropertyTokenizer(String fullName) {
        fullName = fullName.trim();
        int index = fullName.indexOf('.');
        if (index > -1) {
            name = fullName.substring(0, index);
            children = fullName.substring(index + 1);
        } else {
            name = fullName;
            children = null;
        }
        indexName = name;
        int indexOf = indexName.indexOf('[');
        if (indexOf > -1) {
            name = indexName.substring(0, indexOf);
            this.index = indexName.substring(indexOf + 1, indexName.length() - 1);
        }
    }

    @Override
    public Iterator<PropertyTokenizer> iterator() {
        return new PropertyTokenizerIterator();
    }

    @Override
    public String toString() {
        return "PropertyTokenizer [name=" + name + ", indexName=" + indexName + ", index=" + index + ", children=" + children + "]";
    }

    public String getName() {
        return name;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndex() {
        return index;
    }

    public String getChildren() {
        return children;
    }

    public class PropertyTokenizerIterator implements Iterator<PropertyTokenizer> {
        private PropertyTokenizer current;

        @Override
        public boolean hasNext() {
            if (current != null) {
                return current.children != null;
            }
            return children != null;
        }

        @Override
        public PropertyTokenizer next() {
            if (current != null) {
                current = new PropertyTokenizer(current.children);
            } else {
                current = PropertyTokenizer.this;
            }
            return current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
