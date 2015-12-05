package com.yanis.yc_ui_fragment_tabhost.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.yanis.yc_ui_fragment_tabhost.R;
import com.yanis.yc_ui_fragment_tabhost.views.GestureLockView.Mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 整体包含n*n个GestureLockView,每个GestureLockView间间隔mMarginBetweenLockView，
 * 最外层的GestureLockView与容器存在mMarginBetweenLockView的外边距
 * 
 * 关于GestureLockView的边长（n*n）： n * mGestureLockViewWidth + ( n + 1 ) *
 * mMarginBetweenLockView = mWidth ; 得：mGestureLockViewWidth = 4 * mWidth / ( 5
 * * mCount + 1 ) 注：mMarginBetweenLockView = mGestureLockViewWidth * 0.25 ;
 * 
 * @author zhy
 * 
 */
public class GestureLockViewGroup extends RelativeLayout
{

	private static final String TAG = "GestureLockViewGroup";
	/**
	 * 保存所有的GestureLockView
	 */
	private GestureLockView[] mGestureLockViews;
	/**
	 * 每个边上的GestureLockView的个数
	 */
	private int mCount = 4;
	/**
	 * 存储答案
	 */
	private int[] mAnswer = { 0, 1, 2, 5, 8 };
	/**
	 * 保存用户选中的GestureLockView的id
	 */
	private List<Integer> mChoose = new ArrayList<>();

	private Paint mPaint;
	/**
	 * 每个GestureLockView中间的间距 设置为：mGestureLockViewWidth * 25%
	 */
	private int mMarginBetweenLockView = 30;
	/**
	 * GestureLockView的边长 4 * mWidth / ( 6 * mCount + 1 )
	 */
	private int mGestureLockViewWidth;

    /**
     * GestureLockView无手指触摸的状态下外圆边框的颜色
     */
    private int mColorNoFingerOuterBorder = 0xFFFFFF;
	/**
	 * GestureLockView无手指触摸的状态下外圆的颜色
	 */
	private int mColorNoFingerOuterFill;
    /**
     * GestureLockView无手指触摸的状态下内圆边框的颜色
     */
    private int mColorNoFingerInnerBorder;
    /**
     * GestureLockView无手指触摸的状态下内圆的颜色
     */
    private int mColorNoFingerInnerFill;

    /**
     * GestureLockView手指触摸的状态下外圆边框的颜色
     */
    private int mColorFingerOnOuterBorder;
    /**
     * GestureLockView手指触摸的状态下外圆的颜色
     */
    private int mColorFingerOnOuterFill = 0xFFE0DBDB;
    /**
     * GestureLockView手指触摸的状态下内圆边框的颜色
     */
    private int mColorFingerOnInnerBorder;
    /**
     * GestureLockView手指触摸的状态下内圆的颜色
     */
    private int mColorFingerOnInnerFill;

    /**
     * GestureLockView手指抬起的状态下外圆边框的颜色
     */
    private int mColorFingerUpOuterBorder;
    /**
     * GestureLockView手指抬起的状态下外圆的颜色
     */
    private int mColorFingerUpOuterFill;
    /**
     * GestureLockView手指抬起的状态下内圆边框的颜色
     */
    private int mColorFingerUpInnerBorder;
    /**
     * GestureLockView手指抬起的状态下内圆的颜色
     */
    private int mColorFingerUpInnerFill;

    /**
     * GestureLockView手指抬起的状态下内圆的颜色
     */
    private int mLineColor;
    /**
     * GestureLockView手指抬起的状态下内圆的颜色
     */
    private int mLineWidth;
	/**
	 * 宽度
	 */
	private int mWidth;
	/**
	 * 高度
	 */
	private int mHeight;

	private Path mPath;
	/**
	 * 指引线的开始位置x
	 */
	private int mLastPathX;
	/**
	 * 指引线的开始位置y
	 */
	private int mLastPathY;
	/**
	 * 指引下的结束位置
	 */
	private Point mTmpTarget = new Point();

	/**
	 * 最大尝试次数
	 */
	private int mTryTimes = 4;
	/**
	 * 回调接口
	 */
	private OnGestureLockViewListener mOnGestureLockViewListener;

