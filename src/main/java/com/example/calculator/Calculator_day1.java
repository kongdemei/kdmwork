package com.example.calculator;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/4/1
 */
public class Calculator_day1 {
    static final Logger logger = getLogger(lookup().lookupClass());
    static Calculator calculator;
    int result;

    @BeforeAll
    static void dkCalculator() {
        calculator = new Calculator("创建计算器");
    }

    @BeforeEach
    void setId() {
        calculator.initId();
    }

    @AfterEach
    void delId() {
        calculator.destroyId();
    }

    @AfterAll
    static void gbCalculator() {
        calculator.close();
    }


    @Test
    @DisplayName("加法计算:1+1")
    void sumTest_1() {
        logger.info("开始进行计算");
        result = calculator.sum(1, 1);
        logger.info("计算结果: {}", result);
        assertEquals(2,result);
    }

    @Test
    @DisplayName("加法计算:1+1+9")
    void sumTest_2() {
        logger.info("开始进行计算");
        result = calculator.sum(1,1,9);
        logger.info("计算结果: {}", result);
        assertEquals(11,result);
    }
    @Test
    @DisplayName("加法计算:-99+99")
    void sumTest_3() {
        logger.info("开始进行计算");
        result = calculator.sum(-99,99);
        logger.info("计算结果: {}", result);
        assertEquals(0,result);
    }

    @Test
    @DisplayName("加法计算:99+(-99)")
    void sumTest_4() {
        logger.info("开始进行计算");
        result = calculator.sum(99,-99);
        logger.info("计算结果: {}", result);
        assertEquals(0,result);
    }

    @Test
    @DisplayName("加法计算:(-99)+(-99)")
    void sumTest_5() {
        logger.info("开始进行计算");
        result = calculator.sum(-99,-99);
        logger.info("计算结果: {}", result);
        assertEquals(-198,result);
    }

    @Test
    @DisplayName("减法计算:2-1")
    void subtractTest_1() {
        logger.info("开始进行计算");
        result = calculator.subtract(2, 1);
        logger.info("计算结果: {}", result);
        assertEquals(1,result);
    }
    @Test
    @DisplayName("减法计算:-3-2")
    void subtractTest_2() {
        logger.info("开始进行计算");
        result = calculator.subtract(-3, 2);
        logger.info("计算结果: {}", result);
        assertEquals(-5,result);
    }
}
