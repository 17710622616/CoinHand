package com.bssf.john_li.coinhand.CHUtils;

import java.util.Comparator;

/**
 * Created by John_Li on 3/10/2018.
 */

public class ValComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        long timeDiff1 = 0;
        long timeDiff2 = 0;
        try {
            timeDiff1 = CHCommonUtils.compareTimestamps(Long.parseLong(String.valueOf(o1)));
            timeDiff2 = CHCommonUtils.compareTimestamps(Long.parseLong(String.valueOf(o2)));
        } catch (Exception e) {

        }
        return (int)(timeDiff1 - timeDiff2);
    }
}
