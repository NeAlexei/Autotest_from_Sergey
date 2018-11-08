package com.kinomo.streaming.test.http;

import com.kinomo.streaming.test.metrics.Metrics;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.kinomo.streaming.test.config.Settings.KM_AUTH;
import static com.kinomo.streaming.test.config.Settings.METADATA_URL;
import static com.kinomo.streaming.test.config.Settings.SESSION_TOKEN;
import static com.kinomo.streaming.test.utils.Utils.fmtLog;
import static com.kinomo.streaming.test.utils.Utils.inputStreamToString;


public class Metadata {

    private static final Logger logger = LoggerFactory.getLogger(Upload.class);

    private HttpClient httpClient;
    private final String remoteFileId;

    public Metadata(String remoteFileId, HttpClient httpClient) {
        this.remoteFileId = remoteFileId;
        this.httpClient = httpClient;
    }

    public boolean isUploadedTrue() throws IOException, InterruptedException {

        long startTime = System.currentTimeMillis();

        boolean uploaded = true;
        int attemptCount = 0;

        do {
            attemptCount++;

            HttpGet httpGet = new HttpGet(METADATA_URL.replace(":remoteFileId", remoteFileId));
            httpGet.setHeader(KM_AUTH, SESSION_TOKEN);
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200){

                String responseBody = inputStreamToString(response.getEntity().getContent());
                JSONObject jsonObject = new JSONObject(responseBody);
                uploaded = jsonObject.getBoolean("uploaded");
                logger.debug(fmtLog("Uploaded: " + uploaded));

            } else {
                logger.debug(fmtLog(String.valueOf(response.getStatusLine().getStatusCode())));
                logger.debug(fmtLog(inputStreamToString(response.getEntity().getContent())));
            }

            Thread.sleep(3000); //3 sec


        } while (!uploaded && attemptCount < 30);

        long stopTime = System.currentTimeMillis();
        Metrics.put("metadata-total", stopTime - startTime);

        return uploaded;

    }
}
