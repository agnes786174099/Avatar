package com.example.avatar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.method.FaceSimilar;
import com.example.method.Utility;
import com.iflytek.cloud.FaceRequest;
import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;

public class BoyFaceActivity extends Activity implements OnClickListener {
	private final int REQUEST_PICTURE_CHOOSE = 1;
	private final int REQUEST_CAMERA_IMAGE = 2;
	private Bitmap img = null;
	private byte[] imgData = null;
	private Toast mToast;
	private ProgressDialog proDialog;
	private File pictureFile;
	private double[] facePoint=new double[46];
	private int index;
	// FaceRequest对象，集成了人脸识别的各种功能
	private FaceRequest face;
	private int boy_face_index;
	private String boy_accent="xiaoxin";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"=553f4fa5");
		setContentView(R.layout.activity_boyface);
		findViewById(R.id.boy_pick).setOnClickListener(this);
		findViewById(R.id.boy_sure).setOnClickListener(this);
		findViewById(R.id.boy_align).setOnClickListener(this);
		findViewById(R.id.boy_accent).setOnClickListener(this);
		findViewById(R.id.boy_synthesis).setOnClickListener(this);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

		proDialog = new ProgressDialog(this);
		proDialog.setCancelable(true);
		proDialog.setTitle("请稍后");
		// cancel进度框时,取消正在进行的操作
		proDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (null != face) {
					face.cancel();
				}
			}
		});
		
		// 初始化
		face = new FaceRequest(this);
	}


	private void detect(JSONObject obj) throws JSONException {
		int ret = obj.getInt("ret");
		if (ret != 0) {
			showTip("检测失败");
			return;
		}

		if ("success".equals(obj.get("rst"))) {
			
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStrokeWidth(Math.max(img.getWidth(), img.getHeight()) / 100f);

			Bitmap bitmap = Bitmap.createBitmap(img.getWidth(),
					img.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(img, new Matrix(), null);
			JSONArray faceArray = obj.getJSONArray("face");
			for (int i = 0; i < faceArray.length(); i++) {
				float x1 = (float) faceArray.getJSONObject(i)
						.getJSONObject("position").getDouble("left");
				facePoint[0]=x1;
				Log.v("facePoint[0]", facePoint[0]+"");
				float y1 = (float) faceArray.getJSONObject(i)
						.getJSONObject("position").getDouble("top");
				facePoint[1]=y1;
				Log.v("facePoint[1]", facePoint[1]+"");
				float x2 = (float) faceArray.getJSONObject(i)
						.getJSONObject("position").getDouble("right");
				facePoint[2]=x2;
				Log.v("facePoint[2]", facePoint[2]+"");
				float y2 = (float) faceArray.getJSONObject(i)
						.getJSONObject("position").getDouble("bottom");
				facePoint[3]=y2;
				Log.v("facePoint[3]", facePoint[3]+"");
				paint.setStyle(Style.STROKE);
				canvas.drawRect(new Rect((int)x1, (int)y1, (int)x2, (int)y2), 
						paint);
			}
			img = bitmap;
			((ImageView) findViewById(R.id.img)).setImageBitmap(img);
		} else {
			showTip("检测失败");
		}
	}

	private void align(JSONObject obj) throws JSONException {
		int ret = obj.getInt("ret");
		if (ret != 0) {
			showTip("聚焦失败");
			return;
		}
		if ("success".equals(obj.get("rst"))) {
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setStrokeWidth(Math.max(img.getWidth(), img.getHeight()) / 100f);
			
			Paint paint1 = new Paint();
			paint1.setColor(Color.BLACK);
			paint1.setStrokeWidth(Math.max(img.getWidth(), img.getHeight()) / 100f);
			
			Bitmap bitmap = Bitmap.createBitmap(img.getWidth(),
					img.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(img, new Matrix(), null);
			index=3;
			JSONArray faceArray = obj.getJSONArray("result");
			for (int i = 0; i < faceArray.length(); i++) {
				JSONObject landmark = faceArray.getJSONObject(i).getJSONObject(
						"landmark");
				Log.v("landmark", landmark+"");
				Iterator it = landmark.keys();
				Log.v("it", it+"");
				while (index<=43) {
					String key = (String) it.next();
					JSONObject postion = landmark.getJSONObject(key);
					index++;
					facePoint[index]= postion.getDouble("x");
					Log.v("facePoint"+index, facePoint[index]+"");
					index++;
					facePoint[index]= postion.getDouble("y");
					Log.v("facePoint"+index, facePoint[index]+"");
					canvas.drawPoint((float) postion.getDouble("x"),
							(float) postion.getDouble("y"), paint);
				}
			}

			img = bitmap;
			((ImageView) findViewById(R.id.img)).setImageBitmap(img);
		} else {
			showTip("聚焦失败");
		}
	}
	
	private RequestListener mRequestListener = new RequestListener() {

		@Override
		public void onEvent(int eventType, Bundle params) {
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			if (null != proDialog) {
				proDialog.dismiss();
			}

			try {
				String result = new String(buffer, "utf-8");
				Log.d("FaceDemo", result);
				
				JSONObject object = new JSONObject(result);
				String type = object.optString("sst");
				if("detect".equals(type)) {
					detect(object);
				} else if ("align".equals(type)) {
					align(object);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (null != proDialog) {
				proDialog.dismiss();
			}

			if (error != null) {
				showTip(error.getPlainDescription(true));
			} else {
				//
			}
		}
	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.boy_pick:
			final AlertDialog.Builder builder =new AlertDialog.Builder(BoyFaceActivity.this);
			builder.setTitle("请选择");
			builder.setPositiveButton("相册", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_PICK);
					startActivityForResult(intent, REQUEST_PICTURE_CHOOSE);
				}
			});
			builder.setNegativeButton("相机", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					pictureFile = new File(Environment.getExternalStorageDirectory(), 
							"picture" + System.currentTimeMillis()/1000 + ".jpg");
					// 启动拍照,并保存到临时文件
					Intent mIntent = new Intent();
					mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
					mIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
					startActivityForResult(mIntent, REQUEST_CAMERA_IMAGE);
				}
			});
			AlertDialog alert=builder.create();
			alert.show();
			break;
		case R.id.boy_sure:
			if (null != imgData) {
				proDialog.setMessage("检测中...");
				proDialog.show();
				face.setParameter(SpeechConstant.WFR_SST, "detect");
				face.sendRequest(imgData, mRequestListener);
			} else {
				showTip("请选择图片后再检测");
			}
			break;
		case R.id.boy_align:
			if (null != imgData) {
				proDialog.setMessage("聚集中 ...");
				proDialog.show();
				face.setParameter(SpeechConstant.WFR_SST, "align");
				face.sendRequest(imgData, mRequestListener);
			} else {
				showTip("请选择图片后再聚集");
			}
			break;
		case R.id.boy_accent:
			final AlertDialog.Builder builder1=new AlertDialog.Builder(BoyFaceActivity.this);
		  	builder1.setTitle("请选择发音人");
		  	final String[] people = {"小新","小坤(河南话)","小强(湖南话)","老孙(老年人)","小宇 (青年)"};
		  	
		    builder1.setSingleChoiceItems(people, 0, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                	if(people[which]=="小新"){
               		 boy_accent="vixx";
               	 }else if(people[which]=="小坤(河南话)"){
               		 boy_accent="vixk";
               	 }else if(people[which]=="小强(湖南话)"){
               		 boy_accent="vixqa";
               	 }else if (people[which]=="老孙(老年人)"){
               		 boy_accent="vils";
               	 }else if(people[which]=="小宇 (青年)"){
               		 boy_accent="xiaoyu";
               	 }
                }
            });
            builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {}
            });
            builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {}
            });
            AlertDialog alert1=builder1.create();
            alert1.show();
             Log.v("boy_accent", boy_accent);
		break;
		case R.id.boy_synthesis: 
			if (null != imgData) {
				proDialog.setMessage("合成中 ...");
				proDialog.show();
				FaceSimilar faceResult=new FaceSimilar(facePoint);
				double[] similarResult=new double[15];
				similarResult=faceResult.getAllBoySimilar();
				for(int i=0;i<15;i++){
				Log.v("similarResult"+i, similarResult[i]+"");
				}
				boy_face_index=Utility.maxIndex(similarResult);
				Log.v("boy_face_index", boy_face_index+"");
			
				Intent girl_intent=new Intent(BoyFaceActivity.this,BoyActivity.class);
				Bundle bundle = new Bundle();
				 bundle.putInt("index", boy_face_index); 
				 Log.v("boy_accent1", boy_accent);
				 bundle.putString("boy_accent", boy_accent);
				 girl_intent.putExtras(bundle); 
				 startActivity(girl_intent);
				
			} else {
				showTip("请选择图片后再合成");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		
		String fileSrc = null;
		if (requestCode == REQUEST_PICTURE_CHOOSE) {
			if ("file".equals(data.getData().getScheme())) {
				// 有些低版本机型返回的Uri模式为file
				fileSrc = data.getData().getPath();
			} else {
				// Uri模型为content
				String[] proj = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(data.getData(), proj,
						null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				fileSrc = cursor.getString(idx);
				cursor.close();
			}
		} else if (requestCode == REQUEST_CAMERA_IMAGE) {
			fileSrc = pictureFile.getAbsolutePath();
			updateGallery(fileSrc);
		}
		// 获取图片的宽和高
		Options options = new Options();
		options.inJustDecodeBounds = true;
		img = BitmapFactory.decodeFile(fileSrc, options);

		// 压缩图片
		options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
				(double) options.outWidth / 1024f,
				(double) options.outHeight / 1024f)));
		options.inJustDecodeBounds = false;
		img = BitmapFactory.decodeFile(fileSrc, options);
		
		// 部分手机会对图片做旋转，这里检测旋转角度
		int degree = readPictureDegree(fileSrc);
		if (degree != 0) {
			// 把图片旋转为正的方向
			img = rotateImage(degree, img);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//可根据流量及网络状况对图片进行压缩
		img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		imgData = baos.toByteArray();

		((ImageView) findViewById(R.id.img)).setImageBitmap(img);
	}

	@Override
	public void finish() {
		if (null != proDialog) {
			proDialog.dismiss();
		}
		super.finish();
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	private void updateGallery(String filename) {
		MediaScannerConnection.scanFile(this, new String[] {filename}, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					
					@Override
					public void onScanCompleted(String path, Uri uri) {

					}
				});
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotateImage(int angle, Bitmap bitmap) {
		// 图片旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 得到旋转后的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}
}
