package com.example.avatar;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharerec.recorder.OnRecorderStateListener;
import cn.sharerec.recorder.Recorder;
import cn.sharerec.recorder.ViewRecorder;

import com.example.method.Ani;
import com.example.method.JsonHelper;
import com.example.method.StringHelper;
import com.example.avatar.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GirlActivity extends Activity implements OnRecorderStateListener  {
	private  RelativeLayout girl_background;
	private ImageView girl_body;
	private ImageView girl_face;
	private Button girl_btnStart;
	private Button girl_btnStop;
	private Button girl_clothes;
	private Button girl_bg_change;
	private Button girl_btnProfile;
	private Button girl_recognizeBtn=null;
	private Button girl_speakBtn=null;
	private int girl_rowindex;
	private int girl_index;
	private ViewRecorder recorder1;
	private int girl_clothes_number;
	private int girl_background_number;
	String s;
	
	EditText girl_mytext = null;
	
	SpeechRecognizer mIat = null;
	SpeechSynthesizer mTts = null;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
		// 语音听写UI
	private RecognizerDialog mIatDialog;
	AnimationDrawable ani_girl_state=new AnimationDrawable();
	AnimationDrawable ani_girl_body=new AnimationDrawable();
	
	
	private String girl_accent=null;
	
	
@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"=553f4fa5");
		setContentView(R.layout.activity_girl);
		Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        girl_rowindex=bundle.getInt("index");
        girl_accent=bundle.getString("girl_accent");
        if(girl_rowindex>0){
        	girl_index=girl_rowindex;
        }else{
        	int random=(int)Math.random()*15;
        	girl_index=random;
        	Log.v("index", girl_index+"");
        }
        girl_rowindex=0;
        intent=null;
        bundle=null;
        System.gc();
        initView(girl_index);
        recorder1 = new ViewRecorder(girl_background, "95af9864cd34");
        girl_btnStart=(Button) findViewById(R.id.girl_btnStart);
        girl_btnStop=(Button) findViewById(R.id.girl_btnStop);
        girl_btnProfile=(Button) findViewById(R.id.girl_btnProfile);
        girl_btnStart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startRecorder();
			}
        	
        });
        girl_btnStop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopRecorder();
			}
        	
        });
        girl_btnProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showProfile();
			}
        	
        });
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME,girl_accent);// 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
        
		girl_speakBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				s=girl_mytext.getText().toString();
				mTts.startSpeaking(
						girl_mytext.getText().toString(),
						mSynListener);
				setGirlFaceAni(Ani.getGirlAvatarMouthId(girl_index));
				Log.v("s", s);
				setGirlBodyAni(s);
				mIat.stopListening();
			}
		});
        
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		mIat.setParameter(SpeechConstant.ASR_PTT, "0");
		mIat.setParameter(SpeechConstant.ASR_DWA, "0");
		mIatDialog = new RecognizerDialog(GirlActivity.this, null);
		
		girl_recognizeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				girl_mytext.setText(null);// 清空显示内容
				mIatResults.clear();
				 mIatDialog.setListener(recognizerDialogListener);
				 mIatDialog.show();
			}
		});
        
	}
	private void initView(int arg){
		girl_background=(RelativeLayout)findViewById(R.id.girl_background);
		girl_body=(ImageView)findViewById(R.id.girl_body);
		girl_face=(ImageView)findViewById(R.id.girl_face);
		girl_clothes=(Button)findViewById(R.id.girl_clothes);
		girl_bg_change=(Button)findViewById(R.id.girl_bg_change);
		girl_recognizeBtn=(Button)findViewById(R.id.girl_recognizeBtn);
		girl_speakBtn=(Button)findViewById(R.id.girl_speakBtn);
		girl_btnStart=(Button)findViewById(R.id.girl_btnStart);
		girl_btnStop=(Button)findViewById(R.id.girl_btnStop);
		girl_btnProfile=(Button)findViewById(R.id.girl_btnProfile);
		girl_mytext=(EditText)findViewById(R.id.girl_mytext);
		
	

		girl_background.setBackgroundResource(R.drawable.background);
		girl_background_number=1;
		girl_bg_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				girl_background.setBackgroundResource(0);
				switch(girl_background_number){
					case 1:girl_background.setBackgroundResource(R.drawable.background1);girl_background_number=2;break;
					case 2:girl_background.setBackgroundResource(R.drawable.background2);girl_background_number=3;break;
					case 3:girl_background.setBackgroundResource(R.drawable.background3);girl_background_number=4;break;
					case 4:girl_background.setBackgroundResource(R.drawable.background4);girl_background_number=5;break;
					case 5:girl_background.setBackgroundResource(R.drawable.background5);girl_background_number=6;break;
					case 6:girl_background.setBackgroundResource(R.drawable.background);girl_background_number=1;break;
				}
			}
		});
		
		girl_body.setImageResource(R.drawable.girl_he_1);
		girl_clothes_number=1;
		
		setGirlFaceAni(Ani.getGirlAvatarEyeId(girl_index));
		
		girl_clothes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				girl_body.setImageBitmap(null);
				Bitmap bm_body=null;
				switch(girl_clothes_number){
				case 1:
					 bm_body=BitmapFactory.decodeResource(getResources(), R.drawable.girl1_you_1);
					 girl_clothes_number=2;
					break;
				case 2:
					bm_body=BitmapFactory.decodeResource(getResources(), R.drawable.girl_he_1);
					girl_clothes_number=1;
					break;
				}
				girl_body.setImageBitmap(bm_body);
			}
		});
	}
	
	private void setGirlFaceAni(int resid){
		girl_face.setImageResource(resid);
		ani_girl_state=(AnimationDrawable)girl_face.getDrawable();
		if (ani_girl_state.isRunning()) {
			ani_girl_state.stop();
			ani_girl_state.start();
		} else {
			ani_girl_state.start();
		}		
	}
	private void setGirlBodyAni(String str){
		int i=0;
		int[] stringIndex=new int[5];
		stringIndex=StringHelper.getStringIndex(str);
		Log.v("stringIndex0", stringIndex[0]+"");
		Log.v("stringIndex1", stringIndex[0]+"");
		Log.v("stringIndex2", stringIndex[0]+"");
		Log.v("stringIndex3", stringIndex[0]+"");
		Log.v("stringIndex4", stringIndex[0]+"");
		while(i<5){
			if(stringIndex[i]>0){
				girl_body.setImageResource(Ani.getGirlAvatarActionId(i));
				ani_girl_body=(AnimationDrawable)girl_body.getDrawable();
				if(ani_girl_body.isRunning()){
					i=i-1;
				}else{
					ani_girl_body.start();
				}
			}
			i++;
		}
		
	}
	
	private void printResult(RecognizerResult results) {
		String text = JsonHelper.parseIatResult(results.getResultString());

		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}

		girl_mytext.setText(resultBuffer.toString());
	}
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.v("ihg", "" + results.getResultString());
//			mytext.setText(results.getResultString());
			printResult(results);
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
		}

	};
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d("ihg", "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d("ihg", "初始化失败，错误码：" + code);
			}
		}
	};

	// 合成监听器
	private SynthesizerListener mSynListener = new SynthesizerListener() {
		// 会话结束回调接口，没有错误时，error为null
		public void onCompleted(SpeechError error) {
		}
		// 缓冲进度回调
		// percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在
		// 文本中结束位置，info为附加信息。
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
		}
		// 开始播放
		public void onSpeakBegin() {
		}
		// 暂停播放
		public void onSpeakPaused() {
		}
		// 播放进度回调
		// percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文
		// 本中结束位置.
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}
		// 恢复播放回调接口
		public void onSpeakResumed() {
		}
		// 会话事件回调接口
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
	 private void startRecorder() {
			if (recorder1.isAvailable()) {
				recorder1.setOnRecorderStateListener(this);
				recorder1.startRecorder();
				recorder1.startAuotRefreshRate(10);
				Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "not_availiable", Toast.LENGTH_SHORT).show();
			}
	    }	
		

		private void stopRecorder() {
			recorder1.stopRecorder();
			Toast.makeText(this, "停止录制，合成视频中.....", Toast.LENGTH_SHORT).show();
		}

		private void showProfile() {
			recorder1.showProfile();
		}
		
		public void onStateChange(Recorder recorder, int state) {
			if (state == Recorder.STATE_STOPPED) {
				// show share page
				this.recorder1.setTitle("android_tom");
				this.recorder1.addCustomAttr("score", "5000");
				this.recorder1.addCustomAttr("name", "ShareRec Developer");
				this.recorder1.addCustomAttr("brand", "hehe!");
				this.recorder1.addCustomAttr("level", "10");
				this.recorder1.showShare();
			}
		}
}
