package com.izum286;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {

        Checker checker = new Checker();
        RandomAccessFile aFile = new RandomAccessFile("file patch", "r");
        FileChannel fileChannel = aFile.getChannel();
        int chunkSize = 2048; //chunk size in bytes
        long position = 0;

        while (position >= 0){ //change condition while end of the file
            ////порезать файл на куски и скормить чекеру
            ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
            fileChannel.read(buffer, position);
            byte[] rawData = buffer.array();
            checker.check(rawData);
            position = fileChannel.position() + chunkSize;
        }
    }
}
