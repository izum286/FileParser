package com.izum286;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        Checker checker = new Checker();
        RandomAccessFile aFile = new RandomAccessFile("file patch", "r");
        FileChannel fileChannel = aFile.getChannel();
        int chunkSize = 2048; //chunk size in bytes
        long position = 0;
        boolean isPassed = true;

        while (position >= 0 || isPassed){ //change condition while end of the file
            ////порезать файл на куски и скормить чекеру
            ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
            fileChannel.read(buffer, position);
            byte[] rawData = buffer.array();
            isPassed = checker.check(rawData);
            position = fileChannel.position() + chunkSize;
        }

        if (!isPassed){
            System.out.println("File not passed");
        }else {
            System.out.println("Passed");
        }
    }
}
