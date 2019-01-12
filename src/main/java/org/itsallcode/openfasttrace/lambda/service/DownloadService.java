package org.itsallcode.openfasttrace.lambda.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kaklakariada.aws.lambda.exception.BadRequestException;

public class DownloadService
{
    private static final Logger LOG = LogManager.getLogger(DownloadService.class);

    public Path downloadToTempFile(String url)
    {
        final Path tempFile = createTempFile();
        download(url, tempFile);
        return tempFile;
    }

    private Path createTempFile()
    {
        try
        {
            return Files.createTempFile("download", "tmp");
        }
        catch (final IOException e)
        {
            throw new UncheckedIOException("Error creating temp file", e);
        }
    }

    void download(String urlString, Path targetFile)
    {
        final URL url = parseUrl(urlString);
        LOG.debug("Downloading {} to {}", urlString, targetFile);
        try (final InputStream inputStream = url.openStream();
                final ReadableByteChannel rbc = Channels.newChannel(inputStream);
                final FileOutputStream outputStream = new FileOutputStream(targetFile.toFile()))
        {
            final long size = outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            LOG.debug("Download finished after {} bytes", size);
        }
        catch (final IOException e)
        {
            throw new UncheckedIOException("Error downloading from " + url + " to " + targetFile,
                    e);
        }
    }

    private URL parseUrl(String url)
    {
        try
        {
            return new URL(url);
        }
        catch (final MalformedURLException e)
        {
            throw new BadRequestException("Invalid url '" + url + "'", e);
        }
    }
}
