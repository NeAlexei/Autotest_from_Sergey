package com.kinomo.streaming.test.task;

import com.kinomo.streaming.test.http.DownloadByRanges;
import com.kinomo.streaming.test.http.Upload;
import com.kinomo.streaming.test.metrics.Metrics;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.UUID;

import static com.kinomo.streaming.test.config.Settings.DOWNLOADED_FILE_EXTENSION;

public class UploadDownloadTask implements Runnable {

    private final HttpClient httpClient;
    private final byte[] fileContent;

    public UploadDownloadTask(byte[] fileContent) {
        this.httpClient = HttpClientBuilder.create().build();;
        this.fileContent = fileContent;
    }

    @Override
    public void run() {

        try {

            long startTime = System.currentTimeMillis();

            Upload upload = new Upload(httpClient);
            String uploadedFileId = upload.upload(fileContent);

            if (uploadedFileId != null) {
                DownloadByRanges download = new DownloadByRanges(httpClient);
                download.download(uploadedFileId, Thread.currentThread().getName()+UUID.randomUUID().toString() + DOWNLOADED_FILE_EXTENSION);

                long stopTime = System.currentTimeMillis();
                Metrics.put("totalCycle", stopTime - startTime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
