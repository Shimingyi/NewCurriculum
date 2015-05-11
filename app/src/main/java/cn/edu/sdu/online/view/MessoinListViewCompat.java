package cn.edu.sdu.online.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.sdu.online.database.MessionDBHelper;
import cn.edu.sdu.online.modal.MessionMessageItem;
import cn.edu.sdu.online.newcurriculum.R;

public class MessoinListViewCompat extends ListView {

    private static final String TAG = "ListViewCompat";
    private List<MessionMessageItem> mMessageItems;
    private MessionSlideView mFocusedItemView;
    private MessionDBHelper dbhelper = new MessionDBHelper(this.getContext());

    public MessoinListViewCompat(Context context) {
        super(context);
    }

    public void setlist(List<MessionMessageItem> list) {
        mMessageItems = list;
    }

    public MessoinListViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessoinListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                final int position = pointToPosition(x, y);
                Log.e(TAG, "postion=" + position);
                if (position != INVALID_POSITION) {
                    int firstPos = getFirstVisiblePosition(); // 因为ListView会进行缓存，如果你不这么干，有些行的view你是得不到的。
                    mFocusedItemView = (MessionSlideView) getChildAt(position - firstPos);
                    final TextView content_delete = (TextView) mFocusedItemView
                            .findViewById(R.id.content_delete);

                    int status = mMessageItems.get(position).getStatus();
                    if (status == 0) {
                        content_delete.setText(getResources().getString(R.string.mession_complete));
                    } else {
                        content_delete.setText(getResources().getString(R.string.mession_undo));
                    }
                    content_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mFocusedItemView.shrink();// 点击缩回去
                            String str = content_delete.getText().toString();
                            String getstr;
                            if (str.equals(getResources().getString(R.string.mession_complete))) {
                                getstr = getResources().getString(R.string.mession_undo);
                                mMessageItems.get(position).setStatus(1);
                                dbhelper.updateStatus(mMessageItems.get(position).getUuid(), 1);
                            } else {
                                getstr = getResources().getString(R.string.mession_complete);
                                mMessageItems.get(position).setStatus(0);
                                dbhelper.updateStatus(mMessageItems.get(position).getUuid(), 0);
                            }
                            content_delete.setText(getstr);
                            ((BaseAdapter) getAdapter()).notifyDataSetChanged();
                        }
                    });
                }
            }
            default:
                break;
        }
        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
//			if (event.getAction() == MotionEvent.ACTION_UP)
//				return true;
        }
        return super.onTouchEvent(event);
    }

}
