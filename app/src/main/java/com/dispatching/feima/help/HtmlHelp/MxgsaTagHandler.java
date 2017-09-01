package com.dispatching.feima.help.HtmlHelp;

import android.content.Context;
import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * Created by lei.he on 2017/7/12.
 * MxgsaTagHandler
 */

public class MxgsaTagHandler implements Html.TagHandler {
    private int sIndex = 0;
    private  int eIndex=0;
    private final Context mContext;

    public MxgsaTagHandler(Context context){
        mContext=context;
    }

    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("mxgsa")) {
            if (opening) {
                sIndex=output.length();
            }else {
                eIndex=output.length();
//                output.setSpan(new MxgsaSpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

}
