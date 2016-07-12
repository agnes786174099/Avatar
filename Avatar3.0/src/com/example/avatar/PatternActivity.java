package com.example.avatar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class PatternActivity extends Activity{
	private Button pattern_boy;
	private Button pattern_girl;
	private Button info;
@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pattern);
		pattern_boy=(Button)findViewById(R.id.pattern_boy);
		pattern_girl=(Button)findViewById(R.id.pattern_girl);
		info=(Button)findViewById(R.id.info);
		
		pattern_boy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent();
				intent.setClass(PatternActivity.this,BoyFaceActivity.class);
				startActivity(intent);
				finish();
			}
		});
		pattern_girl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent();
				intent.setClass(PatternActivity.this,GirlFaceActivity.class);
				startActivity(intent);
				finish();
			}
		});
		info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final AlertDialog.Builder builder=new AlertDialog.Builder(PatternActivity.this);
				builder.setTitle("关于Avatar");
				builder.setMessage("您好！欢迎使用Avatar。\n"+"这是一款玩偶合成游戏。进入不同模式后，选择照片，检测照片中有人脸之后，经过" +
						"提取信息，可以合成玩偶。\n"+"之后，您可以点击屏幕上方的服装和背景图标，可以更换玩偶的服装和背景；点击屏幕下方的语音输入和" +
						"语音播放，可以让玩偶复述您的话。另外，输入语音后，点击视频录制，语音播放完，玩偶动作结束，" +
						"停止录制，可以分享视频到您的微博。\n"+"祝您Avatar之旅愉快。");
				builder.create().show();
			}
		});
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
		this.finish();
		
	}
	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
}
