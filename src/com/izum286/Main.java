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
        RandomAccessFile aFile = new RandomAccessFile("/test/1234.txt", "r");

        FileChannel fileChannel = aFile.getChannel();
        int chunkSize = 10000; //chunk size in bytes
        long pointer = 0;
        boolean isPassed = true;
        int readbytes = 0;
        long cycles =0;

        while (readbytes >= 0 && isPassed){ //change condition while end of the file
            ////порезать файл на куски и скормить чекеру
            ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
            readbytes = fileChannel.read(buffer, pointer);
            byte[] rawData = buffer.array();
            isPassed = checker.check(rawData);
            pointer = pointer + chunkSize;
            cycles++;
            System.out.println(cycles);
        }

        if (!isPassed){
            System.out.println("File not passed");
        }else {
            System.out.println("Passed");
        }
    }
}
