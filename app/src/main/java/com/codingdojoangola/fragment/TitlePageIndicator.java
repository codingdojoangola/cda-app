package com.codingdojoangola.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


import com.codingdojoangola.R;

import java.util.ArrayList;

/**
 * A TitlePageIndicator is a PageIndicator which displays the title of left view
 * (if exist), the title of the current select view (centered) and the title of
 * the right view (if exist). When the user scrolls the ViewPager then titles are
 * also scrolled.
 */
public class TitlePageIndicator extends View implements PageIndicator {
    /**
     * Percentage indicating what percentage of the screen width away from
     * center should the underline be fully faded. A value of 0.25 means that
     * halfway between the center of the screen and an edge.
     */
    private static final float SELECTION_FADE_PERCENTAGE = 0.25f;
    /**
     * Percentage indicating what percentage of the screen width away from
     * center should the selected text bold turn off. A value of 0.05 means
     * that 10% between the center and an edge.
     */
    private static final float BOLD_FADE_PERCENTAGE = 0.05f;
    /**
     * Title text used when no title is provided by the adapter.
     */
    private static final String EMPTY_TITLE = "";
    
    /**
     * Interface for a callback when the center item has been clicked.
     */
    private float mPageOffset;
    private int mScrollState;

    private Path mPath = new Path();
    
    private final Rect mBounds = new Rect();
    private final Paint mPaintText = new Paint();								//
    
    private final Paint mPaintFooterLine = new Paint();
    private final Paint mPaintFooterIndicator = new Paint();
    
    private boolean mBoldText;													//---- Boolean View
    
    private IndicatorStyle mFooterIndicatorStyle;								//---- Integer View
    private LinePosition mLinePosition;											//---- Integer View

    private int mColorText;														//---- Color View
    private int mColorSelected;													//---- Color View
    //Load defaults from resources (VIEW)
    final Resources res = getResources();

    /** Left and right side padding for not active view titles. */				
    private float mTitlePadding;												//---- Dimension View
    private float mClipPadding;													//---- Dimension View
    private float mTopPadding;													//---- Dimension View
    
    private float mFooterLineHeight;											//---- Dimension View
    private float mFooterIndicatorHeight;										//---- Dimension View
    private float mFooterIndicatorUnderlinePadding;								//---- Dimension View
    private float mFooterPadding;												//---- Dimension View

    private static final int INVALID_POINTER = -1;

    private float mLastMotionX = -1;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mIsDragging;
    
    private int mTouchSlop;														//---- Dimension of the Display
    private ViewPager mViewPager;												//Components of a Screen 
    private ViewPager.OnPageChangeListener mListener;							//Interface Listen 
    private OnCenterItemClickListener mCenterItemClickListener;					//Interface Position of the current center item

    private int mCurrentPage = -1;												//----Position ViewPage
    
   //************************************************ Methods ENUM ***********************************************************
    public enum IndicatorStyle {
        None(0), Triangle(1), Underline(2);
        public final int value;
        
        //------------- Construct
        private IndicatorStyle(int value) {
            this.value = value;
        }
        //------------- Method 
        public static IndicatorStyle fromValue(int value) {
            for (IndicatorStyle style : IndicatorStyle.values()) {
                if (style.value == value) {
                    return style;
                }
            }
            return null;
        }
    }
    //------------------------------
    public enum LinePosition {
        Bottom(0), Top(1);
        public final int value;
        
        //------------- Construct
        private LinePosition(int value) {
            this.value = value;
        }
      //--------------- Method 
        public static LinePosition fromValue(int value) {
            for (LinePosition position : LinePosition.values()) {
                if (position.value == value) {
                    return position;
                }
            }
            return null;
        }
    }
    //************************************************* Construct TitlePageIndicator**************************************************
    public TitlePageIndicator(Context context) {
        this(context, null);
    }
  
