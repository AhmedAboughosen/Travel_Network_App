package com.example.socialnetworkfortravellers.utilLayer;

import android.widget.TextView;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.example.socialnetworkfortravellers.R;

public class ReadMoreTextView extends TextView {

    private static final int DEFAULT_TRIM_LINES = 2;
    private static final int INVALID_END_INDEX = -1;
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final String ELLIPSIZE = "... ";

    private CharSequence text;
    private BufferType bufferType;
    private boolean readMore = true;
    private CharSequence collapsedText;
    private CharSequence expandedText;
    private ExpandableClickableSpan expandableSpan;
    private int colorClickableText;
    private boolean showExpandedText;
    private int trimLines = INVALID_END_INDEX;
    private float expandableTextLen;

    public ReadMoreTextView(Context context) {
        this(context, null);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);

        String trimCollapsedText = typedArray.getString(R.styleable.ReadMoreTextView_collapsedText);
        if (trimCollapsedText == null) {
            int resourceIdTrimCollapsedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_collapsedText, R.string.read_more);
            this.collapsedText = getResources().getString(resourceIdTrimCollapsedText);
        } else {
            this.collapsedText = trimCollapsedText;
        }

        String trimExpandedText = typedArray.getString(R.styleable.ReadMoreTextView_expandedText);
        if (trimExpandedText == null) {
            int resourceIdTrimExpandedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_expandedText, R.string.read_less);
            this.expandedText = getResources().getString(resourceIdTrimExpandedText);
        } else {
            this.expandedText = trimExpandedText;
        }


        this.trimLines = typedArray.getInt(R.styleable.ReadMoreTextView_trimLines, DEFAULT_TRIM_LINES);

        this.colorClickableText = typedArray.getColor(R.styleable.ReadMoreTextView_clickableTextColor, ContextCompat.getColor(context, R.color.colorPrimaryDark));

        this.showExpandedText = typedArray.getBoolean(R.styleable.ReadMoreTextView_showExpandedText, DEFAULT_SHOW_TRIM_EXPANDED_TEXT);
        typedArray.recycle();
        expandableSpan = new ExpandableClickableSpan();
        onGlobalLayoutLineEndIndex();
        setText();
        expandableTextLen = getMeasureTextWidth(ELLIPSIZE + this.collapsedText);
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text;
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (text != null) {
            if (readMore) {
                return updateCollapsedText();
            } else {
                return updateExpandedText();
            }
        }
        return null;
    }

    private CharSequence updateCollapsedText() {
        StringBuilder sb = new StringBuilder();
        if (getLayout() != null) {
            if (trimLines < getLayout().getLineCount()) {
                for (int i = 0; i < trimLines; i++) {
                    if (i < trimLines - 1) {
                        sb.append(getLineText(i));
                    } else {
                        sb.append(getTrimLineText(i));
                    }
                }
                SpannableStringBuilder s = new SpannableStringBuilder(sb)
                        .append(ELLIPSIZE)
                        .append(collapsedText);
                return addClickableSpan(s, collapsedText);
            }
        }
        return sb.append(text);
    }

    private CharSequence updateExpandedText() {
        if (showExpandedText) {
            SpannableStringBuilder s = new SpannableStringBuilder(text, 0, text.length()).append(expandedText);
            return addClickableSpan(s, expandedText);
        }
        return text;
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {
        s.setSpan(expandableSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }


    public void setClickableTextColor(@ColorInt int colorClickableText) {
        this.colorClickableText = colorClickableText;
    }

    public void setCollapsedText(CharSequence collapsedText) {
        this.collapsedText = collapsedText;
    }

    public void setExpandedText(CharSequence expandedText) {
        this.expandedText = expandedText;
    }

    public void setClickableTextColorId(@ColorRes int colorClickableText) {
        this.colorClickableText = ContextCompat.getColor(getContext(), colorClickableText);
    }

    public void setTrimCollapsedText(@StringRes int trimCollapsedText) {
        this.collapsedText = getResources().getText(trimCollapsedText);
    }

    public void setTrimExpandedText(@StringRes int trimExpandedText) {
        this.expandedText = getResources().getText(trimExpandedText);
        ;
    }

    public void setTrimLines(int trimLines) {
        this.trimLines = trimLines;
    }

    private class ExpandableClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            readMore = !readMore;
            setText();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(colorClickableText);
        }
    }

    private void onGlobalLayoutLineEndIndex() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                setText();
            }
        });
    }


    private CharSequence getLineText(int line) {
        int lineStart = getLayout().getLineStart(line);
        int lineEnd = getLayout().getLineEnd(this.getLineCount() - 1);
        return text.subSequence(lineStart, lineEnd);
    }

    private CharSequence getTrimLineText(int line) {
        int lineStart = getLayout().getLineStart(line);
        int length = text.length();
        int lineEnd = Math.min(getLayout().getLineEnd(line), length);
        for (int i = lineEnd; i > lineStart; i--) {
            CharSequence charSequence = text.subSequence(lineStart, i);
            float measureTextWidth = getMeasureTextWidth(charSequence.toString());
            int availableWidth = getAvailableWidth();
            if (availableWidth >= measureTextWidth + expandableTextLen) {
                return charSequence;
            }
        }
        return text.subSequence(lineStart, lineEnd);
    }

    private int getAvailableWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private float getMeasureTextWidth(String text) {
        Paint paint = getPaint();
        return paint.measureText(text);
    }
}