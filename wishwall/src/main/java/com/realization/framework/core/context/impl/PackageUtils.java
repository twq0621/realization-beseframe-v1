package com.realization.framework.core.context.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageUtils { 
     
    public static void main(String[] args) { 
        String packageName = "com.realization"; 
 
        List<String> classNames = getClassName(packageName); 
        for (String className : classNames) { 
            System.out.println(className); 
        } 
    } 
 
    public static List<String> getClassName(String packageName) { 
    	System.err.println(PackageUtils.class.getClassLoader().getResource(""));
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "/"); 
        List<String> fileNames = getClassName(filePath, null); 
        return fileNames; 
    } 
 
    private static List<String> getClassName(String filePath, List<String> className) { 
        List<String> myClassName = new ArrayList<String>(); 
        File file = new File(filePath); 
        File[] childFiles = file.listFiles(); 
        for (File childFile : childFiles) { 
            if (childFile.isDirectory()) { 
                myClassName.addAll(getClassName(childFile.getPath(), myClassName)); 
            } else { 
                String childFilePath = childFile.getPath(); 
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf(".")); 
                childFilePath = childFilePath.replace("\\", "."); 
                myClassName.add(childFilePath); 
            } 
        } 
 
        return myClassName; 
    } 
} 