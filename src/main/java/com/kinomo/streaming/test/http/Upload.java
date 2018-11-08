package com.kinomo.streaming.test.http;


import com.kinomo.streaming.test.utils.Utils;
import com.kinomo.streaming.test.metrics.Metrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

import static com.kinomo.streaming.test.config.Settings.KM_AUTH;
import static com.kinomo.streaming.test.config.Settings.SESSION_TOKEN;
import static com.kinomo.streaming.test.config.Settings.UPLOAD_URL;

public class Upload {

    private static final Logger logger = LoggerFactory.getLogger(Upload.class);

    private final HttpClient client;

    public Upload(HttpClient client) {
        this.client = client;
    }

    public void upload(String filePath) throws IOException, InterruptedException {

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("upload_file", Paths.get(filePath).toFile(), ContentType.create("application/octet-stream"), "filename.mp4")
                .build();

        HttpPost httpPost = new HttpPost(UPLOAD_URL);
        httpPost.setHeader(KM_AUTH, SESSION_TOKEN);
        httpPost.setEntity(entity);

        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 200) {

            String responseBody = Utils.inputStreamToString(response.getEntity().getContent());
            JSONObject jsonObject = new JSONObject(responseBody);
            String uploadedFileId = jsonObject.getString("id");
            logger.debug("Uploaded file id: " + uploadedFileId);

            Metadata metadata = new Metadata(uploadedFileId, client);
            metadata.isUploadedTrue();

        } else {
            logger.debug(response.toString());
        }
    }

    public String upload(byte[] fileContent) throws IOException, InterruptedException {

        long startTime = System.currentTimeMillis();

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("upload_file", fileContent, ContentType.create("application/octet-stream"), "filename.mp4")
                .build();

        HttpPost httpPost = new HttpPost(UPLOAD_URL);
        httpPost.setHeader(KM_AUTH, SESSION_TOKEN);
        httpPost.setEntity(entity);

        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 200) {

            String responseBody = Utils.inputStreamToString(response.getEntity().getContent());
            JSONObject jsonObject = new JSONObject(responseBody);
            String uploadedFileId = jsonObject.getString("id");
            logger.debug("Uploaded file id: " + uploadedFileId);

            Metadata metadata = new Metadata(uploadedFileId, client);
            if (metadata.isUploadedTrue()){
                long stopTime = System.currentTimeMillis();
                Metrics.put("upload", stopTime - startTime);
                return uploadedFileId;
            }

        } else {
            logger.debug(response.toString());
        }

        return null;
    }

}
