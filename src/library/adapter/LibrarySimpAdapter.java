package library.adapter;

import java.util.ArrayList;
import java.util.List;

import com.my_library.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 类描述： 适配数组或者list<String>的适配器
 *
 * @author 祝志国:2014-5-22
 */
public class LibrarySimpAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater layoutInflater;
	private Object mylist;
	private Weights weight;
	public LibrarySimpAdapter(Activity activity,ArrayList<String> arrayList) 
	{
		this.activity=activity;
		this.mylist=arrayList;
		layoutInflater=activity.getLayoutInflater();
	}
	
	public LibrarySimpAdapter(Activity activity,String [] stringArray) 
	{
		this.activity=activity;
		this.mylist=stringArray;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mylist.getClass()==ArrayList.class) 
		{
			return ((ArrayList<String>)mylist).size();
		}else if (mylist.getClass()==String[].class) 
		{
			return ((String[])mylist).length;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) 
		{
			weight=new Weights();
			convertView=layoutInflater.inflate(R.layout.simpadapter_list_textview, null);
			weight.text=(TextView)convertView.findViewById(R.id.simpadapter_list_text);
			convertView.setTag(weight);
		}else
		{
			weight=(Weights)convertView.getTag();
		}
		
		weight.text.setText(getString(position));
		
		return convertView;
	}

	final class Weights 
	{
		private TextView text;
		
	}
	
	
	/**获取资源
	 * @param position
	 * @return
	 */
	public String getString(int position)
	{
		if (mylist.getClass()==ArrayList.class) 
		{
			return ((ArrayList<String>)mylist).get(position);
		}else if (mylist.getClass()==String[].class) 
		{
			return ((String[])mylist)[position];
		}
		return "";
	}
	
	
	
}
