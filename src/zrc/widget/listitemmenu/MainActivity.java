package zrc.widget.listitemmenu;

import java.util.ArrayList;
import java.util.List;

import zrc.widget.listitemmenu.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SwipeRefreshLayout swipeRefreshLayout = null;
	List<User> arrays = new ArrayList<User>();
	private MenuListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe);
		
		listView = (MenuListView) findViewById(R.id.list);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				System.out.println("onScrollStateChanged");
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
		
		swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            
            @Override
            public void onRefresh() {
            	swipeRefreshLayout.setRefreshing(true);
            	addContent(5);
            	
            	//延迟3秒，动画停止
                new Handler().postDelayed(new Runnable() {
                    
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
            }
        });
        
        addContent(5);
        listView.setAdapter(adapter);
	}


	private ItemMenuAdapter adapter = new ItemMenuAdapter() {

        @Override
        public int getCount() {
            return arrays.size();
        }

        @Override
        public void onDeleteItem(int pos) {
            arrays.remove(pos);
            notifyDataSetChanged();
        }
        
        @Override
        public void notifyDataSetChanged() {
        	super.notifyDataSetChanged();
        	/**
        	 * 如果是将ListView放在ScrollView中，则下这个方法重载比较好,否则会ListView内容显示出问题 
        	 */
        	MenuListView.setListViewHeightBasedOnChildren(listView);
        };
        
        @Override
        public ItemMenuView getItemView(final int position, ItemMenuView convertView,
                                         ViewGroup parent) {
            if(convertView==null){
                convertView = (ItemMenuView) getLayoutInflater().inflate(R.layout.item_view, null);
                
            }
            View delete = convertView.findViewById(R.id.delete);
            
            LinearLayout item = (LinearLayout) convertView.findViewById(R.id.content);
            item.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int)getResources().getDimension(R.dimen.item_height)));
            TextView name = (TextView) item.findViewById(R.id.name);
            name.setText(arrays.get(position).getName());
            TextView sign = (TextView) item.findViewById(R.id.sign);
            sign.setText(arrays.get(position).getSign());
            
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "单击了 "+position, Toast.LENGTH_SHORT).show();
                }
            });

            View info = convertView.findViewById(R.id.info);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "单击了"+position, Toast.LENGTH_SHORT).show();
                }
            });

            final ItemMenuView finalConvertView = convertView;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalConvertView.hideMenu();
                    deleteItem(position, finalConvertView);
                }
            });
            return convertView;
        }
    };
	private void addContent(int n) {
		for (int i = 0; i <n; i++) {
        	arrays.add(new User("剑圣","不死才是关键！",getResources().getDrawable(R.mipmap.logo)));
		}
	}
}
