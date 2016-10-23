package com.xieyingjie.smartsocket.adapters.RvAdapterLibirary;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * 只要是单一类型的item都用这个
 * * @param <T>
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>{
    protected Context mContext; // 场景，由activity传过来
    protected int mLayoutId; //传过来的布局的Id
    protected List<T> mDatas;//要绑定的数据
    protected LayoutInflater mInflater;//布局填充器

    public CommonAdapter(final Context context, final int layoutId, List<T> datas){
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context); //
        mLayoutId = layoutId;
//        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>(){
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);
}
