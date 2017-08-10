package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.AddressResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class AddressAdapter extends BaseQuickAdapter<AddressResponse.DataBean, BaseViewHolder> {
    private final Context mContext;


    public AddressAdapter(List<AddressResponse.DataBean> mList, Context context) {
        super(R.layout.adapter_address, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResponse.DataBean item) {

        if (item == null) return;

        helper.addOnClickListener(R.id.address_edit).addOnClickListener(R.id.address_delete).addOnClickListener(R.id.address_default);
        helper.setText(R.id.address_name, item.receiverName + "");
        helper.setText(R.id.address_phone, item.receiverPhone);
        helper.setText(R.id.address_address, item.address + item.area);
        helper.setChecked(R.id.address_default, item.isDefault == 1);
        helper.setText(R.id.address_default_text, item.isDefault == 1 ? "默认地址" : "设为默认");

    }

}
