package com.kinomo.streaming.test.http;

import com.kinomo.streaming.test.metrics.Metrics;
import com.kinomo.streaming.test.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static com.kinomo.streaming.test.config.Settings.DOWNLOAD_DATA_URL;
import static com.kinomo.streaming.test.config.Settings.KM_AUTH;
import static com.kinomo.streaming.test.config.Settings.SESSION_TOKEN;

public class Download {

    private static final Logger logger = LoggerFactory.getLogger(Download.class);

    HttpClient httpClient;

    public Download(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private Long getContentLength(String url) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(KM_AUTH, SESSION_TOKEN);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        long stopTime = System.currentTimeMillis();
        Metrics.put("download-contentLength", stopTime - startTime);

        String contentRangeHeader = httpResponse.getFirstHeader("Content-Range").getValue();
        String upperBoundRange = contentRangeHeader.split("/")[1];

        return Long.valueOf(upperBoundRange);
    }

    private Long downloadRange(String url, Long from, Optional<Long> to, Path filePath) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(KM_AUTH, SESSION_TOKEN);

        httpGet.setHeader("Range", "bytes=" + from + "-" + (to.isPresent()? to.get(): ""));
        HttpResponse response = httpClient.execute(httpGet);

        long downloadedBytes = Utils.inputStreamToFile(response.getEntity().getContent(), filePath.toFile());

        long stopTime = System.currentTimeMillis();
        Metrics.put("download-range", stopTime - startTime);

        return downloadedBytes;
    }

    public void download(String remoteFileId, String saveAsFile){
        try {

            long startTime = System.currentTimeMillis();

            String url = DOWNLOAD_DATA_URL.replace(":remoteFileId", remoteFileId);

            File file = new File(saveAsFile);
            file.createNewFile();

            long totalContentLength = getContentLength(url);
            //download.downloadRange(0L, Optional.of(totalContentLength), file.toPath());

            long downloadedBytes = 0L;
            int downloadRequestNumber = 1;
            while (downloadedBytes < totalContentLength){
                logger.debug(Utils.fmtLog("Download request number: " + downloadRequestNumber++));
                downloadedBytes += downloadRange(url, downloadedBytes, Optional.empty(), file.toPath());
                logger.debug(Utils.fmtLog("Downloaded so far: " + downloadedBytes));
            }

            long stopTime = System.currentTimeMillis();
            Metrics.put("download-total", stopTime - startTime);

            logger.debug(Utils.fmtLog(String.valueOf(downloadedBytes)));
            logger.debug(Utils.fmtLog("Done"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
