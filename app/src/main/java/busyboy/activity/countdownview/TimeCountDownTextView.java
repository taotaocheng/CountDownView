package busyboy.activity.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by busyboy on 2015/9/25.
 */
public class TimeCountDownTextView extends TextView {
    private static final long MAX_COUNTDOWN_TIME = 1000 * 60 * 30; // 30 minutes
    private CountDownTimer mTimer = null;
    private String mCss;
    private long mCountDownTime;
    private long mSecond;
    private long mMinute;
    private onCountDownFinishListener mOnCountDownFinishListener;

    public TimeCountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCountDownTime(context, attrs);
    }

    public TimeCountDownTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCountDownTime(context, attrs);
    }

    public TimeCountDownTextView(Context context) {
        super(context);
    }

    private void initCountDownTime(Context context, AttributeSet attrs) {
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.TimeCountDownView);
        mCountDownTime = (long) attribute.getFloat(R.styleable.TimeCountDownView_countDownTime, 0);
        mCss = attribute.getString(R.styleable.TimeCountDownView_count_down_format);
        if (TextUtils.isEmpty(mCss)) {
            mCss = getContext().getString(R.string.count_down_default_format);
        }
    }

    public void setCountDownTimes(long countDownTime, String cssResId) {
        if (!TextUtils.isEmpty(cssResId)) {
            this.mCss = cssResId;
        }
        mCountDownTime = countDownTime;
    }

    public void setCountDownTimes(long countDownTime) {
        mCountDownTime = countDownTime;
    }

    public void start() {
        if (mCountDownTime < 0) {
            mCountDownTime = 0;
        } else {
            if (mCountDownTime > MAX_COUNTDOWN_TIME) {
                mCountDownTime = MAX_COUNTDOWN_TIME;
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            int countDownInterval = 1000;
            mTimer = new CountDownTimer(mCountDownTime, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mMinute = millisUntilFinished / (1000 * 60);
                    mSecond = (millisUntilFinished % (1000 * 60)) / 1000;
                    TimeCountDownTextView.this.setText(Html.fromHtml(String.format(mCss, mMinute, mSecond)));
                }

                @Override
                public void onFinish() {
                    if(mOnCountDownFinishListener != null){
                        mOnCountDownFinishListener.onFinish();
                    }
                }
            };
        }
        mTimer.start();
    }

    public void setOnCountDownFinishListener(onCountDownFinishListener onCountDownFinishListener) {
        this.mOnCountDownFinishListener = onCountDownFinishListener;
    }

    public interface onCountDownFinishListener {
        void onFinish();
    }
}
