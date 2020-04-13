package com.izum286;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * bloomfilter implementation
 * for more fast perfomance
 */
public class BloomChecker {
    BloomFilter<byte[]> filter = BloomFilter.create(Funnels.byteArrayFunnel(), 500, 0.01);
    //filter.put - insertions from chunk
}
