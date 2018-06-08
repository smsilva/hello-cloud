package org.example.hellocloud.greetings.control;

import java.text.DecimalFormat;
import org.junit.Test;

public class DoubleTest {
    
    @Test
    public void testFormat() {
	String value = new DecimalFormat("#,##0.0#;(#,##0.0#)").format(Double.parseDouble("-1101.39"));
	
	System.out.println("value=[" + value + "]");
    }

}
