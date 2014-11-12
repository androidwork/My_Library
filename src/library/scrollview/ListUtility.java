package library.scrollview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListUtility {
	
	public ListUtility() {
		// TODO Auto-generated constructor stub
	}
	public  void setListViewHeightBasedOnChildren(ListView listView) { 
//      MyListAdapter myListAdapter = (MyListAdapter) listView.getAdapter();  
//      if (myListAdapter == null) { 
//          // pre-condition 
//          return; 
//      } 

      int totalHeight = 0; 
      for (int i = 0; i < listView.getCount(); i++) { 
          View listItem = listView.getAdapter().getView(i, null, listView); 
          listItem.measure(0, 0); 
          totalHeight += listItem.getMeasuredHeight(); 
      } 

      ViewGroup.LayoutParams params = listView.getLayoutParams(); 
      params.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
      listView.setLayoutParams(params); 
  } 
}
