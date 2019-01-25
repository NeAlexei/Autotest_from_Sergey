package com.kinomo.streaming.test.task;

import com.kinomo.streaming.test.http.DownloadAsWhole;
import com.kinomo.streaming.test.http.DownloadByRanges;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.UUID;

public class DownloadTask implements Runnable {

    private final HttpClient httpClient;

    public DownloadTask() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public void run() {

        DownloadByRanges download = new DownloadByRanges(httpClient);
       // download.download("5c1cd3b298004d0001b4a080", Thread.currentThread().getName()+UUID.randomUUID().toString() + ".png"); //dev auto
      download.download("5c344fb7fe5183000b2b2414", Thread.currentThread().getName()+UUID.randomUUID().toString() + ".sketchfabmodel"); // local auto

    }
}
