package com.chiibi.moneyflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.chiibi.moneyflow.Constant.TYPE_COLUMN;
import static com.chiibi.moneyflow.Constant.ITEM_COLUMN;
import static com.chiibi.moneyflow.Constant.AMOUNT_COLUMN;
public class ListViewAdapter extends BaseAdapter
{
    public ArrayList<HashMap> list;
    Activity activity;

    public ListViewAdapter(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.txtFirst = convertView.findViewById(R.id.FirstText);
            holder.txtSecond = convertView.findViewById(R.id.SecondText);
            holder.txtThird = convertView.findViewById(R.id.ThirdText);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.txtFirst.setText((CharSequence) map.get(TYPE_COLUMN));
        holder.txtSecond.setText((CharSequence) map.get(ITEM_COLUMN));
        holder.txtThird.setText(String.valueOf(map.get(AMOUNT_COLUMN)));


        return convertView;
    }

}
