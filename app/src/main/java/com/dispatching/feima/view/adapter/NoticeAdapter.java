package com.dispatching.feima.view.adapter;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.DataServer;
import com.dispatching.feima.entity.Status;


public class NoticeAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public NoticeAdapter() {
        super( R.layout.adapter_notice, DataServer.getSampleData(10));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.adapter_content,item.getText());
        helper.setText(R.id.adapter_time,item.getCreatedAt());
    }

}
