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
		//ʵ����imageview�ؼ�--------------------------------------------------
		mAttacher = new PhotoViewAttacher(imageView);
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//�����ڴ�--------------------------------------------------------------
		mAttacher.cleanup();
	}
}
