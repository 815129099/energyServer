package com.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class testStream {

    public static void main(String[] args){
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.add(1);
        list.add(9);
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(6);
        list.add(1);

        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);
        list1.add(6);
        list1.add(7);
        //list.stream().sorted(Comparator.comparing(integer -> ))
        //List<Integer> collect = list1.stream().filter(i->list.stream().noneMatch(j->i.equals(j))).collect(Collectors.toList());
        System.out.println(list.stream().reduce((a,b)->a+b).get());
        //list.stream().forEach();
        //List<Student> collect = stuList.stream().filter(student -> stuClassList.stream().noneMatch(stuClass -> stuClass.getStudentNo().equals(student.getStudentNo()))).collect(Collectors.toList());
       // collect.stream().forEach(k->System.out.println(k));
    }
}
