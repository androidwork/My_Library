package library.tool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Action£∫º∆À„listview∏ﬂ∂»
 * @author android_vip@sina.com
 * @create 2014-8-18
 */
public class Library_ListViewHeight_Utilety
{
    public  void setListViewHeightBasedOnChildren(ListView listView) { 
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