    public GestureLockViewGroup(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public GestureLockViewGroup(Context context, AttributeSet attrs,
			int defStyle)
	{
		super(context, attrs, defStyle);
		/**
		 * 获得所有自定义的参数的值
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.GestureLockViewGroup, defStyle, 0);
		int n = a.getIndexCount();

		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle_border:
                    mColorNoFingerOuterBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle_fill:
                    mColorNoFingerOuterFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle_border:
                    mColorNoFingerInnerBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle_fill:
                    mColorNoFingerInnerFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on_outer_circle_border:
                    mColorFingerOnOuterBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on_outer_circle_fill:
                    mColorFingerOnOuterFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on_inner_circle_border:
                    mColorFingerOnInnerBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on_inner_circle_fill:
                    mColorFingerOnInnerFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_outer_circle_border:
                    mColorFingerUpOuterBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_outer_circle_fill:
                    mColorFingerUpOuterFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_inner_circle_border:
                    mColorFingerUpInnerBorder = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_inner_circle_fill:
                    mColorFingerUpInnerFill = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_line_color:
                    mLineColor = a.getColor(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_line_width:
                    mLineWidth = a.getInt(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_count:
                    mCount = a.getInt(attr, 3);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    mTryTimes = a.getInt(attr, 5);
                default:
                    break;
			}
		}

		a.recycle();

		// 初始化画笔
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(20);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		// mPaint.setColor(Color.parseColor("#aaffffff"));
		mPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		// Log.e(TAG, mWidth + "");
		// Log.e(TAG, mHeight + "");

		mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;

		// setMeasuredDimension(mWidth, mHeight);

		// 初始化mGestureLockViews
		if (mGestureLockViews == null)
		{
			mGestureLockViews = new GestureLockView[mCount * mCount];
			// 计算每个GestureLockView的宽度
			mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / (6 * mCount + 1));
			//计算每个GestureLockView的间距
            mMarginBetweenLockView = (int)((mWidth - mCount * mGestureLockViewWidth) / (mCount - 1));
			// 设置画笔的宽度为GestureLockView的内圆直径稍微小点（不喜欢的话，随便设）
			mPaint.setStrokeWidth(mLineWidth == 0 ? mGestureLockViewWidth * 0.2f : mLineWidth);

			for (int i = 0; i < mGestureLockViews.length; i++)
			{
				//初始化每个GestureLockView
				GestureLockView gestureLockView = new GestureLockView(getContext());
                gestureLockView.setColorNoFingerOuterBorder(mColorNoFingerOuterBorder);
                gestureLockView.setColorNoFingerOuterFill(mColorNoFingerOuterFill);
                gestureLockView.setColorNoFingerInnerBorder(mColorNoFingerInnerBorder);
                gestureLockView.setColorNoFingerInnerFill(mColorNoFingerInnerFill);
                gestureLockView.setColorFingerOnOuterBorder(mColorFingerOnOuterBorder);
                gestureLockView.setColorFingerOnOuterFill(mColorFingerOnOuterFill);
                gestureLockView.setColorFingerOnInnerBorder(mColorFingerOnInnerBorder);
                gestureLockView.setColorFingerOnInnerFill(mColorFingerOnInnerFill);
                gestureLockView.setColorFingerUpOuterBorder(mColorFingerUpOuterBorder);
                gestureLockView.setColorFingerUpOuterFill(mColorFingerUpOuterFill);
                gestureLockView.setColorFingerUpInnerBorder(mColorFingerUpInnerBorder);
                gestureLockView.setColorFingerUpInnerFill(mColorFingerUpInnerFill);
				mGestureLockViews[i] = gestureLockView;
				mGestureLockViews[i].setId(i + 1);
				//设置参数，主要是定位GestureLockView间的位置
				RelativeLayout.LayoutParams lockerParams = new RelativeLayout.LayoutParams(mGestureLockViewWidth, mGestureLockViewWidth);

				// 不是每行的第一个，则设置位置为前一个的右边
				if (i % mCount != 0){
					lockerParams.addRule(RelativeLayout.RIGHT_OF, mGestureLockViews[i - 1].getId());
				}
				// 从第二行开始，设置为上一行同一位置View的下面
				if (i > mCount - 1){
					lockerParams.addRule(RelativeLayout.BELOW, mGestureLockViews[i - mCount].getId());
				}
                int bottomMargin = mMarginBetweenLockView;
                int rightMargin = mMarginBetweenLockView;
				//最后一列不留右间距
                if ( (i+1)%mCount == 0){
                    rightMargin = 0;
                }
                //最后一行不留下间距
                if( i >= mCount*(mCount-1) ){
                    bottomMargin = 0;
                }
                lockerParams.setMargins(0, 0, rightMargin, bottomMargin);
				mGestureLockViews[i].setMode(Mode.STATUS_NO_FINGER);
				addView(mGestureLockViews[i], lockerParams);
			}
			Log.e(TAG, "mWidth = " + mWidth + " ,  mGestureViewWidth = " + mGestureLockViewWidth + " , mMarginBetweenLockView = " + mMarginBetweenLockView);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action)
		{
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "ACTION_DOWN");
			// 重置
			reset();
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "ACTION_MOVE");
			mPaint.setColor(mLineColor != 0 ? mLineColor : mColorFingerOnOuterBorder);
			mPaint.setAlpha(80);
			GestureLockView child = getChildIdByPos(x, y);
			if (child != null)
			{
				int cId = child.getId();
				if (!mChoose.contains(cId))
				{
					mChoose.add(cId);
					child.setMode(Mode.STATUS_FINGER_ON);
					if (mOnGestureLockViewListener != null)
						mOnGestureLockViewListener.onBlockSelected(cId);
					// 设置指引线的起点
					mLastPathX = child.getLeft() / 2 + child.getRight() / 2;
					mLastPathY = child.getTop() / 2 + child.getBottom() / 2;

					if (mChoose.size() == 1)// 当前添加为第一个
					{
						mPath.moveTo(mLastPathX, mLastPathY);
					} else
					// 非第一个，将两者使用线连上
					{
						mPath.lineTo(mLastPathX, mLastPathY);
					}

				}
			}
			// 指引线的终点
			mTmpTarget.x = x;
			mTmpTarget.y = y;
			break;
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "ACTION_UP");
            mPaint.setColor(mLineColor != 0 ? mLineColor : mColorFingerOnOuterBorder);
			mPaint.setAlpha(80);
			this.mTryTimes--;
			Log.e(TAG, "mChoose = " + mChoose);

			// 回调是否成功
			if (mOnGestureLockViewListener != null && mChoose.size() > 0)
			{
				mOnGestureLockViewListener.onGestureEvent(checkAnswer());
				if (this.mTryTimes == 0)
				{
					mOnGestureLockViewListener.onUnmatchedExceedBoundary();
				}
			}

			Log.e(TAG, "mUnMatchExceedBoundary = " + mTryTimes);
			Log.e(TAG, "mChoose = " + mChoose);
			// 将终点设置位置为起点，即取消指引线
			mTmpTarget.x = mLastPathX;
			mTmpTarget.y = mLastPathY;

			// 改变子元素的状态为UP
			changeItemMode();
			
			// 计算每个元素中箭头需要旋转的角度
			for (int i = 0; i + 1 < mChoose.size(); i++)
			{
				int childId = mChoose.get(i);
				int nextChildId = mChoose.get(i + 1);

				GestureLockView startChild = (GestureLockView) findViewById(childId);
				GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);

				int dx = nextChild.getLeft() - startChild.getLeft();
				int dy = nextChild.getTop() - startChild.getTop();
				// 计算角度
				int angle = (int) Math.toDegrees(Math.atan2(dy, dx)) + 90;
				startChild.setArrowDegree(angle);
			}
			break;

		}
		invalidate();
		return true;
	}

	private void changeItemMode()
	{
		for (GestureLockView gestureLockView : mGestureLockViews)
		{
			if (mChoose.contains(gestureLockView.getId()))
			{
				gestureLockView.setMode(Mode.STATUS_FINGER_UP);
			}
		}
	}

	/**
	 * 
	 * 做一些必要的重置
	 */
	private void reset()
	{
		Log.e(TAG, "reset");
		mChoose.clear();
		mPath.reset();
		for (GestureLockView gestureLockView : mGestureLockViews)
		{
			gestureLockView.setMode(Mode.STATUS_NO_FINGER);
			gestureLockView.setArrowDegree(-1);
		}
	}
	/**
	 * 检查用户绘制的手势是否正确
	 * @return
	 */
	private boolean checkAnswer()
	{
		Log.e(TAG, "checkAnswer");
		if (mAnswer.length != mChoose.size())
			return false;

		for (int i = 0; i < mAnswer.length; i++)
		{
			if (mAnswer[i] != mChoose.get(i))
				return false;
		}

		return true;
	}
	
	/**
	 * 检查当前左边是否在child中
	 * @param child
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkPositionInChild(View child, int x, int y)
	{

		//设置了内边距，即x,y必须落入下GestureLockView的内部中间的小区域中，可以通过调整padding使得x,y落入范围不变大，或者不设置padding
		int padding = (int) (mGestureLockViewWidth * 0.15);

		if (x >= child.getLeft() + padding && x <= child.getRight() - padding
				&& y >= child.getTop() + padding
				&& y <= child.getBottom() - padding)
		{
			return true;
		}
		return false;
	}

	/**
	 * 通过x,y获得落入的GestureLockView
	 * @param x
	 * @param y
	 * @return
	 */
	private GestureLockView getChildIdByPos(int x, int y)
	{
		for (GestureLockView gestureLockView : mGestureLockViews)
		{
			if (checkPositionInChild(gestureLockView, x, y))
			{
				return gestureLockView;
			}
		}

		return null;

	}

	/**
	 * 设置回调接口
	 * 
	 * @param listener
	 */
	public void setOnGestureLockViewListener(OnGestureLockViewListener listener)
	{
		this.mOnGestureLockViewListener = listener;
	}

	/**
	 * 对外公布设置答案的方法
	 * 
	 * @param answer
	 */
	public void setAnswer(int[] answer)
	{
		this.mAnswer = answer;
	}

	/**
	 * 设置最大实验次数
	 * 
	 * @param boundary
	 */
	public void setUnMatchExceedBoundary(int boundary)
	{
		this.mTryTimes = boundary;
	}

	@Override
	public void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		//绘制GestureLockView间的连线
		if (mPath != null)
		{
			canvas.drawPath(mPath, mPaint);
		}
		//绘制指引线
		if (mChoose.size() > 0)
		{
			if (mLastPathX != 0 && mLastPathY != 0)
				canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
						mTmpTarget.y, mPaint);
		}

	}

	public interface OnGestureLockViewListener
	{
		/**
		 * 单独选中元素的Id
		 * 
		 * @param cId
		 */
		public void onBlockSelected(int cId);

		/**
		 * 是否匹配
		 * 
		 * @param matched
		 */
		public void onGestureEvent(boolean matched);

		/**
		 * 超过尝试次数
		 */
		public void onUnmatchedExceedBoundary();
	}
}
