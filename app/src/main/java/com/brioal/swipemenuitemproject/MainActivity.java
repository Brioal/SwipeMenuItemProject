package com.brioal.swipemenuitemproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.brioal.swipemenuitem.OnMenuItemClickListener;
import com.brioal.swipemenuitem.SwipeMenuItemView;

public class MainActivity extends AppCompatActivity {
    private SwipeMenuItemView mMenuItemView;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuItemView = (SwipeMenuItemView) findViewById(R.id.main_swipeMenuItem);
        mMenuItemView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onClicked(int position) {
                switch (position) {
                    case 0:
                        showToast("点击置顶");
                        break;
                    case 1:
                        showToast("点击删除");
                        break;
                }
            }
        });
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
