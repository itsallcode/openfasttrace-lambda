package org.itsallcode.openfasttrace.lambda.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kaklakariada.aws.lambda.exception.BadRequestException;

public class UnpackService
{
    private static final Logger LOG = LogManager.getLogger(UnpackService.class);

    public Path extractToTempFolder(Path zipFile)
    {
        final Path tempFolder = createTempFolder();
        extractFolder(zipFile, tempFolder);
        return tempFolder;
    }

    private Path createTempFolder()
    {
        try
        {
            return Files.createTempDirectory("unpack");
        }
        catch (final IOException e)
        {
            throw new UncheckedIOException("Error creating temp folder", e);
        }
    }

    public void extractFolder(Path zipFile, Path extractFolder)
    {
        LOG.debug("Extracting {} to {}", zipFile, extractFolder);
        try (final ZipFile zip = new ZipFile(zipFile.toFile()))
        {
            Files.createDirectories(extractFolder);
            extractEntries(zip, extractFolder);
        }
        catch (final IOException e)
        {
            throw new BadRequestException("File is not a zip", e);
        }
    }

    private void extractEntries(final ZipFile zip, Path extractFolder) throws IOException
    {
        final Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
        int entryCount = 0;
        while (zipFileEntries.hasMoreElements())
        {
            entryCount++;
            final ZipEntry entry = zipFileEntries.nextElement();
            final String currentEntry = entry.getName();

            final Path destFile = extractFolder.resolve(currentEntry);

            Files.createDirectories(destFile.getParent());

            if (!entry.isDirectory())
            {
                extractEntry(zip, entry, destFile);
            }
        }
        LOG.debug("Extracted {} entries from zip", entryCount);
    }

    private void extractEntry(final ZipFile zip, final ZipEntry entry, final Path destFile)
    {
        LOG.trace("Extracting entry {} to {}", entry.getName(), destFile);
        try (final InputStream is = zip.getInputStream(entry);
                final FileOutputStream fos = new FileOutputStream(destFile.toFile());
                final ReadableByteChannel rbc = Channels.newChannel(is);)
        {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (final IOException e)
        {
            throw new UncheckedIOException("Error unzipping entry " + entry + " to " + destFile, e);
        }
    }
}
