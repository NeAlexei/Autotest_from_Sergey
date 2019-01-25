package com.kinomo.streaming.test.config;

public class Settings {

    //public static final String FILE_PATH = "D:\\_code\\videotest\\jellyfish-25-mbps-hd-hevc.mp4";
    public static final String FILE_PATH = "D:\\_code\\streaming-test\\burger_880x880.mp4";

   private static final String BASE_URL = "http://localhost:8089";
    //private static final String BASE_URL = "http://test01-http-platform.kino-mo.com/api/v1";
   // private static final String BASE_URL = "https://dev-platform.kino-mo.com/api/v1";

    //public static final String DOWNLOAD_DATA_URL = BASE_URL + "/cct-content/images/:remoteFileId/data";
    public static final String DOWNLOAD_DATA_URL = BASE_URL + "/asset-library/:remoteFileId/asset.sketchfabmodel";
    //public static final String DOWNLOAD_DATA_URL = BASE_URL + "/cct-content/staticimage";
    public static final String UPLOAD_URL =  BASE_URL + "/cct-content/videos";
    public static final String METADATA_URL = BASE_URL + "/cct-content/videos/:remoteFileId";

    public static final String KM_AUTH = "km-auth";
  //  public static final String SESSION_TOKEN = "45f4d842b8b9d7f1dad1167ba29ee6d730c5722024e04a620a98cde3adf4e4295bc9ef9cea878b378c7031a5";
    public static final String SESSION_TOKEN = "123";

    public static final String DOWNLOADED_FILE_EXTENSION = "mp4";
}