    public TitlePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiTitlePageIndicatorStyle);
    }

    @SuppressWarnings("deprecation")
	public TitlePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        if (isInEditMode()) return;
        
        //----- Color VIEW
        final int defaultFooterColor = res.getColor(R.color.default_title_indicator_footer_color);
        final int defaultSelectedColor = res.getColor(R.color.default_title_indicator_selected_color);
        final int defaultTextColor = res.getColor(R.color.default_title_indicator_text_color);
        
        //---- Dimension VIEW
        final float defaultFooterLineHeight = res.getDimension(R.dimen.default_title_indicator_footer_line_height);
        final float defaultFooterIndicatorHeight = res.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
        final float defaultFooterPadding = res.getDimension(R.dimen.default_title_indicator_footer_padding);
        final float defaultTextSize = res.getDimension(R.dimen.main_table_titlePageIndicator_textSize);
        final float defaultTitlePadding = res.getDimension(R.dimen.default_title_indicator_title_padding);
        final float defaultClipPadding = res.getDimension(R.dimen.default_title_indicator_clip_padding);
        final float defaultTopPadding = res.getDimension(R.dimen.default_title_indicator_top_padding);
        final float defaultFooterIndicatorUnderlinePadding = res.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
        
        //----- Integer VIEW
        final int defaultFooterIndicatorStyle = res.getInteger(R.integer.default_title_indicator_footer_indicator_style);
        final int defaultLinePosition = res.getInteger(R.integer.default_title_indicator_line_position);
       
        //----- Boolean VIEW
        final boolean defaultSelectedBold = res.getBoolean(R.bool.default_title_indicator_selected_bold);

        //Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitlePageIndicator, defStyle, 0);

        //Retrieve the colors to be used for this view and apply them.
        
        //----- Dimension
        mFooterLineHeight = a.getDimension(R.styleable.TitlePageIndicator_footerLineHeight, defaultFooterLineHeight);
        mClipPadding = a.getDimension(R.styleable.TitlePageIndicator_clipPadding, defaultClipPadding);      
        mTopPadding = a.getDimension(R.styleable.TitlePageIndicator_topPadding, defaultTopPadding);
        mTitlePadding = a.getDimension(R.styleable.TitlePageIndicator_titlePadding, defaultTitlePadding);
        mFooterIndicatorHeight = a.getDimension(R.styleable.TitlePageIndicator_footerIndicatorHeight, defaultFooterIndicatorHeight);
        mFooterIndicatorUnderlinePadding = a.getDimension(R.styleable.TitlePageIndicator_footerIndicatorUnderlinePadding, defaultFooterIndicatorUnderlinePadding);
        mFooterPadding = a.getDimension(R.styleable.TitlePageIndicator_footerPadding, defaultFooterPadding);
        
        final float textSize = a.getDimension(R.styleable.TitlePageIndicator_android_textSize, defaultTextSize);
        
        //----- Color
        mColorSelected = a.getColor(R.styleable.TitlePageIndicator_selectedColor, defaultSelectedColor);
        mColorText = a.getColor(R.styleable.TitlePageIndicator_android_textColor, defaultTextColor);
        
        final int footerColor = a.getColor(R.styleable.TitlePageIndicator_footerColor, defaultFooterColor);
        
        //----- Integer
        mFooterIndicatorStyle = IndicatorStyle.fromValue(a.getInteger(R.styleable.TitlePageIndicator_footerIndicatorStyle, defaultFooterIndicatorStyle));
        mLinePosition = LinePosition.fromValue(a.getInteger(R.styleable.TitlePageIndicator_linePosition, defaultLinePosition));
        
        //----- Boolean
        mBoldText = a.getBoolean(R.styleable.TitlePageIndicator_selectedBold, defaultSelectedBold);
        
        //-----------
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);
        
        mPaintFooterLine.setStrokeWidth(mFooterLineHeight);
        mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
       
        mPaintFooterLine.setColor(footerColor);
        mPaintFooterIndicator.setColor(footerColor);
        mPaintFooterIndicator.setStyle(Paint.Style.FILL_AND_STROKE);


        Drawable background = a.getDrawable(R.styleable.TitlePageIndicator_android_background);
        
        if (background != null) {
          setBackgroundDrawable(background);
        }

        a.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    //******************************* Methods Override **************************************
    public void setDefaultFooterColor(int color){
    	//this.defaultFooterColor = res.getColor(color);
    }
    //:::::::::::::::::::: Interface OnCenterItemClickListener :::::::::::::::::::::::::::::::
    public interface OnCenterItemClickListener {
        /**
         * Callback when the center item has been clicked.
         *
         * @param position Position of the current center item.
         */
        void onCenterItemClick(int position);
    }
    
    //:::::::::::::::::::: Interface PageIndicator ViewPager.OnPageChangeListener :::::::::::::::
    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }
    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }
    @Override
    public void setViewPager(ViewPager view, int initialPosition, int color) {
        setViewPager(view, color);
        setCurrentItem(initialPosition);
    }
    @Override
    public void setViewPager(ViewPager view, int color) {
    	setFooterColor(res.getColor(color)); 
    	
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
    }
    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }
 
    //:::::::::::::::::::::::::::::: Override VIEW ::::::::::::::::::::::::::::
    @SuppressWarnings("incomplete-switch")
	@Override
    // @see android.view.View#onDraw(android.graphics.Canvas)
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }

        // mCurrentPage is -1 on first start and after orientation changed. If so, retrieve the correct index from viewpager.
        if (mCurrentPage == -1 && mViewPager != null) {
            mCurrentPage = mViewPager.getCurrentItem();
        }

        //Calculate views bounds
        ArrayList<Rect> bounds = calculateAllBounds(mPaintText);
        final int boundsSize = bounds.size();

        //Make sure we're on a page that still exists
        if (mCurrentPage >= boundsSize) {
            setCurrentItem(boundsSize - 1);
            return;
        }

        final int countMinusOne = count - 1;
        final float halfWidth = getWidth() / 2f;
        final int left = getLeft();
        final float leftClip = left + mClipPadding;
        final int width = getWidth();
        int height = getHeight();
        final int right = left + width;
        final float rightClip = right - mClipPadding;

        int page = mCurrentPage;
        float offsetPercent;
        if (mPageOffset <= 0.5) {
            offsetPercent = mPageOffset;
        } else {
            page += 1;
            offsetPercent = 1 - mPageOffset;
        }
        final boolean currentSelected = (offsetPercent <= SELECTION_FADE_PERCENTAGE);
        final boolean currentBold = (offsetPercent <= BOLD_FADE_PERCENTAGE);
        final float selectedPercent = (SELECTION_FADE_PERCENTAGE - offsetPercent) / SELECTION_FADE_PERCENTAGE;

        //Verify if the current view must be clipped to the screen
        Rect curPageBound = bounds.get(mCurrentPage);
        float curPageWidth = curPageBound.right - curPageBound.left;
        if (curPageBound.left < leftClip) {
            //Try to clip to the screen (left side)
            clipViewOnTheLeft(curPageBound, curPageWidth, left);
        }
        if (curPageBound.right > rightClip) {
            //Try to clip to the screen (right side)
            clipViewOnTheRight(curPageBound, curPageWidth, right);
        }

        //Left views starting from the current position
        if (mCurrentPage > 0) {
            for (int i = mCurrentPage - 1; i >= 0; i--) {
                Rect bound = bounds.get(i);
                //Is left side is outside the screen
                if (bound.left < leftClip) {
                    int w = bound.right - bound.left;
                    //Try to clip to the screen (left side)
                    clipViewOnTheLeft(bound, w, left);
                    //Except if there's an intersection with the right view
                    Rect rightBound = bounds.get(i + 1);
                    //Intersection
                    if (bound.right + mTitlePadding > rightBound.left) {
                        bound.left = (int) (rightBound.left - w - mTitlePadding);
                        bound.right = bound.left + w;
                    }
                }
            }
        }
        //Right views starting from the current position
        if (mCurrentPage < countMinusOne) {
            for (int i = mCurrentPage + 1 ; i < count; i++) {
                Rect bound = bounds.get(i);
                //If right side is outside the screen
                if (bound.right > rightClip) {
                    int w = bound.right - bound.left;
                    //Try to clip to the screen (right side)
                    clipViewOnTheRight(bound, w, right);
                    //Except if there's an intersection with the left view
                    Rect leftBound = bounds.get(i - 1);
                    //Intersection
                    if (bound.left - mTitlePadding < leftBound.right) {
                        bound.left = (int) (leftBound.right + mTitlePadding);
                        bound.right = bound.left + w;
                    }
                }
            }
        }

        //Now draw views ******************************* TITLE SUB-MENU ************************************
        int colorTextAlpha = mColorText >>> 24;
        for (int i = 0; i < count; i++) {
            //Get the title
            Rect bound = bounds.get(i);
            //Only if one side is visible
            if ((bound.left > left && bound.left < right) || (bound.right > left && bound.right < right)) {
                final boolean currentPage = (i == page);
                final CharSequence pageTitle = getTitle(i);

                //Only set bold if we are within bounds
                mPaintText.setFakeBoldText(currentPage && currentBold && mBoldText);

                //Draw text as unselected
                mPaintText.setColor(mColorText);
                if(currentPage && currentSelected) {
                    //Fade out/in unselected text as the selected text fades in/out
                    mPaintText.setAlpha(colorTextAlpha - (int)(colorTextAlpha * selectedPercent));
                }

                //Except if there's an intersection with the right view
                if (i < boundsSize - 1)  {
                    Rect rightBound = bounds.get(i + 1);
                    //Intersection
                    if (bound.right + mTitlePadding > rightBound.left) {
                        int w = bound.right - bound.left;
                        bound.left = (int) (rightBound.left - w - mTitlePadding);
                        bound.right = bound.left + w;
                    }
                }
                canvas.drawText(pageTitle, 0, pageTitle.length(), bound.left, bound.bottom + mTopPadding, mPaintText);

                //If we are within the selected bounds draw the selected text
                if (currentPage && currentSelected) {
                    mPaintText.setColor(mColorSelected);
                    mPaintText.setAlpha((int)((mColorSelected >>> 24) * selectedPercent));
                    canvas.drawText(pageTitle, 0, pageTitle.length(), bound.left, bound.bottom + mTopPadding, mPaintText);
                }
            }
        }

        //If we want the line on the top change height to zero and invert the line height to trick the drawing code
        float footerLineHeight = mFooterLineHeight;
        float footerIndicatorLineHeight = mFooterIndicatorHeight;
        if (mLinePosition == LinePosition.Top) {
            height = 0;
            footerLineHeight = -footerLineHeight;
            footerIndicatorLineHeight = -footerIndicatorLineHeight;
        }

        //Draw the footer line
        mPath.reset();
        mPath.moveTo(0, height - footerLineHeight / 2f);
        mPath.lineTo(width, height - footerLineHeight / 2f);
        mPath.close();
        canvas.drawPath(mPath, mPaintFooterLine);

        float heightMinusLine = height - footerLineHeight;
        switch (mFooterIndicatorStyle) {
            case Triangle:
                mPath.reset();
                mPath.moveTo(halfWidth, heightMinusLine - footerIndicatorLineHeight);
                mPath.lineTo(halfWidth + footerIndicatorLineHeight, heightMinusLine);
                mPath.lineTo(halfWidth - footerIndicatorLineHeight, heightMinusLine);
                mPath.close();
                canvas.drawPath(mPath, mPaintFooterIndicator);
                break;

            case Underline:
                if (!currentSelected || page >= boundsSize) {
                    break;
                }

                Rect underlineBounds = bounds.get(page);
                final float rightPlusPadding = underlineBounds.right + mFooterIndicatorUnderlinePadding;
                final float leftMinusPadding = underlineBounds.left - mFooterIndicatorUnderlinePadding;
                final float heightMinusLineMinusIndicator = heightMinusLine - footerIndicatorLineHeight;

                mPath.reset();
                mPath.moveTo(leftMinusPadding, heightMinusLine);
                mPath.lineTo(rightPlusPadding, heightMinusLine);
                mPath.lineTo(rightPlusPadding, heightMinusLineMinusIndicator);
                mPath.lineTo(leftMinusPadding, heightMinusLineMinusIndicator);
                mPath.close();

                mPaintFooterIndicator.setAlpha((int)(0xFF * selectedPercent));
                canvas.drawPath(mPath, mPaintFooterIndicator);
                mPaintFooterIndicator.setAlpha(0xFF);
                break;
        }
    }
    //-------------- Methods auxiliary onDraw
    /**
     * Calculate views bounds and scroll them according to the current index
     *
     * @param paint
     * @return
     */
    private ArrayList<Rect> calculateAllBounds(Paint paint) {
        ArrayList<Rect> list = new ArrayList<Rect>();
        //For each views (If no values then add a fake one)
        final int count = mViewPager.getAdapter().getCount();
        final int width = getWidth();
        final int halfWidth = width / 2;
        for (int i = 0; i < count; i++) {
            Rect bounds = calcBounds(i, paint);
            int w = bounds.right - bounds.left;
            int h = bounds.bottom - bounds.top;
            bounds.left = (int)(halfWidth - (w / 2f) + ((i - mCurrentPage - mPageOffset) * width));
            bounds.right = bounds.left + w;
            bounds.top = 0;
            bounds.bottom = h;
            list.add(bounds);
        }

        return list;
    }
    /**
     * Calculate the bounds for a view's title
     *
     * @param index
     * @param paint
     * @return
     */
    private Rect calcBounds(int index, Paint paint) {
        //Calculate the text bounds
        Rect bounds = new Rect();
        CharSequence title = getTitle(index);
        bounds.right = (int) paint.measureText(title, 0, title.length());
        bounds.bottom = (int) (paint.descent() - paint.ascent());
        return bounds;
    }
    private CharSequence getTitle(int i) {
        CharSequence title = mViewPager.getAdapter().getPageTitle(i);
        if (title == null) {
            title = EMPTY_TITLE;
        }
        return title;
    }  
    /**
     * Set bounds for the right textView including clip padding.
     *
     * @param curViewBound
     *            current bounds.
     * @param curViewWidth
     *            width of the view.
     */
    private void clipViewOnTheRight(Rect curViewBound, float curViewWidth, int right) {
        curViewBound.right = (int) (right - mClipPadding);
        curViewBound.left = (int) (curViewBound.right - curViewWidth);
    }
    /**
     * Set bounds for the left textView including clip padding.
     *
     * @param curViewBound
     *            current bounds.
     * @param curViewWidth
     *            width of the view.
     */
    private void clipViewOnTheLeft(Rect curViewBound, float curViewWidth, int left) {
        curViewBound.left = (int) (left + mClipPadding);
        curViewBound.right = (int) (mClipPadding + curViewWidth);
    }
    
    //:::::::::::::::::::::::::: Override VIEW  ::::::::::::::::::::::::::::::::
    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mPageOffset = positionOffset;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }
    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Measure our width in whatever mode specified
        final int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);

        //Determine our height
        float height;
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightSpecMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            //Calculate the text bounds
            mBounds.setEmpty();
            mBounds.bottom = (int) (mPaintText.descent() - mPaintText.ascent());
            height = mBounds.bottom - mBounds.top + mFooterLineHeight + mFooterPadding + mTopPadding;
            if (mFooterIndicatorStyle != IndicatorStyle.None) {
                height += mFooterIndicatorHeight;
            }
        }
        final int measuredHeight = (int)height;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }
    
    //************************** Methods SET e GET TitlePageIndicator ******************************************
    /**
     * Set a callback listener for the center item click.
     *
     * @param listener Callback instance.
     */
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        mCenterItemClickListener = listener;
    }
    //---------------------------- Paint FooterColor
    public int getFooterColor() {
        return mPaintFooterLine.getColor();
    }
    public void setFooterColor(int footerColor) {
        mPaintFooterLine.setColor(footerColor);
        mPaintFooterIndicator.setColor(footerColor);
        invalidate();
    }
  //---------------------------- Paint PaintText
    public float getTextSize() {
        return mPaintText.getTextSize();
    }

    public void setTextSize(float textSize) {
        mPaintText.setTextSize(textSize);
        invalidate();
    }
    public void setTypeface(Typeface typeface) {
        mPaintText.setTypeface(typeface);
        invalidate();
    }

    public Typeface getTypeface() {
        return mPaintText.getTypeface();
    }
    //---------------------------- FooterLineHeight
    public float getFooterLineHeight() {
        return mFooterLineHeight;
    }
    public void setFooterLineHeight(float footerLineHeight) {
        mFooterLineHeight = footerLineHeight;
        mPaintFooterLine.setStrokeWidth(mFooterLineHeight);
        invalidate();
    }
    //---------------------------- FooterIndicatorHeight
    public float getFooterIndicatorHeight() {
        return mFooterIndicatorHeight;
    }
    public void setFooterIndicatorHeight(float footerTriangleHeight) {
        mFooterIndicatorHeight = footerTriangleHeight;
        invalidate();
    }
    //---------------------------- FooterPadding
    public float getFooterIndicatorPadding() {
        return mFooterPadding;
    }
    public void setFooterIndicatorPadding(float footerIndicatorPadding) {
        mFooterPadding = footerIndicatorPadding;
        invalidate();
    }
    //---------------------------- FooterIndicatorStyle
    public IndicatorStyle getFooterIndicatorStyle() {
        return mFooterIndicatorStyle;
    }
    public void setFooterIndicatorStyle(IndicatorStyle indicatorStyle) {
        mFooterIndicatorStyle = indicatorStyle;
        invalidate();
    }
    //---------------------------- LinePosition
    public LinePosition getLinePosition() {
        return mLinePosition;
    }
    public void setLinePosition(LinePosition linePosition) {
        mLinePosition = linePosition;
        invalidate();
    }
    //---------------------------- ColorSelected
    public int getSelectedColor() {
        return mColorSelected;
    }
    public void setSelectedColor(int selectedColor) {
        mColorSelected = selectedColor;
        invalidate();
    }
    //---------------------------- BoldText
    public boolean isSelectedBold() {
        return mBoldText;
    }
    public void setSelectedBold(boolean selectedBold) {
        mBoldText = selectedBold;
        invalidate();
    }
    //---------------------------- ColorText
    public int getTextColor() {
        return mColorText;
    }

    public void setTextColor(int textColor) {
        mPaintText.setColor(textColor);
        mColorText = textColor;
        invalidate();
    }
    //---------------------------- TitlePadding
    public float getTitlePadding() {
        return this.mTitlePadding;
    }

    public void setTitlePadding(float titlePadding) {
        mTitlePadding = titlePadding;
        invalidate();
    }
    //---------------------------- TopPadding
    public float getTopPadding() {
        return this.mTopPadding;
    }

    public void setTopPadding(float topPadding) {
        mTopPadding = topPadding;
        invalidate();
    }
    //---------------------------- ClipPadding
    public float getClipPadding() {
        return this.mClipPadding;
    }

    public void setClipPadding(float clipPadding) {
        mClipPadding = clipPadding;
        invalidate();
    }
    
    //******************************* EVENTS TITLEPAGEINDICATOR ***********************************
    @SuppressLint("ClickableViewAccessibility") 
    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mLastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float deltaX = x - mLastMotionX;

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop) {
                        mIsDragging = true;
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x;
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                        mViewPager.fakeDragBy(deltaX);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;
                    final float leftThird = halfWidth - sixthWidth;
                    final float rightThird = halfWidth + sixthWidth;
                    final float eventX = ev.getX();

                    if (eventX < leftThird) {
                        if (mCurrentPage > 0) {
                            if (action != MotionEvent.ACTION_CANCEL) {
                                mViewPager.setCurrentItem(mCurrentPage - 1);
                            }
                            return true;
                        }
                    } else if (eventX > rightThird) {
                        if (mCurrentPage < count - 1) {
                            if (action != MotionEvent.ACTION_CANCEL) {
                                mViewPager.setCurrentItem(mCurrentPage + 1);
                            }
                            return true;
                        }
                    } else {
                        //Middle third
                        if (mCenterItemClickListener != null && action != MotionEvent.ACTION_CANCEL) {
                            mCenterItemClickListener.onCenterItemClick(mCurrentPage);
                        }
                    }
                }

                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                if (mViewPager.isFakeDragging()) mViewPager.endFakeDrag();
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        return true;
    }
    
    
    //*********************************** Class SaveState *****************************************
    static class SavedState extends BaseSavedState {
        int currentPage;

        //--------------- Construct
        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }
        //--------------------------------------------------
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
    //***********************************************************************************************************************************
}
