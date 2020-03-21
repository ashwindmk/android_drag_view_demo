package com.ashwin.android.dragviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RelativeActivity extends AppCompatActivity {
    private ViewGroup rootLayout;
    private ImageView imageView;

    private int rootWidth;
    private int rootHeight;

    private int xDelta;
    private int yDelta;

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    Log.w("drag-demo", "action_down");
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    xDelta = x - layoutParams.leftMargin;
                    yDelta = y - layoutParams.topMargin;
                    Log.w("drag-demo", "raw-x: " + x + ", raw-y: " + y + ", x: " + ((int) event.getX()) + ", y: " + ((int) event.getY()));
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.w("drag-demo", "action_pointer_down");
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    Log.w("drag-demo", "action_pointer_up");
                    break;

                case MotionEvent.ACTION_MOVE:
                    Log.w("drag-demo", "action_move");
                    RelativeLayout.LayoutParams newLayoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    newLayoutParams.leftMargin = Math.max(0, Math.min(rootWidth, x - xDelta));
                    newLayoutParams.topMargin = Math.max(0, Math.min(rootHeight, y - yDelta));
                    //newLayoutParams.rightMargin = 50;
                    //newLayoutParams.bottomMargin = 50;
                    v.setLayoutParams(newLayoutParams);
                    break;

                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
            }

            rootLayout.invalidate();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);

        rootLayout = (ViewGroup) findViewById(R.id.root_layout);
        imageView = (ImageView) findViewById(R.id.drag_imageview);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

        rootHeight = displayMetrics.heightPixels - 500;
        rootWidth = displayMetrics.widthPixels;
        Log.w("drag-demo", "root-width: " + rootWidth + ", root-height: " + rootHeight);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        layoutParams.leftMargin = 200;
        layoutParams.topMargin = 200;
        imageView.setLayoutParams(layoutParams);
        imageView.setOnTouchListener(new ChoiceTouchListener());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("drag-demo", "on-click");
            }
        });
    }
}
