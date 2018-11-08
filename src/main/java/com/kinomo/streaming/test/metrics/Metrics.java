package com.kinomo.streaming.test.metrics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Metrics {

    private static final Logger logger = LoggerFactory.getLogger(Metrics.class);

    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> map = new ConcurrentHashMap();

    public static void put(String metricName, long durationMillis){
        ConcurrentHashMap<String, Object> threadMap = map.getOrDefault(Thread.currentThread().getName(), new ConcurrentHashMap<>());
        if (!"download-range".equals(metricName)){
            threadMap.put(metricName, durationMillis);
        } else {
            List<Long> downloadRangeMetrics = (List<Long>) threadMap.getOrDefault("download-range", new ArrayList<>());
            downloadRangeMetrics.add(durationMillis);
            threadMap.put(metricName, downloadRangeMetrics);

        }
        map.put(Thread.currentThread().getName(), threadMap);
    }

    public static void print(){
        map.forEachKey(100, key -> {
            logger.info(key + " : " + Arrays.toString(map.get(key).entrySet().toArray()));
        });
    }

    public static void reset(){
        map.clear();
    }
}
