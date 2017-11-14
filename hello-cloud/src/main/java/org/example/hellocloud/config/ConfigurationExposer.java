package org.example.hellocloud.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class ConfigurationExposer {

    private final Properties properties = new Properties();

    @PostConstruct
    public void initProperties() {
	try (InputStream in = new FileInputStream("/opt/config/application.properties")) {
	    this.properties.load(in);
	} catch (IOException e) {
	    throw new IllegalStateException("Could not initialize configuration", e);
	}
    }

    @Config("")
    @Produces
    public String exposeConfig(InjectionPoint injectionPoint) {
	Config config = injectionPoint.getAnnotated().getAnnotation(Config.class);
	
	if (config != null) {
	    return this.properties.getProperty(config.value());
	}
	
	return "";
    }

}
