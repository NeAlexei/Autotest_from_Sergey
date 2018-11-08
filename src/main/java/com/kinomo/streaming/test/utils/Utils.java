package com.kinomo.streaming.test.utils;

import java.io.*;

public class Utils {

    public static String fmtLog(String log){
        return Thread.currentThread().getName() + " --- " + log;
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(inputStream));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    public static long inputStreamToFile(InputStream is, File outputFile) throws IOException {

        long bytesDownloaded = 0;
        try (OutputStream os = new FileOutputStream(outputFile, true)) {

            byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
                //System.out.println(String.format("Bytes downloaded: %d", bytesDownloaded));
            }
        }
        return bytesDownloaded;
    }
}
