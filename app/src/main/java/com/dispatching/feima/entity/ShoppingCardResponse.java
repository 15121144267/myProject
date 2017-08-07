package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/7.
 */

public class ShoppingCardResponse implements Serializable {

    public List<ShoppingCardListBean> products;

    public static class ShoppingCardListBean implements Serializable  {

        public String shopName;
        public List<ShoppingCardBean> shoppingCardBeen;

        public static class ShoppingCardBean implements Serializable {
            public String price;
            public String specification;
            public String describe;
        }

    }
}
