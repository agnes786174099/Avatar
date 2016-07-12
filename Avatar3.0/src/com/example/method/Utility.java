package com.example.method;

import android.util.Log;


public class Utility {
	 public static int maxIndex(double[] arg){
	    	double[] array1=new double[arg.length];
	    	double[] array2=new double[arg.length];
	    	for(int k=0;k<arg.length;k++){
	    		array1[k]=arg[k];
	    		Log.v("array1"+k, array1[k]+"");
	    		array2[k]=array1[k];
	    	}
	    	int j;
	    	int index=0;
	    	double tem=0;
	    	for(j=0;j<array2.length-1;j++){
	    	if(array2[j]<array2[j+1]){
	    		tem=array2[j+1];
	    		array2[j+1]=array2[j];
	    		array2[j]=tem;
	    	}
	    	}
	    	while(array2[arg.length-1]!=array1[index]){
	    		index++;
	    	}
	    	Log.v("maxindex", index+"");
	    	return index;
	    }
}
