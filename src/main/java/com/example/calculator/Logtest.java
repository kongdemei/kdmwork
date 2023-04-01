package com.example.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/4/1
 */
public class Logtest {


    private final static Logger logger = LoggerFactory.getLogger(Logtest.class);

    public static void main(String[] args) {
        logger.info("logback 成功了");
        logger.error("logback 成功了");
        logger.debug("logback 成功了");
    }

}
