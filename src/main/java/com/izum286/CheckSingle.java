package com.izum286;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class CheckSingle implements Callable<Boolean> {
    byte[] pattern = new byte[]{2,13,44,55,7,12,5};
    byte[] single;

    public CheckSingle(byte[] toCheck) {
        this.single = toCheck;
    }

    @Override
    public Boolean call() throws Exception {
        long start = System.nanoTime();
        Map<Byte, Integer> map = new HashMap<>();
        ArrayList<Byte> res = new ArrayList<>();


        for (int i=0;i<single.length;i++){
            map.put(single[i], map.getOrDefault(single[i], 0)+1);
        }
        for (int i=0;i<pattern.length;i++){
            if (map.containsKey(pattern[i])&&map.get(pattern[i])>0){
                map.put(pattern[i],map.get(pattern[i])-1);
                res.add(pattern[i]);
            }
        }
//        System.out.println(System.nanoTime()-start  +" "+Thread.currentThread().getId());
        return res.size()<=(pattern.length/2)+1;
    }

}
