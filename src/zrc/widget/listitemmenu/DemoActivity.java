package zrc.widget.listitemmenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaric on 2015/1/29.
 */
@SuppressLint("NewApi")
public class DemoActivity extends Activity {
    List<User> arrays = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuListView listView = (MenuListView) findViewById(R.id.list);

        for (int i = 0; i <30; i++) {
        	arrays.add(new User("剑圣","不死才是关键！",getResources().getDrawable(R.mipmap.logo)));
		}

        listView.setAdapter(new ItemMenuAdapter() {

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
                        Toast.makeText(DemoActivity.this, "单击了 "+position, Toast.LENGTH_SHORT).show();
                    }
                });

                View info = convertView.findViewById(R.id.info);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DemoActivity.this, "单击了"+position, Toast.LENGTH_SHORT).show();
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
        });
    }
}
