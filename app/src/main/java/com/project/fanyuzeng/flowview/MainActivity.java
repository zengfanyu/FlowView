package com.project.fanyuzeng.flowview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * @author fanyuzeng
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FlowLayout mFlowLayout;
    private int mFlowLayoutHeight;
    private int mFlowLayoutWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flow_layout);


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
