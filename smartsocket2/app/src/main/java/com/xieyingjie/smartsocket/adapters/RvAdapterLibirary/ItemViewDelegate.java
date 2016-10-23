package com.xieyingjie.smartsocket.adapters.RvAdapterLibirary;


/**
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
