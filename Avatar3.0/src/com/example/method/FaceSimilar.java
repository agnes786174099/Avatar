package com.example.method;

import com.example.res.FaceData;

public class FaceSimilar {
	public double[] user_face;
	private double similar=0;
	public FaceSimilar(double[] face){
		user_face=face;
	}
	public double[] getAllBoySimilar(){
		double[] similarAll=new double[15];
		similarAll[0]=getSimilar(user_face,FaceData.boyface1);
		similarAll[1]=getSimilar(user_face,FaceData.boyface2);
		similarAll[2]=getSimilar(user_face,FaceData.boyface3);
		similarAll[3]=getSimilar(user_face,FaceData.boyface4);
		similarAll[4]=getSimilar(user_face,FaceData.boyface5);
		similarAll[5]=getSimilar(user_face,FaceData.boyface6);
		similarAll[6]=getSimilar(user_face,FaceData.boyface7);
		similarAll[7]=getSimilar(user_face,FaceData.boyface8);
		similarAll[8]=getSimilar(user_face,FaceData.boyface9);
		similarAll[9]=getSimilar(user_face,FaceData.boyface10);
		similarAll[10]=getSimilar(user_face,FaceData.boyface11);
		similarAll[11]=getSimilar(user_face,FaceData.boyface12);
		similarAll[12]=getSimilar(user_face,FaceData.boyface13);
		similarAll[13]=getSimilar(user_face,FaceData.boyface14);
		similarAll[14]=getSimilar(user_face,FaceData.boyface15);
		return similarAll;
	}
	public double[] getAllGirlSimilar(){
		double[] similarAll=new double[15];
		similarAll[0]=getSimilar(user_face,FaceData.girlface1);
		similarAll[1]=getSimilar(user_face,FaceData.girlface2);
		similarAll[2]=getSimilar(user_face,FaceData.girlface3);
		similarAll[3]=getSimilar(user_face,FaceData.girlface4);
		similarAll[4]=getSimilar(user_face,FaceData.girlface5);
		similarAll[5]=getSimilar(user_face,FaceData.girlface6);
		similarAll[6]=getSimilar(user_face,FaceData.girlface7);
		similarAll[7]=getSimilar(user_face,FaceData.girlface8);
		similarAll[8]=getSimilar(user_face,FaceData.girlface9);
		similarAll[9]=getSimilar(user_face,FaceData.girlface10);
		similarAll[10]=getSimilar(user_face,FaceData.girlface11);
		similarAll[11]=getSimilar(user_face,FaceData.girlface12);
		similarAll[12]=getSimilar(user_face,FaceData.girlface13);
		similarAll[13]=getSimilar(user_face,FaceData.girlface14);
		similarAll[14]=getSimilar(user_face,FaceData.girlface15);
		return similarAll;
	}
	private double getSimilar(double[] figure1,double[] figure2){
		double[] tem=new double[figure1.length/2];
		double sum=0;
		for(int i=0;i<figure1.length/2;i++){
			tem[i]=Math.sqrt((figure1[2*i]-figure2[2*i])*(figure1[2*i]-figure2[2*i])+
					(figure1[2*i+1]-figure2[2*i+1])*(figure1[2*i+1]-figure2[2*i+1]));
		}
		for(int j=0;j<tem.length;j++){
			sum=tem[j]+sum;
		}
		similar=sum/(tem.length*1.0);
		return similar;
	}
	
	
}
