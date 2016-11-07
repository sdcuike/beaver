package com.doctor.commons.mail;

/**
 * 邮件优先级<br>
 * 参考：
 * <a href="https://people.dsv.su.se/~jpalme/ietf/ietf-mail-attributes.html">
 * https://people.dsv.su.se/~jpalme/ietf/ietf-mail-attributes.html</a><br>
 * 3.9 Quality information
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.07
 *         <p>
 */
public enum EmailPriority {
    Highest(1, "Highest"),
    High(2, "High"),
    Normal(3, "Normal"),
    Low(4, "Low"),
    Lowest(5, "Lowest");

    private int    priority;
    private String description;

    private EmailPriority(int priority, String description) {
        this.priority = priority;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
