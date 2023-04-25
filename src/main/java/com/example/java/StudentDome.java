package com.example.java;

import com.alibaba.fastjson.JSON;
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
    public ResultStudent read(Integer id) throws IOException {
        ResultStudent result = new ResultStudent();
        Yaml yaml = new Yaml();
        Map<Integer, Student> objectMap = yaml.load(new FileInputStream(PATH));
        if (objectMap == null) {
            result.setException(new CustomException("没有学员信息"));
            return result;
        }
        if (id == null || id == 0) {
            result.setException(new CustomException("学号不能为空"));
            return result;
        }
        Object object = objectMap.get(id);
        Student student = JSON.parseObject(JSON.toJSONString(object), Student.class);
        result.setStudents(student);
        return result;

    }


    //查询所有
    public List<Student> readAll() throws IOException {
        Yaml yaml = new Yaml();
        List<Student> list = new ArrayList<>();
        Map<Integer, Student> objectMap = yaml.load(new FileInputStream(PATH));
        if (objectMap == null) {
            return new ArrayList<>();
        }
        Iterator<Student> iterator = objectMap.values().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }


    //添加学员
    public ResultStudent write(String str) throws IOException {
        ResultStudent resultStudent = new ResultStudent();
        Student student = strUtils(str).getStudent();
        if (null == student) {
            resultStudent.setException(strUtils(str).getException());
            return resultStudent;
        }
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
        Map<Integer, Student> objectMap = yaml.load(new FileInputStream(PATH));
        if (null != objectMap.get(student.getSnoid())) {
            resultStudent.setException(new CustomException("学员信息添加成功"));
            return resultStudent;
        }
        resultStudent.setException(new CustomException("学员信息添加失败"));
        return resultStudent;
    }


    //删除学号
    public ResultStudent del(Integer id) throws IOException {
        ResultStudent result = new ResultStudent();
        Yaml yaml = new Yaml();
        Map<Integer, Object> objectMap = yaml.load(new FileInputStream(PATH));
        if (objectMap == null || null == objectMap.get(id)) {
            result.setException(new CustomException("没有学员信息"));
            return result;
        }
        objectMap.remove(id);
        FileWriter fileWriter = new FileWriter(PATH);
        yaml.dump(objectMap, fileWriter);
        fileWriter.close();
        Map<Integer, Student> map = yaml.load(new FileInputStream(PATH));
        if (null != map.get(id)) {
            result.setException(new CustomException("删除学员信息失败"));
            return result;
        }
        result.setException(new CustomException("删除学员信息成功"));
        return result;
    }

    //添加学员信息字符串处理
    public ResultStudent strUtils(String str) {
        ResultStudent result = new ResultStudent();
        Student std = new Student(null, null, null);
        String[] strings = str.split(",");
        if (strings.length == 3) {
            //if (StringUtils.isBlank(strings[0]) || StringUtils.isBlank(strings[1]) || StringUtils.isBlank(strings[2])) {
            result.setException(new CustomException("学员信息有误"));
            //    return result;
            //  }
            std.setSnoid(Integer.valueOf(strings[0]));
            std.setName(strings[1]);
            std.setGender(strings[2]);
            result.setStudents(std);
            return result;
        }
        result.setException(new CustomException("学员信息有误"));
        return result;
    }

    public static void main(String[] args) throws IOException {
        StudentDome studentDome = new StudentDome();
        ResultStudent resultStudent = new ResultStudent();
        Scanner sc = new Scanner(System.in);
        System.out.println("选择查询的功能：1.根据学号查询学员信息 2.查看所有学员 3.添加学员 4.删除学员");
        System.out.println();
        switch (sc.nextInt()) {
            case 1:
                //根据id查询学员信息
                Scanner snoid = new Scanner(System.in);
                System.out.println("输入学号：");
                resultStudent = studentDome.read(snoid.nextInt());
                System.out.println("根据id查询:" + resultStudent.getStudent());
                break;
            case 2:
                //查询所有
                List<Student> list = studentDome.readAll();
                System.out.println("查询所有:" + list);
                break;
            case 3:
                //添加学员
                Scanner scanner = new Scanner(System.in);
                System.out.println("输入(学号,姓名,性别) 中间用,分割: ");
                resultStudent = studentDome.write(scanner.next());
                System.out.println(resultStudent.getException());
                break;

            case 4:
                //删除学员
                Scanner delsnoid = new Scanner(System.in);
                System.out.println("输入删除学员的学号: ");
                resultStudent = studentDome.del(delsnoid.nextInt());
                System.out.println(resultStudent.getException());
        }
    }
}