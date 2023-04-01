package com.example.java;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/3/31
 */
public class Test {

    @org.junit.jupiter.api.Test
    //根据学号查询
    public void read() throws IOException {
        Integer id =3;
        Yaml yaml = new Yaml();
        List<Student> list = new ArrayList<>();
        Map<Integer, Student> objectMap = yaml.load(new FileInputStream("src\\main\\java\\com\\example\\java\\test.yml"));
        if (objectMap == null) {
            return;
        }
        if (id == null) {
            Set<Integer> set = objectMap.keySet();
            Iterator<Integer> integer = set.iterator();
            while (integer.hasNext()) {
                list.add(objectMap.get(integer.next()));
            }
            System.out.println("list" + list);

        }
          list.add(objectMap.get(id));
        System.out.println("2....."+list);
    }
}

