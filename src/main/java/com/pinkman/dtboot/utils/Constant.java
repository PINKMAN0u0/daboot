package com.pinkman.dtboot.utils;

/**
 * Created by helen on 2018/3/3
 */
public class Constant {

    /** 超级管理员ID */
    public static final int SUPER_ADMIN = 1;

    /**
     * 定时任务状态
     *
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL((byte)0),
        /**
         * 暂停
         */
        PAUSE((byte)1);

        private byte value;

        private ScheduleStatus(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
}
