/*
 *����Bf�㷨��ģʽƥ��
*/


package com.example.method;

import android.util.Log;


public class StringHelper {
	public static final String me="��";
	public static final String you="��";
	public  static final String other="����������";
	public static  final String happy="���˿��ֿ���";
	public static final String annoy="�������Ʒ�������";
	public static int[] stringIndex;
	
	//�õ����������ַ���ƥ����±�
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
						System.out.println("�ո��");
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
