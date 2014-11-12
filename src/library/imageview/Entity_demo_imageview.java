package library.imageview;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Entity_demo_imageview extends Activity{
	PhotoViewAttacher mAttacher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView imageView=new ImageView(this);
		//实例化imageview控件--------------------------------------------------
		mAttacher = new PhotoViewAttacher(imageView);
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//清理内存--------------------------------------------------------------
		mAttacher.cleanup();
	}
}
