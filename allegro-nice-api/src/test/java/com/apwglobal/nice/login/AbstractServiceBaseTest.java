package com.apwglobal.nice.login;

import com.apwglobal.nice.service.Configuration;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractServiceBaseTest {

    protected static Credentials cred;
    protected static Configuration conf;
    protected static boolean test;

    @BeforeClass
    public static void abstractServiceSetup() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = AbstractServiceBaseTest.class.getResourceAsStream("/test-credentions.properties");
        properties.load(resourceAsStream);

        cred = new Credentials(
                Long.parseLong(properties.getProperty("allegro.clientId")),
                properties.getProperty("allegro.username"),
                properties.getProperty("allegro.password"),
                properties.getProperty("allegro.key")
        );

        int countryId = Integer.valueOf(properties.getProperty("allegro.country"));
        conf = new Configuration(countryId);

        test = Boolean.valueOf(properties.getProperty("allegro.sandbox"));
    }

}
