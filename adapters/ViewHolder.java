package com.example.terence.internsmartassistant.adapters;

/**
 * Created by Terence on 10/10/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.terence.internsmartassistant.R;

/**
 * Created by Terence on 10/5/2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView txtmessage,txtin,txtout;

    public ViewHolder(View itemView){
        super(itemView);

        view = itemView;
        txtmessage = (TextView)view.findViewById(R.id.txtMessage);
        txtin = (TextView) view.findViewById(R.id.txtIn);
        txtout = (TextView) view.findViewById(R.id.txtOut);

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