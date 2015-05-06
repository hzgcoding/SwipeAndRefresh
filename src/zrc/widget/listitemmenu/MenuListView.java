package zrc.widget.listitemmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Zaric on 2015/1/29.
 */
public class MenuListView extends ListView implements ItemMenuView.ISlideFocusManager{
    private static final int INVALID = -1, NORMAL = 0, FLING = 1;
    private int mTouchMode = 0;
    private float mMotionX;
    private float mMotionY;
    private int mTouchSlop = 0;

    private ItemMenuView mCurrentView;

    public MenuListView(Context context) {
        super(context);
        init();
    }
    @Override
    public void setAdapter(ListAdapter adapter) {
    	super.setAdapter(adapter);
    	/**
    	 * 兼容 ScrollView 和 ListView 整合显示不全问题
    	 */
    	setListViewHeightBasedOnChildren(this);
    }
    /**
     * 重新测量ListView大小 
     * @param listView
     * @author hzg
     */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ItemMenuAdapter listAdapter = (ItemMenuAdapter) listView.getAdapter(); 
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public MenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("deprecation")
	private void init(){
        mTouchSlop =
                (int) (ViewConfiguration.getTouchSlop() * getResources().getDisplayMetrics().density);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            mMotionX = ev.getX();
            mMotionY = ev.getY();
            mTouchMode = NORMAL;
            if(mCurrentView!=null) {
                if (mMotionY < mCurrentView.getTop() || mMotionY > mCurrentView.getBottom()){
                    mCurrentView.hideMenu();
                    mCurrentView = null;
                }
            }
            return super.onInterceptTouchEvent(ev);
        }
        if(mTouchMode == INVALID){
            return false;
        } else if(mTouchMode == FLING){
            return super.onInterceptTouchEvent(ev);
        }
        if(ev.getAction()==MotionEvent.ACTION_MOVE){
            float diffX = Math.abs(ev.getX() - mMotionX);
            float diffY = Math.abs(ev.getY() - mMotionY);
            if(diffX > mTouchSlop || diffY > mTouchSlop){
                if(diffX > diffY){
                    mTouchMode = INVALID;
                    return false;
                } else {
                    mTouchMode = FLING;
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onFocus(ItemMenuView view) {
        if(mCurrentView!=null && mCurrentView!=view){
            mCurrentView.hideMenu();
        }
        mCurrentView = view;
    }
}
