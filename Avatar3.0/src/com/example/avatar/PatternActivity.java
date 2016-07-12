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
				builder.setTitle("����Avatar");
				builder.setMessage("���ã���ӭʹ��Avatar��\n"+"����һ����ż�ϳ���Ϸ�����벻ͬģʽ��ѡ����Ƭ�������Ƭ��������֮�󣬾���" +
						"��ȡ��Ϣ�����Ժϳ���ż��\n"+"֮�������Ե����Ļ�Ϸ��ķ�װ�ͱ���ͼ�꣬���Ը�����ż�ķ�װ�ͱ����������Ļ�·������������" +
						"�������ţ���������ż�������Ļ������⣬���������󣬵����Ƶ¼�ƣ����������꣬��ż����������" +
						"ֹͣ¼�ƣ����Է�����Ƶ������΢����\n"+"ף��Avatar֮����졣");
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
