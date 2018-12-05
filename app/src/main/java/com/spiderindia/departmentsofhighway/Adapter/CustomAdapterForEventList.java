package com.spiderindia.departmentsofhighway.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spiderindia.departmentsofhighway.Details.EventListDetails;
import com.spiderindia.departmentsofhighway.R;

import java.util.ArrayList;

/**
 * Created by pyr on 16-Aug-18.
 */

public class CustomAdapterForEventList extends BaseAdapter{
    Context mContext;
    ArrayList<EventListDetails> eventList;

    LayoutInflater inflator;

    public CustomAdapterForEventList(Context context, ArrayList<EventListDetails> eventListDetail) {
        mContext=context;
        this.eventList=eventListDetail;
        this.inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  EventListDetails getItem1(int position)
    {
        return ((EventListDetails) getItem(position));
    }
    static class CardViewHolder {
        TextView titleTxt,contentTxt,dateTxt;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final EventListDetails m = getItem1(position);
        View mView = convertView;

        if(mView==null)
        {
            mView=inflator.inflate(R.layout.row_item_for_event_list,null,false);
        }

        CardViewHolder cd = new CardViewHolder();
        cd.titleTxt= (TextView) mView.findViewById(R.id.title_txt);
        cd.dateTxt= (TextView) mView.findViewById(R.id.date_txt);
        cd.contentTxt = (TextView) mView.findViewById(R.id.content_event);

        cd.titleTxt.setText(m.getTitle());
        cd.dateTxt.setText(m.getDate());
        cd.contentTxt.setText(m.getContent());

        mView.setTag(position);

        return mView;
    }
}
