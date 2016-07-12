package com.example.method;

import android.R.raw;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.avatar.R;

public class Ani {

	public static int[] boy_avatar_eye;
	public static int[] girl_avatar_eye;
	public static int[] boy_avatar_mouth;
	public static int[] girl_avatar_mouth;
	public static int[] girl_action;
	public static int[] boy_action;
//	男脸眼睛动画
	public static int[] getBoyAvatarAniEye(){
		if(boy_avatar_eye==null){
			boy_avatar_eye=new int[]{
					R.drawable.ani_boyface1_eye,
					R.drawable.ani_boyface2_eye,
					R.drawable.ani_boyface3_eye,
					R.drawable.ani_boyface4_eye,
					R.drawable.ani_boyface5_eye,
					R.drawable.ani_boyface6_eye,
					R.drawable.ani_boyface7_eye,
					R.drawable.ani_boyface8_eye,
					R.drawable.ani_boyface9_eye,
					R.drawable.ani_boyface10_eye,
					R.drawable.ani_boyface11_eye,
					R.drawable.ani_boyface12_eye,
					R.drawable.ani_boyface13_eye,
					R.drawable.ani_boyface14_eye,
					R.drawable.ani_boyface15_eye,
			};
		}
		return boy_avatar_eye;
	}
	public static int getBoyAvatarEyeId(int index){
		getBoyAvatarAniEye();
		int id=boy_avatar_eye[index];
		return id;
	}
//	男脸嘴动画
	public static int[] getBoyAvatarAniMouth(){
		if(boy_avatar_mouth==null){
			boy_avatar_mouth=new int[]{
					R.drawable.ani_boyface1_mouth,
					R.drawable.ani_boyface2_mouth,
					R.drawable.ani_boyface3_mouth,
					R.drawable.ani_boyface4_mouth,
					R.drawable.ani_boyface5_mouth,
					R.drawable.ani_boyface6_mouth,
					R.drawable.ani_boyface7_mouth,
					R.drawable.ani_boyface8_mouth,
					R.drawable.ani_boyface9_mouth,
					R.drawable.ani_boyface10_mouth,
					R.drawable.ani_boyface11_mouth,
					R.drawable.ani_boyface12_mouth,
					R.drawable.ani_boyface13_mouth,
					R.drawable.ani_boyface14_mouth,
					R.drawable.ani_boyface15_mouth
			};
		}
		return boy_avatar_mouth;
	}
	public static int getBoyAvatarMouthId(int index){
		getBoyAvatarAniMouth();
		int id=boy_avatar_mouth[index];
		return id;
	}
	
//	女脸眼睛动画
	public  static int[] getGirlAvatarAniEye(){
		if(girl_avatar_eye==null){
			girl_avatar_eye=new int[]{
					R.drawable.ani_girlface1_eye,
					R.drawable.ani_girlface2_eye,
					R.drawable.ani_girlface3_eye,
					R.drawable.ani_girlface4_eye,
					R.drawable.ani_girlface5_eye,
					R.drawable.ani_girlface6_eye,
					R.drawable.ani_girlface7_eye,
					R.drawable.ani_girlface8_eye,
					R.drawable.ani_girlface9_eye,
					R.drawable.ani_girlface10_eye,
					R.drawable.ani_girlface11_eye,
					R.drawable.ani_girlface12_eye,
					R.drawable.ani_girlface13_eye,
					R.drawable.ani_girlface14_eye,
					R.drawable.ani_girlface15_eye
			
			};
		}
		return girl_avatar_eye;
	}
	public static int getGirlAvatarEyeId(int index){
		getGirlAvatarAniEye();
		int id=girl_avatar_eye[index];
		return id;
	}
//	女脸嘴动画
	public static int[] getGirlAvatarAniMouth(){
		if(girl_avatar_mouth==null){
			girl_avatar_mouth=new int[]{
					R.drawable.ani_girlface1_mouth,
					R.drawable.ani_girlface2_mouth,
					R.drawable.ani_girlface3_mouth,
					R.drawable.ani_girlface4_mouth,
					R.drawable.ani_girlface5_mouth,
					R.drawable.ani_girlface6_mouth,
					R.drawable.ani_girlface7_mouth,
					R.drawable.ani_girlface8_mouth,
					R.drawable.ani_girlface9_mouth,
					R.drawable.ani_girlface10_mouth,
					R.drawable.ani_girlface11_mouth,
					R.drawable.ani_girlface12_mouth,
					R.drawable.ani_girlface13_mouth,
					R.drawable.ani_girlface14_mouth,
					R.drawable.ani_girlface15_mouth,
			};
		}
		return girl_avatar_mouth;
	}
	
	public static int getGirlAvatarMouthId(int index){
		getGirlAvatarAniMouth();
		int id=girl_avatar_mouth[index];
		return id;
	}
//	女生动作
	public static int[] getGirlAvatarAction(){
		if(girl_action==null){
			girl_action=new int[]{
					R.drawable.action_girl_me,
					R.drawable.action_girl_you,
					R.drawable.action_girl_he,
					R.drawable.action_girl_happy,
					R.drawable.action_girl_annoy
			};
		}
		return girl_action;
	}
	
	public static int getGirlAvatarActionId(int index){
		getGirlAvatarAction();
		int id=girl_action[index];
		return id;
	}
	
//	男生动作
	public static int[] getBoyAvatarAction(){
		if(boy_action==null){
			boy_action=new int[]{
					R.drawable.action_boy_me,
					R.drawable.action_boy_you,
					R.drawable.action_boy_he,
					R.drawable.action_boy_happy,
					R.drawable.action_boy_annoy
			};
		}
		return boy_action;
	}
	
	public static int getBoyAvatarActionId(int index){
		getBoyAvatarAction();
		int id=boy_action[index];
		return id;
	}
	


}
