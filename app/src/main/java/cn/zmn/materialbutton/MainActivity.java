package cn.zmn.materialbutton;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

    private LinearLayout myLayout;
    private FlatButton flatButton;
    private FlatButton disFlatButton;
    private LayoutParams params;
    private RaisedButton raisedButton;
    private RaisedButton disRaisedButton;
    private FloatingActionButton floatButton;
    private FloatingActionButton floatButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLayout = new LinearLayout(this);
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setGravity(Gravity.CENTER);
        setContentView(myLayout);

        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,40);

        flatButton = new FlatButton(this);
        flatButton.setText("Flat Button");
        flatButton.setBackgroundColor(0xffFFBB33);
        flatButton.setFocusColor(0xffFF8800);
        flatButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("test", "click flatButton");

            }
        });
        myLayout.addView(flatButton,params);

        disFlatButton = new FlatButton(this);
        disFlatButton.setText("Disable Flat Button");
        disFlatButton.setClickable(false);
        myLayout.addView(disFlatButton,params);

        raisedButton = new RaisedButton(this);
        raisedButton.setBackgroundColor(0xffAA66CC);
        raisedButton.setFocusColor(0xff9933CC);
        raisedButton.setText("Raised Button");
        raisedButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("test", "click raisedButton");
            }
        });
        myLayout.addView(raisedButton,params);

        disRaisedButton = new RaisedButton(this);
        disRaisedButton.setText("Disable Raised Button");
        disRaisedButton.setBackgroundColor(0xffAA66CC);
        disRaisedButton.setClickable(false);
        myLayout.addView(disRaisedButton, params);

        floatButton = new FloatingActionButton(this);
        floatButton.setIcon(R.drawable.ic_action_new);
        floatButton.setBackgroundColor(0xff99CC00);
        floatButton.setFocusBgcolor(0xff669900);
        floatButton.setRotateRepeatReverse(315);
        floatButton.setRotateDuration(1000);
        floatButton.setOnTwoWayListener(new FloatingActionButton.OnTwoWayListener() {

            @Override
            public void onForward() {
                Log.v("test", "floatButton forward");
            }

            @Override
            public void onBack() {
                Log.v("test", "floatButton onBack");

            }
        });
        myLayout.addView(floatButton, params);

        floatButton2 = new FloatingActionButton(this);
        floatButton2.setIcon(R.drawable.ic_action_new);
        floatButton2.setBackgroundColor(0xff33B5E5);
        floatButton2.setFocusBgcolor(0xff0099CC);
        floatButton2.setRotate(360);
        floatButton2.setRotateDuration(1000);
        floatButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("test", "click floatButton2");
            }
        });
        myLayout.addView(floatButton2, params);

    }

}
