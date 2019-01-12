package org.itsallcode.openfasttrace.lambda.service;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itsallcode.openfasttrace.lambda.TraceRequest;
import org.itsallcode.openfasttrace.lambda.TraceResponse;

import com.github.kaklakariada.aws.lambda.exception.BadRequestException;

public class OftService
{
    private static final Logger LOG = LogManager.getLogger(OftService.class);

    private final UnpackService unpackService;
    private final DownloadService downloadService;

    public OftService(UnpackService unpackService, DownloadService downloadService)
    {
        this.unpackService = unpackService;
        this.downloadService = downloadService;
    }

    public TraceResponse trace(TraceRequest request)
    {
        if (request == null)
        {
            throw new BadRequestException("Invalid request");
        }
        final TraceResponse response = new TraceResponse();
        if (request.url != null)
        {
            LOG.info("Got url '{}'", request.url);
            final Path tempFile = downloadService.downloadToTempFile(request.url);
            final Path tempFolder = unpackService.extractToTempFolder(tempFile);
            LOG.info("Extracted file to {}", tempFolder);
            response.setSuccess(true);
            return response;
        }
        LOG.info("No url given");
        return response;
    }
}
