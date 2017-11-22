package com.project.fanyuzeng.flowview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author fanyuzeng
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flow_layout);

        initData();
    }

    private void initData() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < mVals.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.tv, mFlowLayout, false);
            textView.setText(mVals[i]);
            mFlowLayout.addView(textView);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


}
