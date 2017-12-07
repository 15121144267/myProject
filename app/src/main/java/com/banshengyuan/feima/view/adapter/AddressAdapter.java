package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AddressResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class AddressAdapter extends BaseQuickAdapter<AddressResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public AddressAdapter(List<AddressResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_address, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResponse.ListBean item) {

        if (item == null) return;

        helper.addOnClickListener(R.id.address_edit).addOnClickListener(R.id.address_delete).addOnClickListener(R.id.address_default);
        helper.setText(R.id.address_name, item.getName());
        helper.setText(R.id.address_phone, item.getMobile());
        String address = item.getProvince() + item.getCity() + item.getArea() + " " + item.getStreet() +" "+ item.getAddress();
        helper.setText(R.id.address_address, address);
        helper.setChecked(R.id.address_default, item.getIs_default() == 2);
        helper.setText(R.id.address_default_text, item.getIs_default() == 2 ? "默认地址" : "设为默认");
    }

}
