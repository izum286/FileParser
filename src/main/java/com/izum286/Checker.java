package com.izum286;

import java.util.*;
import java.util.concurrent.*;

public class Checker  {
    int numThreads = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);




    boolean check(byte[] buffer) throws InterruptedException, ExecutionException, TimeoutException {
        List<Future<Boolean>> res = executorService.invokeAll(divideBuffer(buffer));
        for (Future<Boolean> future: res){
            if (!future.get().booleanValue()){
                return false;
            }
        }return true;
    }

    private List<Callable<Boolean>> divideBuffer(byte[] buffer) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < buffer.length; i++) {
            byte[] singleChunk = new byte[buffer.length / numThreads];
            for (int j = 0; j < singleChunk.length; j++) {
                singleChunk[j] = buffer[i];
            }
            list.add(singleChunk);
        }
        List<Callable<Boolean>> tasks = new ArrayList<>();
        for(Object check: list){
            tasks.add(new CheckSingle((byte[]) check));
        }
        return tasks;
    }

}
