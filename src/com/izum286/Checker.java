package com.izum286;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Checker implements Runnable {
    int numThreads = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

    byte[] pattern = new byte[]{2,13,44,55,7,12,5};
    @Override
    public void run() {

    }

    boolean check(byte[] buffer){
        List<Object> list = divideBuffer(buffer);
    }

    private List<Object> divideBuffer(byte[] buffer) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < buffer.length; i++) {
            byte[] singleChunk = new byte[buffer.length / numThreads];
            for (int j = 0; j < singleChunk.length; j++) {
                singleChunk[j] = buffer[i];
            }
            list.add(singleChunk);
        }
        return list;
    }

}
