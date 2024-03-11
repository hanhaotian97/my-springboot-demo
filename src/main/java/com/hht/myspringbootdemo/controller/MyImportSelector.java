package com.hht.myspringbootdemo.controller;

import com.hht.myspringbootdemo.entity.Apple;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("com.hht.myspringbootdemo.entity.User");
        strList.add("com.hht.myspringbootdemo.entity.Strawberry");
        strList.add(Apple.class.getName());
        String[] strings = new String[strList.size()];
        return strList.toArray(strings);
    }
}