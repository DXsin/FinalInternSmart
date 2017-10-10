package com.example.terence.internsmartassistant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.terence.internsmartassistant.R;

/**
 * Created by Terence on 10/5/2017.
 */

public class JournalViewHolder extends RecyclerView.ViewHolder {

    View mView;
    TextView txtmessage,txtin,txtout;

    public JournalViewHolder(View itemView){
        super(itemView);

        mView = itemView;

        txtmessage = (TextView)mView.findViewById(R.id.txtMessage);
        txtin = (TextView) mView.findViewById(R.id.txtIn);
        txtout = (TextView) mView.findViewById(R.id.txtOut);


    }

    public void setJMessage(String message){
        txtmessage.setText(message);

    }
    public void setJIn(String in){
        txtin.setText(in);
    }

    public void setJOut(String out){
        txtout.setText(out);
    }


}
