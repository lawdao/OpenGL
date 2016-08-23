package example.fussen.opengl.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import example.fussen.opengl.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button firstDemo;
    private boolean supportsEs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSupported();

        firstDemo = (Button) findViewById(R.id.btn_first);
        firstDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (supportsEs2) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.btn_first:
                    intent.setClass(MainActivity.this, FirstActivity.class);
                    break;
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 检测手机或者模拟器是否支持OpenGL2.0
     */
    private void checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        supportsEs2 = supportsEs2 || isEmulator;
    }
}
