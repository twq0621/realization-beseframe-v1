package com.realization.framework.core.context.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.realization.framework.core.context.ClassScaner;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午05:44:11
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class ClassScanerImpl implements ClassScaner{

	@Override
	public List<String> getClassName(String packageName) {
		  String filePath = this.getClass().getClassLoader().getResource("").getPath() + packageName.replace(".", "/"); 
		  List<String> fileNames = getClassName(filePath, null); 
	        return fileNames; 
	}
	
	
	 private  List<String> getClassName(String filePath, List<String> className) { 
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
