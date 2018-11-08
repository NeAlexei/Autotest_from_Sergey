package com.kinomo.streaming.test.task;

import com.kinomo.streaming.test.http.Upload;
import com.kinomo.streaming.test.http.Download;
import com.kinomo.streaming.test.metrics.Metrics;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.UUID;

public class UploadDownloadTask implements Runnable {

    private final HttpClient httpClient;
    private final byte[] fileContent;

    public UploadDownloadTask(byte[] fileContent) {
        this.httpClient = HttpClientBuilder.create().build();;
        this.fileContent = fileContent;
    }

    @Override
    public void run() {

      /*  try {*/

            long startTime = System.currentTimeMillis();

           /* Upload upload = new Upload(httpClient);
            String uploadedFileId = upload.upload(fileContent);*/

           String uploadedFileId = "12345";
            if (uploadedFileId != null) {
                Download download = new Download(httpClient);
                download.download(uploadedFileId, Thread.currentThread().getName()+UUID.randomUUID().toString() + ".mp4");

                long stopTime = System.currentTimeMillis();
                Metrics.put("totalCycle", stopTime - startTime);
            }

       /* } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
