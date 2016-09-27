package com.doctor.commons;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月27日<br/>
 */
public interface StringConstants {

    String   Empty              = "";

    String[] Empty_Array        = new String[0];
    String   Digits             = "0123456789";
    String   Lower_Case_Letters = "abcdefghijklmnopqrstuvwxyz";

    String   Upper_Case_Letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String   Alphabets          = Lower_Case_Letters + Upper_Case_Letters;
    String   Alpha_Numeric      = Alphabets + Digits;

}
