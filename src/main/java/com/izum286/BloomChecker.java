package com.izum286;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * bloomfilter implementation
 * for more fast perfomance
 */
public class BloomChecker {

    BloomFilter<byte[]> filter = BloomFilter.create(Funnels.byteArrayFunnel(), 3_000_000, 0.0001);

    void makeFilter(String filePatch) throws IOException {
        long start = System.nanoTime();
        RandomAccessFile aFile = new RandomAccessFile(filePatch, "r");
        FileChannel fileChannel = aFile.getChannel();
        int chunkSize = 1_000_000; //in bytes
        int readBytes = 0;
        long pointer = 0;
        while (readBytes>=0){
            ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
            readBytes = fileChannel.read(buffer, pointer);
            byte[] rawData = buffer.array();
            filter.put(rawData);
            pointer = pointer + chunkSize;
        }
        long spent = System.nanoTime()- start;
        System.out.println("filter formed in " + spent/1000000 + " ms");
    }

    boolean check(byte[] buffer){
        long start = System.nanoTime();
        boolean isPassed = filter.mightContain(buffer);
        long spent = System.nanoTime()- start;
        System.out.println("filter passed in " + spent/1000000 + " ms");

        return isPassed;
    }

    public static void main(String[] args) throws IOException {
        BloomChecker checker = new BloomChecker();
//        String filePatch = "/test/1234.txt";
        String filePatch = "/фильмы/kmm.mkv";
        byte[] pattern = new byte[]{2,13,44,55,7,12,5};
        checker.makeFilter(filePatch);
        boolean isPassed = checker.check(pattern);
        System.out.println("is passed "+isPassed);
    }
}
