package com.kinomo.streaming.test.http;

import com.kinomo.streaming.test.metrics.Metrics;
import com.kinomo.streaming.test.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.kinomo.streaming.test.config.Settings.DOWNLOAD_DATA_URL;
import static com.kinomo.streaming.test.config.Settings.KM_AUTH;
import static com.kinomo.streaming.test.config.Settings.SESSION_TOKEN;

public class DownloadAsWhole {

    private static final Logger logger = LoggerFactory.getLogger(DownloadAsWhole.class);

    HttpClient httpClient;

    public DownloadAsWhole(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void download(String remoteFileId, String saveAsFile) {

        try {
            File file = new File(saveAsFile);
            file.createNewFile();
            long startTime = System.currentTimeMillis();

            String url = DOWNLOAD_DATA_URL.replace(":remoteFileId", remoteFileId);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(KM_AUTH, SESSION_TOKEN);

            HttpResponse response = httpClient.execute(httpGet);

            long downloadedBytes = Utils.inputStreamToFile(response.getEntity().getContent(), file);
            logger.debug(Utils.fmtLog(String.valueOf(downloadedBytes)));
            logger.debug(Utils.fmtLog("Done"));

            long stopTime = System.currentTimeMillis();
            Metrics.put("download-whole", stopTime - startTime);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
