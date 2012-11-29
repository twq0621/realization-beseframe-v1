package com.realization.framework.common.util.security;

import java.util.Random;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-14   下午09:36:03
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class Util {

	public static String getRandomInt(int p){
		char[] block = {'0','1','2','3','4','5','6','7','8','9'};
		Random random = new Random();
		StringBuffer sb =new StringBuffer();
		for(int i=0; i  <p;i++){
			int m =random.nextInt(10);
			sb.append(block[m]);
		}
		System.err.print(" :");
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		Random random = new Random();
		for(int i=0;i<100;i++){
		System.err.println(random.nextInt());
		}
	}
}
