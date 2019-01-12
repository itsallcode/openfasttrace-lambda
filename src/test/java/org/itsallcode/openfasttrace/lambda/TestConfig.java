package org.itsallcode.openfasttrace.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TestConfig
{
    private final Properties properties;

    private TestConfig(Properties properties)
    {
        this.properties = properties;
    }

    public static TestConfig load()
    {
        return new TestConfig(loadConfig(Paths.get("test.properties")));
    }

    private static Properties loadConfig(Path path)
    {
        final Properties properties = new Properties();
        try (InputStream stream = Files.newInputStream(path))
        {
            properties.load(stream);
        }
        catch (final IOException e)
        {
            throw new UncheckedIOException("Error loading config from " + path, e);
        }
        return properties;
    }

    public String getBaseUrl()
    {
        return properties.getProperty("ApiUrl");
    }
}
