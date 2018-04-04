package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.model.PackageData;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 11/8/2017.
 */

public class MySinglePackageDetails extends RecyclerView.Adapter<MySinglePackageDetails.MySingleViewHolder> {

    private Context context;
    private ArrayList<PackageData> mData;

    public MySinglePackageDetails(Context context, ArrayList<PackageData> mData){
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MySingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlepackage_item,parent,false);
        return new MySingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySingleViewHolder holder, int position) {
        PackageData packageData =mData.get(position);
        String categoryname=packageData.getCategoryName()== "null" ? "" :packageData.getCategoryName();
        String subjectname = packageData.getSubjectName() == "null" ? "" :"/"+packageData.getSubjectName();
        String sublevel =packageData.getSublevelName() == "null" ? "" :"/"+packageData.getSublevelName();
        String subchaptername =packageData.getSublevelName() == "null" ? "" : "/"+packageData.getSublevelName();

        holder.txtView.setText(categoryname+subjectname+sublevel+subchaptername);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MySingleViewHolder extends RecyclerView.ViewHolder{

        TextView txtView;

        public MySingleViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView)itemView.findViewById(R.id.packagename);
        }
    }
}
