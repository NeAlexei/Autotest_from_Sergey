package com.kinomo.streaming.test.config;

public class Settings {

    //public static final String FILE_PATH = "D:\\_code\\videotest\\jellyfish-25-mbps-hd-hevc.mp4";
    public static final String FILE_PATH = "D:\\Java\\streaming-test\\src\\main\\resources\\vaselin.mp4";

   private static final String BASE_URL = "https://test02-platform.kino-mo.com";
    //private static final String BASE_URL = "http://test01-http-platform.kino-mo.com/api/v1";
   // private static final String BASE_URL = "https://dev-platform.kino-mo.com/api/v1";

    //public static final String DOWNLOAD_DATA_URL = BASE_URL + "/cct-content/images/:remoteFileId/data";
    public static final String DOWNLOAD_DATA_URL = BASE_URL + "/api/v1/cct-content/videos/:remoteFileId/data";
    //public static final String DOWNLOAD_DATA_URL = BASE_URL + "/cct-content/staticimage";
    public static final String UPLOAD_URL =  BASE_URL + "/api/v1/cct-content/videos";
    public static final String METADATA_URL = BASE_URL + "/api/v1/cct-content/videos/:remoteFileId";

    public static final String KM_AUTH = "km-auth";
    public static final String SESSION_TOKEN = "1f91af6826f434cc1f178b272662d656c47698d566c7b29150fcb9bb01db7bd05beed78ef2331e1503716bd3";
//    public static final String SESSION_TOKEN = "123";

    public static final String DOWNLOADED_FILE_EXTENSION = ".mp4";
}
