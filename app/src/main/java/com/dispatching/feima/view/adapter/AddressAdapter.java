package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.AddressResponse;

import java.util.List;


public class AddressAdapter extends BaseQuickAdapter<AddressResponse, BaseViewHolder> {
    private final Context mContext;
    private Integer mPosition = Integer.MAX_VALUE;
    private boolean mIsOpen = false;

    public void setChecked(Integer position, boolean isOpen) {
        mPosition = position;
        mIsOpen = isOpen;
        notifyDataSetChanged();
    }

    public AddressAdapter(List<AddressResponse> mList, Context context) {
        super(R.layout.adapter_address, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResponse item) {

        if (item == null) return;
        if (helper.getAdapterPosition() == mPosition && mIsOpen) {
            helper.setChecked(R.id.address_default, true);
            helper.setText(R.id.address_default_text, "默认地址");
        } else if (mPosition == Integer.MAX_VALUE && !mIsOpen) {
            //第一次进入
            helper.addOnClickListener(R.id.address_edit).addOnClickListener(R.id.address_delete).addOnClickListener(R.id.address_default);
            helper.setText(R.id.address_name, item.name);
            helper.setText(R.id.address_phone, item.name);
            helper.setText(R.id.address_address, item.address);
            helper.setText(R.id.address_address, item.address);
            helper.setChecked(R.id.address_default, item.checkedAddress);
            helper.setText(R.id.address_default_text, item.checkedAddress ? "默认地址" : "设为默认");
        } else {
            helper.setChecked(R.id.address_default, false);
            helper.setText(R.id.address_default_text, "设为默认");
        }

    }

}
