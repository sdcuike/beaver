package com.doctor.commons.core.reflection.property;

import java.util.Iterator;

import org.junit.Test;

public class PropertyTokenizerTest {

    @Test
    public void testPropertyTokenizer() {
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer("person[0].birthday.date");
        for (PropertyTokenizer propertyTokenizer2 : propertyTokenizer) {
            System.out.println(propertyTokenizer2);
        }

        Iterator<PropertyTokenizer> iterator = propertyTokenizer.iterator();
        while (iterator.hasNext()) {
            PropertyTokenizer propertyTokenizer2 = (PropertyTokenizer) iterator.next();
            System.out.println(propertyTokenizer2);

        }
    }

}
