package com.example.java;

import org.junit.platform.commons.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/3/31
 */
public class StudentDome {
    private static String PATH = "src\\main\\java\\com\\example\\java\\test.yml";


    //根据学号查询
    public List<Student> read(Integer id) throws IOException {
        Yaml yaml = new Yaml();
        List<Student> list = new ArrayList<>();
        Map<Integer, Student> objectMap = yaml.load(new FileInputStream(PATH));
        if (objectMap == null) {
            return null;
        }
        if (id == null) {
            Set<Integer> set = objectMap.keySet();
            Iterator<Integer> integer = set.iterator();
            while (integer.hasNext()) {
                list.add(objectMap.get(integer.next()));
            }
            return list;
        }
        list.add(objectMap.get(id));
        return list;
    }

    //添加学员
    public void write(Student student) throws IOException {
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("snoid", student.getSnoid());
        studentMap.put("name", student.getName());
        studentMap.put("gender", student.getGender());
        map.put(student.getSnoid(), studentMap);
        Yaml yaml = new Yaml();
        FileWriter fileWriter = new FileWriter(PATH, true);
        yaml.dump(map, fileWriter);
        fileWriter.close();
    }

    //删除学号
    public void del(Integer id) throws IOException {
        Yaml yaml = new Yaml();
        Map<Integer, Object> objectMap = yaml.load(new FileInputStream(PATH));
        if (objectMap == null) {
            return;
        }
        objectMap.remove(id);
        FileWriter fileWriter = new FileWriter(PATH);
        yaml.dump(objectMap, fileWriter);
        fileWriter.close();
    }

    //添加学员信息字符串处理
    public ResultStudent strUtils(String str) {
        ResultStudent result = new ResultStudent();
        Student std = new Student(null, null, null);
        String[] strings = str.split(",");
        if (strings.length == 3) {
            if (StringUtils.isBlank(strings[0]) || StringUtils.isBlank(strings[1]) || StringUtils.isBlank(strings[2])) {
                result.setMessge("输入的学员信息有误");
                return result;
            }
            std.setSnoid(Integer.valueOf(strings[0]));
            std.setName(strings[1]);
            std.setGender(strings[2]);
            result.setStudents(std);
            return result;
        }
        result.setMessge("输入的学员信息有误");
        return result;
    }

    public static void main(String[] args) throws IOException {
        StudentDome studentDome = new StudentDome();
        List<Student> list=null;
        Scanner sc = new Scanner(System.in);
        System.out.println("选择查询的功能：1.根据学号查询学员信息 2.添加学员 3.查看所有学员 4.删除学员");
        System.out.println();
        switch (sc.nextInt()) {
            case 1:
                //根据id查询学员信息
                Scanner snoid = new Scanner(System.in);
                System.out.println("输入学号：");
                list = studentDome.read(snoid.nextInt());
                System.out.println("根据id查询:" + list);
                break;
            case 2:
                //添加学员
                Scanner student = new Scanner(System.in);
                System.out.println("输入(学号,姓名,性别) 中间用,分割: ");
                ResultStudent strUtils = studentDome.strUtils(student.next());
                if (StringUtils.isBlank(strUtils.getMessge())) {
                    studentDome.write(strUtils.getStudents());
                    break;
                }
                System.out.println(strUtils.getMessge());
                break;
            case 3:
                //查询所有
                list = studentDome.read(null);
                System.out.println("查询所有:" + list);
                break;
            case 4:
                //删除学员
                Scanner delsnoid = new Scanner(System.in);
                System.out.println("输入删除学员的学号: ");
                studentDome.del(delsnoid.nextInt());
        }
    }
}