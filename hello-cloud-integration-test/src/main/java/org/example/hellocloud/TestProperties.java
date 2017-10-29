package org.example.hellocloud;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {
    
    private final Properties properties = new Properties();

    public TestProperties() throws Exception {
	try (InputStream in = new FileInputStream("/opt/config/application.properties")) {
	    properties.load(in);
	}
    }

    public String getProperty(String key) {
	return properties.getProperty(key);
    }

}
