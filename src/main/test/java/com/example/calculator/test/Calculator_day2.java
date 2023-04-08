package com.example.calculator.test;

import com.example.calculator.Calculator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/4/1
 */
public class Calculator_day2 {
    static final Logger logger = getLogger(lookup().lookupClass());
    static Calculator calculator;
    int result;
    private  static String PATH="src\\main\\test\\java\\data\\jiafa.yaml";
    //private  static String PATH="src\\main\\test\\java\\data\\chufa.yaml";
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
    //基于CsvSource方式注入参数
    @ParameterizedTest
    @CsvSource({"1","2","3"})
    @DisplayName("计算加法方式_1")
    void  sum_1(int a,int b,int c){
        result=calculator.sum(a,b);
        logger.info("计算结果 {}",result);
        assertEquals(c,result,"断言正确");
  }

    //基于CsvFileSource方式注入参数，numLinesToSkip去除第一行，从第二行开始
    @ParameterizedTest
    @CsvFileSource(resources = "/data/data_1.csv",numLinesToSkip = 1)
    @DisplayName("计算加法方式_2")
    void  sum_2(int a,int b,int c){
        result=calculator.sum(a,b);
        logger.info("计算结果 {}",result);
        assertEquals(c,result,"断言正确");
    }

    //基于MethodSource方式注入参数
    @ParameterizedTest
    @MethodSource("stream")
    @DisplayName("计算加法方式_3")
    void  sum_3(List<Integer> list,int c){
        result=calculator.sum(list.stream().mapToInt(Integer::intValue).toArray());
        logger.info("计算结果 {}",result);
        assertEquals(c,result,"断言正确");
    }
    static Stream<Arguments> stream(){
        return Stream.of(
                Arguments.of(Arrays.asList(1,1),2),
                Arguments.of(Arrays.asList(-1,-98),-99)
        );
    }
    //基于YAML文件参数化
    @ParameterizedTest
    @MethodSource("ymlStream")
    @DisplayName("计算加法方式_yaml")
    void  yamlSum(DataYml dataYml){
        result =calculator.sum(dataYml.getAs(),dataYml.getBs());
        logger.info("计算结果 {}",result);
        assertEquals(dataYml.getResult(),result,"断言正确");
    }

    @ParameterizedTest
    @MethodSource("ymlStream")
    @DisplayName("除法计算方式_yaml")
    void  yamlchufa(DataYml dataYml){
        result =calculator.division(dataYml.getAs(),dataYml.getBs());
        logger.info("计算结果 {}",result);
        assertEquals(dataYml.getResult(),result,"断言正确");
    }

static Stream<DataYml> ymlStream() throws IOException {
        ObjectMapper mapper=new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String,Object>>> reference=new TypeReference<List<HashMap<String,Object>>>(){};
        List<HashMap<String,Object>> hashMap=mapper.readValue(new File(PATH),reference);
        List<DataYml> dataYmls=new ArrayList<>();
        for (HashMap<String,Object> map:hashMap){
            DataYml dataYml=new DataYml();
            dataYml.setAs((Integer)map.get("a"));
            dataYml.setBs((Integer)map.get("b"));
            dataYml.setResult((Integer)map.get("result"));
            dataYmls.add(dataYml);
        }

        return dataYmls.stream();
    }



}
