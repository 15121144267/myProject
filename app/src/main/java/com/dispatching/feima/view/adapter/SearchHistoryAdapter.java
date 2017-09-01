package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.view.customview.MyLinearLayout;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class SearchHistoryAdapter extends BaseQuickAdapter<SpecificationResponse.ProductsBean.SpecificationListBean, BaseViewHolder> {
    private final Context mContext;
    public SearchHistoryAdapter(List<SpecificationResponse.ProductsBean.SpecificationListBean> notices, Context context) {
        super(R.layout.adapter_search_history, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecificationResponse.ProductsBean.SpecificationListBean item) {
        if (item == null) return;
        String type = item.partName;
        MyLinearLayout myLinearLayout = helper.getView(R.id.adapter_search_tag);
        if(helper.getAdapterPosition()==0){
            helper.setVisible(R.id.adapter_search_close,true);
        }
        if (myLinearLayout.getChildCount() == 0) {
            helper.setText(R.id.adapter_search_name, item.partName);
            List<String> mList = item.value;
            TextView[] textViews = new TextView[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                TextView textView = new TextView(mContext);
                textView.setGravity(17);
                textView.setPadding(25, 15, 25, 15);
                textViews[i] = textView;
                textViews[i].setBackgroundResource(R.drawable.shape_gray);
                textViews[i].setText(mList.get(i));
                textViews[i].setTag(i);
                myLinearLayout.addView(textViews[i]);
            }
            for (TextView textView : textViews) {
                textView.setTag(textViews);
                textView.setOnClickListener(new SpecificationClickListener(type));
            }
        }
    }
    private class SpecificationClickListener implements View.OnClickListener {
        private String type;

        public SpecificationClickListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            clickEvent(type, v);
        }
    }

    private void clickEvent(String type, View v) {

        TextView[] textViews = (TextView[]) v.getTag();
        TextView tv = (TextView) v;
        for (TextView textView : textViews) {
            if (tv.equals(textView)) {
                String specificationContent = textView.getText().toString();

               // selectProMap.put(type, specificationContent);
            }
        }
        notifyDataSetChanged();
    }
}
