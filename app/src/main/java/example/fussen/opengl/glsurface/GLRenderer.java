package example.fussen.opengl.glsurface;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fussen on 16/8/22.
 */
public class GLRenderer implements GLSurfaceView.Renderer {


    //三角形三个点的坐标,(三维)该数据在堆内存中,OpenGL是直接在内存中操作数据的,需要将操作的数据需要保存到NIO里面的Buffer对象中
    //该操作可以放在构造函数中
    private float[] mTriangleArray = {
            0f, 1f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
    };

    //三角形各顶点颜色(三个顶点)
    private float[] mColor = new float[]{
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1
    };

    private final FloatBuffer mTriangleBuffer;
    private final FloatBuffer mColorBuffer;


    public GLRenderer() {

        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(mTriangleArray.length * 4);

        //以本机字节顺序来修改此缓冲区的字节顺序
        bb.order(ByteOrder.nativeOrder());

        mTriangleBuffer = bb.asFloatBuffer();

        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        mTriangleBuffer.put(mTriangleArray);

        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。

        mTriangleBuffer.position(0);

        //已将float[]转为了FloatBuffer，后面绘制三角形的时候，直接通过成员变量mTriangleBuffer即可。


        //颜色相关
        ByteBuffer bb2 = ByteBuffer.allocateDirect(mColor.length * 4);

        bb2.order(ByteOrder.nativeOrder());

        mColorBuffer = bb2.asFloatBuffer();

        mColorBuffer.put(mColor);

        mColorBuffer.position(0);
    }

    /**
     * 在SurfaceView创建时调用
     *
     * @param gl10
     * @param eglConfig
     */
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        //设置清屏的颜色(背景色)，参数分别对应RGBA(白色)
        gl10.glClearColor(1f, 1f, 1f, 1f);
    }

    /**
     * 在绘制图形时调用
     *
     * @param gl10
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        float ratio = (float) width / height;


        //设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        gl10.glViewport(0, 0, width, height);


        // 设置投影矩阵
        gl10.glMatrixMode(GL10.GL_PROJECTION);

        // 重置投影矩阵
        gl10.glLoadIdentity();

        // 设置视口的大小
        gl10.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();

    }

    /**
     * 在视图大小发生改变时调用
     *
     * @param gl10
     */
    @Override
    public void onDrawFrame(GL10 gl10) {

        //使用glClearColor函数所设置的颜色进行清屏。和深度缓存
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // 重置当前的模型观察矩阵
        gl10.glLoadIdentity();
        // 允许设置顶点
        //GL10.GL_VERTEX_ARRAY顶点数组
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // 允许设置颜色
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);

        //将三角形在z轴上移动
        gl10.glTranslatef(0f, 0.0f, -2.0f);

        // 设置三角形
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);

        // 设置三角形颜色

        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        // 绘制三角形
        gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // 取消颜色设置
        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);

        // 取消顶点设置
        gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //绘制结束
        gl10.glFinish();

    }

}
