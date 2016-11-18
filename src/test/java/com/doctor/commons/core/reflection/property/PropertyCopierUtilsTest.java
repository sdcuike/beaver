package com.doctor.commons.core.reflection.property;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class PropertyCopierUtilsTest {

    @Test
    public void testCopyBeanProperty() {
        Person person = new Person();
        person.setAge(11);
        person.setName("sdcui");

        Person person3 = new Person();
        person3.setName("who");
        person.setCh(person3);

        Person person2 = new Person();
        PropertyCopierUtils.copyBeanProperty(Person.class, person, person2);

        Assert.assertThat(person2.toString(), IsEqual.equalTo(person.toString()));

    }

    static class Person {
        private int    age;
        private String name;
        private Double sm;

        private Person ch;

        public Person getCh() {
            return ch;
        }

        public void setCh(Person ch) {
            this.ch = ch;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getSm() {
            return sm;
        }

        public void setSm(Double sm) {
            this.sm = sm;
        }

        @Override
        public String toString() {
            return "Person [age=" + age + ", name=" + name + ", sm=" + sm + ", ch=" + ch + "]";
        }

    }

}
