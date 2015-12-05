package com.yanis.yc_ui_fragment_tabhost.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

public class GestureLockView extends View
{
	private static final String TAG = "GestureLockView";
	/**
	 * GestureLockView的三种状态
	 */
	enum Mode
	{
		STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
	}

	/**
	 * GestureLockView的当前状态
	 */
	private Mode mCurrentStatus = Mode.STATUS_NO_FINGER;
	
	/**
	 * 宽度
	 */
	private int mWidth;
	/**
	 * 高度
	 */
	private int mHeight;
	/**
	 * 外圆半径
	 */
	private int mRadius;
	/**
	 * 画笔的宽度
	 */
	private int mStrokeWidth = 6;

	/**
	 * 圆心坐标
	 */
	private int mCenterX;
	private int mCenterY;
	private Paint mPaint;

	/**
	 * 箭头（小三角最长边的一半长度 = mArrawRate * mWidth / 2 ）
	 */
	private float mArrowRate = 0.333f;
	private int mArrowDegree = -1;
	private Path mArrowPath;
	/**
	 * 内圆的半径 = mInnerCircleRadiusRate * mRadius
	 */
	private float mInnerCircleRadiusRate = 0.3F;
    /**
	 * 四个颜色，可由用户自定义，初始化时由GestureLockViewGroup传入
	 */
    private int mColorNoFingerOuterBorder;
    private int mColorNoFingerOuterFill;
	private int mColorNoFingerInnerBorder;
    private int mColorNoFingerInnerFill;

	private int mColorFingerOnOuterBorder;
    private int mColorFingerOnOuterFill;
    private int mColorFingerOnInnerBorder;
    private int mColorFingerOnInnerFill;

    private int mColorFingerUpOuterBorder;
    private int mColorFingerUpOuterFill;
    private int mColorFingerUpInnerBorder;
    private int mColorFingerUpInnerFill;

    public GestureLockView(Context context)
    {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPath = new Path();
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		// 取长和宽中的小值
		mWidth = mWidth < mHeight ? mWidth : mHeight;
		mRadius = mCenterX = mCenterY = mWidth / 2;
		mRadius -= mStrokeWidth / 2;

		// 绘制三角形，初始时是个默认箭头朝上的一个等腰三角形，用户绘制结束后，根据由两个GestureLockView决定需要旋转多少度
		float mArrowLength = mWidth / 2 * mArrowRate;
		mArrowPath.moveTo(mWidth / 2, mStrokeWidth + 2);
		mArrowPath.lineTo(mWidth / 2 - mArrowLength, mStrokeWidth + 2 + mArrowLength);
		mArrowPath.lineTo(mWidth / 2 + mArrowLength, mStrokeWidth + 2 + mArrowLength);
		mArrowPath.close();
		mArrowPath.setFillType(Path.FillType.WINDING);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{

		switch (mCurrentStatus)
		{
		case STATUS_FINGER_ON:
            onFingerMove(canvas);
			break;
		case STATUS_FINGER_UP:
			// 绘制外圆
//			mPaint.setColor(mColorFingerUp);
//			mPaint.setStyle(Style.STROKE);
//			mPaint.setStrokeWidth(mStrokeWidth);
//			canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
//			// 绘制内圆
//			mPaint.setStyle(Style.FILL);
//			canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);

//			drawArrow(canvas);
            onFingerMove(canvas);
			break;

		case STATUS_NO_FINGER:

			// 绘制外圆边
			mPaint.setStyle(Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
			mPaint.setColor(mColorNoFingerOuterBorder);
			canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
//			// 绘制内圆
//            if(mColorNoFingerInnerEnabled) {
//                mPaint.setColor(mColorNoFingerInner);
//                canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);
//            }
			break;

		}

	}

    private void onFingerMove(Canvas canvas){
        // 绘制外圆边
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(mColorFingerOnOuterBorder);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        //填充外圆
        mPaint.setColor(mColorFingerOnOuterFill);
        canvas.drawCircle(mCenterX, mCenterY, mRadius - mStrokeWidth, mPaint);
        // 绘制内圆边
        mPaint.setColor(mColorFingerOnInnerBorder);
        canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);
        //填充外圆
        mPaint.setColor(mColorFingerOnInnerFill);
        canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate - mStrokeWidth, mPaint);
    }

	/**
	 * 绘制箭头
	 * @param canvas
	 */
	private void drawArrow(Canvas canvas)
	{
		if (mArrowDegree != -1)
		{
			mPaint.setStyle(Paint.Style.FILL);

			canvas.save();
			canvas.rotate(mArrowDegree, mCenterX, mCenterY);
			canvas.drawPath(mArrowPath, mPaint);

			canvas.restore();
		}

	}

	/**
	 * 设置当前模式并重绘界面
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode)
	{
		this.mCurrentStatus = mode;
		invalidate();
	}

	public void setArrowDegree(int degree)
	{
		this.mArrowDegree = degree;
	}

	public int getArrowDegree()
	{
		return this.mArrowDegree;
	}

    public void setColorNoFingerOuterBorder(int colorNoFingerOuterBorder) {
        this.mColorNoFingerOuterBorder = colorNoFingerOuterBorder;
    }

    public void setColorNoFingerOuterFill(int colorNoFingerOuterFill) {
        this.mColorNoFingerOuterFill = colorNoFingerOuterFill;
    }

    public void setColorNoFingerInnerBorder(int colorNoFingerInnerBorder) {
        this.mColorNoFingerInnerBorder = colorNoFingerInnerBorder;
    }

    public void setColorNoFingerInnerFill(int colorNoFingerInnerFill) {
        this.mColorNoFingerInnerFill = colorNoFingerInnerFill;
    }

    public void setColorFingerOnOuterBorder(int colorFingerOnOuterBorder) {
        this.mColorFingerOnOuterBorder = colorFingerOnOuterBorder;
    }

    public void setColorFingerOnOuterFill(int colorFingerOnOuterFill) {
        this.mColorFingerOnOuterFill = colorFingerOnOuterFill;
    }

    public void setColorFingerOnInnerBorder(int colorFingerOnInnerBorder) {
        this.mColorFingerOnInnerBorder = colorFingerOnInnerBorder;
    }

    public void setColorFingerOnInnerFill(int colorFingerOnInnerFill) {
        this.mColorFingerOnInnerFill = colorFingerOnInnerFill;
    }

    public void setColorFingerUpOuterBorder(int colorFingerUpOuterBorder) {
        this.mColorFingerUpOuterBorder = colorFingerUpOuterBorder;
    }

    public void setColorFingerUpOuterFill(int colorFingerUpOuterFill) {
        this.mColorFingerUpOuterFill = colorFingerUpOuterFill;
    }

    public void setColorFingerUpInnerBorder(int colorFingerUpInnerBorder) {
        this.mColorFingerUpInnerBorder = colorFingerUpInnerBorder;
    }

    public void setColorFingerUpInnerFill(int colorFingerUpInnerFill) {
        this.mColorFingerUpInnerFill = colorFingerUpInnerFill;
    }
}
