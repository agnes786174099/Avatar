/*
 *基于Bf算法的模式匹配
*/


package com.example.method;

import android.util.Log;


public class StringHelper {
	public static final String me="我";
	public static final String you="你";
	public  static final String other="他它她他们";
	public static  final String happy="高兴快乐开心";
	public static final String annoy="纠结郁闷烦恼讨厌";
	public static int[] stringIndex;
	
	//得到库中所有字符串匹配的下标
	public static int[] getStringIndex(String str){
		 
		int iFlag=getMatchIndex(str,me);
		int youFlag=getMatchIndex(str,you);
		int otherFlag=getMatchIndex(str,other);
		int happyFlag=getMatchIndex(str,happy);
		int annoyFlag=getMatchIndex(str,annoy);
		stringIndex=new int[]{iFlag,youFlag,otherFlag,annoyFlag,happyFlag};
		return stringIndex;
	}
	
	public static int getMatchIndex(String s1,String s2){
					
		char c;
		int flag=0;
		char[] same=s1.toCharArray();
		int count=0,j;
		int i,k;
		for(i=0;i<s1.length();i++){
			c=s1.charAt(i);
			out:for(k=0;k<s2.length();k++){
				if(c==(s2.charAt(k))){
					for(j=0;j<count;j++){
						if(c==same[j])
							break out;
					}
					same[count]=c;
					count++;
					if(c==' '){
						System.out.println("空格键");
						break;
					}
					System.out.print(c);
					flag++;
					break;
				}

			}

		}
		return flag;
	}
}
