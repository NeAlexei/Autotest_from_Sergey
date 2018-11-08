package com.kinomo.streaming.test.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileFinder {

    public static File[] finder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.startsWith("pool") && filename.endsWith(".mp4"); }
        } );

    }

    public static void main(String[] args) {
        File[] files = finder(".");
        for (File file: files){
            file.delete();
        }
    }

    public static void deleteDownloadedFiles(){
        File[] files = finder(".");
        for (File file: files){
            file.delete();
        }
    }
}
