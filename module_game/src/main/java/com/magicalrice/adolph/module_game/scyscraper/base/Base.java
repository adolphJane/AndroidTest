package com.magicalrice.adolph.module_game.scyscraper.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.*;

public class Base {

    private FloatBuffer mVertexBuffer;      //顶点坐标数据缓冲
    private FloatBuffer mTextureBuffer;     //顶点纹理数据缓冲
    private int vCount = 36;                //顶点坐标数
    private int drawableId;                 //纹理ID
    public float mAngleX,mAngleY,mAngleZ;   //绕x,y,z轴旋转角度

    public Base(int drawableId) {
        this.drawableId = drawableId;
        //初始化顶点
        initVertex();
        //初始化纹理
        initTexture();
    }

    public void initVertex() {
        float upX = BASE_LENGTH * UNIT_SIZE / 2;        //上表面最大x值
        float dwX = BASE_LENGTH * UNIT_SIZE * DOWN_UP_RATIO / 2;        //下表面最大x值，DOWN_UP_RATIO为下表面与上表面的宽度比
        float y = BASE_HEIGHT * UNIT_SIZE / 2;          //最大y值
        float upZ = BASE_WIDTH * UNIT_SIZE / 2;         //上表面最大z值
        float dwZ = BASE_WIDTH * UNIT_SIZE * DOWN_UP_RATIO / 2;         //下表面最大z值

        float[] vertices = {
                //上
                -upX,y,-upZ,
                -upX,y,upZ,
                upX,y,-upZ,

                -upX,y,upZ,
                upX,y,upZ,
                upX,y,-upZ,

                //下
                -dwX,-y,dwZ,
                -dwX,-y,-dwZ,
                dwX,-y,dwZ,

                -dwX,-y,-dwZ,
                dwX,-y,-dwZ,
                dwX,-y,dwZ,
                //左
                -upX,y,-upZ,
                -dwX,-y,-dwZ,
                -upX,y,upZ,

                -dwX,-y,-dwZ,
                -dwX,-y,dwZ,
                -upX,y,upZ,
                //右
                upX,y,upZ,
                dwX,-y,dwZ,
                upX,y,-upZ,

                dwX,-y,dwZ,
                dwX,-y,-dwZ,
                upX,y,-upZ,
                //前
                -upX,y,upZ,
                -dwX,-y,dwZ,
                upX,y,upZ,

                -dwX,-y,dwZ,
                dwX,-y,dwZ,
                upX,y,upZ,
                //后
                upX,y,-upZ,
                dwX,-y,-dwZ,
                -upZ,y,-upZ,

                dwX,-y,-dwZ,
                -dwX,-y,-dwZ,
                -upX,y,-upZ
        };

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);     //一个float型是4个byte
        bb.order(ByteOrder.nativeOrder());                                  //设置字节顺序
        mVertexBuffer = bb.asFloatBuffer();                                 //转换为float型缓冲
        mVertexBuffer.put(vertices);                                        //向缓冲中放入顶点坐标数组
        mVertexBuffer.position(0);                                          //设置缓冲区起始位置
    }

    public void initTexture() {
        float[] textures = new float[vCount * 2];
        for (int i = 0,temp = 0;i < 6;i++) {
            //左上
            textures[temp++] = 0;
            textures[temp++] = 0;

            textures[temp++] = 0;
            textures[temp++] = 1;

            textures[temp++] = 1;
            textures[temp++] = 0;

            //右下
            textures[temp++] = 0;
            textures[temp++] = 1;

            textures[temp++] = 1;
            textures[temp++] = 1;

            textures[temp++] = 1;
            textures[temp++] = 0;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(textures.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mTextureBuffer = bb.asFloatBuffer();
        mTextureBuffer.put(textures);
        mTextureBuffer.position(0);
    }

    public void drawSelf(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);           //启用顶点数组
        //为画笔指定顶点坐标数组
        gl.glVertexPointer(
                3,               //每个顶点的坐标数量为3
                GL10.GL_FLOAT,       //顶点坐标值得类型为 GL_FIXED
                0,              //连续顶点坐标数值之间的间隔
                mVertexBuffer         //顶点坐标数据
        );

        gl.glEnable(GL10.GL_TEXTURE_2D);                        //启用纹理
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);    //启用纹理数组
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,mTextureBuffer);     //为画笔指定纹理顶点坐标数据
        gl.glBindTexture(GL10.GL_TEXTURE_2D,drawableId);        //绑定纹理

        gl.glRotatef(mAngleX,1,0,0);                    //沿x轴旋转
        gl.glRotatef(mAngleY,0,1,0);                    //沿y轴旋转
        gl.glRotatef(mAngleZ,0,0,1);                    //沿z轴旋转

        gl.glDrawArrays(GL10.GL_TRIANGLES,0,vCount);        //顶点绘制
    }
}
