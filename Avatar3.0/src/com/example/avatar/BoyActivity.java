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

public class BoyActivity extends Activity implements OnRecorderStateListener {
	private  RelativeLayout boy_background;
	private ImageView boy_body;
	private ImageView boy_face;
	private Button boy_btnStart;
	private Button boy_btnStop;
	private Button boy_clothes;
	private Button boy_bg_change;
	private Button boy_btnProfile;
	private Button boy_recognizeBtn=null;
	private Button boy_speakBtn=null;
	private int boy_rowindex;
	private int boy_index;
	private ViewRecorder recorder;
	private int boy_clothes_number;
	private int boy_background_number;
	String s;
	private String boy_accent=null;
	EditText boy_mytext = null;
	
	SpeechRecognizer mIat = null;
	SpeechSynthesizer mTts = null;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
		// 语音听写UI
	private RecognizerDialog mIatDialog;
	AnimationDrawable ani_boy_state=new AnimationDrawable();
	AnimationDrawable ani_boy_body=new AnimationDrawable();
	
	
@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"=553f4fa5");
		setContentView(R.layout.activity_boy);
		Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        boy_rowindex=bundle.getInt("index");
        boy_accent=bundle.getString("boy_accent");
        Log.v("boy_accent", boy_accent);
        if(boy_rowindex>0){
        	boy_index=boy_rowindex;
        }else{
        	int random=(int)Math.random()*15;
        	boy_index=random;
        	Log.v("index", boy_index+"");
        }
        boy_rowindex=0;
        intent=null;
        bundle=null;
        System.gc();
        initView(boy_index);
        recorder = new ViewRecorder(boy_background, "95af9864cd34");
        boy_btnStart=(Button) findViewById(R.id.boy_btnStart);
        boy_btnStop=(Button) findViewById(R.id.boy_btnStop);
        boy_btnProfile=(Button) findViewById(R.id.boy_btnProfile);
        boy_btnStart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startRecorder();
			}
        	
        });
        boy_btnStop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopRecorder();
			}
        	
        });
        boy_btnProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showProfile();
			}
        	
        });
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
    	mTts.setParameter(SpeechConstant.VOICE_NAME, boy_accent);// 设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "30");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
		
		boy_speakBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				s=boy_mytext.getText().toString();
				mTts.startSpeaking(
						boy_mytext.getText().toString(),
						mSynListener);
				setBoyFaceAni(Ani.getBoyAvatarMouthId(boy_index));
				Log.v("s", s);
				setBoyBodyAni(s);
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
		mIatDialog = new RecognizerDialog(BoyActivity.this, null);
		
		boy_recognizeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boy_mytext.setText(null);// 清空显示内容
				mIatResults.clear();
				 mIatDialog.setListener(recognizerDialogListener);
				 mIatDialog.show();
			}
		});
        
	}
	private void initView(int arg){
		boy_background=(RelativeLayout)findViewById(R.id.boy_background);
		boy_body=(ImageView)findViewById(R.id.boy_body);
		boy_face=(ImageView)findViewById(R.id.boy_face);
		boy_clothes=(Button)findViewById(R.id.boy_clothes);
		boy_bg_change=(Button)findViewById(R.id.boy_bg_change);
		boy_recognizeBtn=(Button)findViewById(R.id.boy_recognizeBtn);
		boy_speakBtn=(Button)findViewById(R.id.boy_speakBtn);
		boy_btnStart=(Button)findViewById(R.id.boy_btnStart);
		boy_btnStop=(Button)findViewById(R.id.boy_btnStop);
		boy_btnProfile=(Button)findViewById(R.id.boy_btnProfile);
		boy_mytext=(EditText)findViewById(R.id.boy_mytext);

		boy_background.setBackgroundResource(R.drawable.background);
		boy_background_number=1;
		boy_bg_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boy_background.setBackgroundResource(0);
				switch(boy_background_number){
					case 1:boy_background.setBackgroundResource(R.drawable.background1);boy_background_number=2;break;
					case 2:boy_background.setBackgroundResource(R.drawable.background2);boy_background_number=3;break;
					case 3:boy_background.setBackgroundResource(R.drawable.background3);boy_background_number=4;break;
					case 4:boy_background.setBackgroundResource(R.drawable.background4);boy_background_number=5;break;
					case 5:boy_background.setBackgroundResource(R.drawable.background5);boy_background_number=6;break;
					case 6:boy_background.setBackgroundResource(R.drawable.background);boy_background_number=1;break;
				}
			}
		});
		
		boy_body.setImageResource(R.drawable.boy_he_1);
		boy_clothes_number=1;
		
		setBoyFaceAni(Ani.getBoyAvatarEyeId(boy_index));
		
		boy_clothes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boy_body.setImageBitmap(null);
				Bitmap bm_body=null;
				switch(boy_clothes_number){
				case 1:bm_body=BitmapFactory.decodeResource(getResources(), R.drawable.boy1_he_1);boy_clothes_number=2;break;
				case 2:bm_body=BitmapFactory.decodeResource(getResources(), R.drawable.boy_he_1);boy_clothes_number=1;break;
				}
				boy_body.setImageBitmap(bm_body);
			}
		});
	}
	
	private void setBoyFaceAni(int resid){
		boy_face.setImageResource(resid);
		ani_boy_state=(AnimationDrawable)boy_face.getDrawable();
		if (ani_boy_state.isRunning()) {
			ani_boy_state.stop();
			ani_boy_state.start();
		} else {
			ani_boy_state.start();
		}		
	}
	private void setBoyBodyAni(String str){
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
				boy_body.setImageResource(Ani.getBoyAvatarActionId(i));
				ani_boy_body=(AnimationDrawable)boy_body.getDrawable();
				if(ani_boy_body.isRunning()){
					i=i-1;
				}else{
					ani_boy_body.start();
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

		boy_mytext.setText(resultBuffer.toString());
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
			if (recorder.isAvailable()) {
				recorder.setOnRecorderStateListener(this);
				recorder.startRecorder();
				recorder.startAuotRefreshRate(10);
				Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "not_availiable", Toast.LENGTH_SHORT).show();
			}
	    }	
		

		private void stopRecorder() {
			recorder.stopRecorder();
			Toast.makeText(this, "停止录制，合成视频中.....", Toast.LENGTH_SHORT).show();
		}

		private void showProfile() {
			recorder.showProfile();
		}
		
		public void onStateChange(Recorder recorder, int state) {
			if (state == Recorder.STATE_STOPPED) {
				this.recorder.setTitle("android_tom");
				this.recorder.addCustomAttr("score", "5000");
				this.recorder.addCustomAttr("name", "ShareRec Developer");
				this.recorder.addCustomAttr("brand", "hehe!");
				this.recorder.addCustomAttr("level", "10");
				this.recorder.showShare();
			}
		}
}
