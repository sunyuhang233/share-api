package top.hang.share.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author : Ahang
 * @program : share-api
 * @description : 雪花算法 封装 hutool 雪花算法
 * @create : 2023-10-07 13:13
 **/

public class SnowUtil {
    /**
     * 数据中心
     */
    private static final long DATA_CENTER_ID = 1;

    /**
     * 机器标识
     */
    private static final long WORKER_ID = 1;

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextIdStr();
    }
}
