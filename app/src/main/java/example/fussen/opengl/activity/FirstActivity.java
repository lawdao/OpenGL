package example.fussen.opengl.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.fussen.opengl.glsurface.GLRenderer;

public class FirstActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);

        //设置渲染器
        glSurfaceView.setRenderer(new GLRenderer());

        setContentView(glSurfaceView);


    }


    @Override
    protected void onResume() {
        super.onResume();

        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //防止activity不可见时还在绘制图形导致程序崩溃
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }
}
