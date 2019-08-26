package com.example.basic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class String {
    @Test
    public void split() {
        java.lang.String string = "004.034556";
        java.lang.String[] parts = string.split("\\.");
        assertEquals("0042", parts[0]);
//        assert !parts[0].equals("004") : "already serving";
    }
}



