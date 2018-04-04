package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.model.PackagesItems;
import com.saxxis.myexamspace.payment.MyPayment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/8/2017.
 */

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackageHolder> {

    private Context context;
    private ArrayList<PackagesItems> mdata;



    public PackagesAdapter(Context context, ArrayList<PackagesItems> mdata){
        this.mdata=mdata;
        this.context=context;
    }

    @Override
    public PackageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_item,parent,false);
        return new PackageHolder(view);
    }

    @Override
    public void onBindViewHolder(PackageHolder holder, int position) {

        final PackagesItems packitem = mdata.get(position);

        holder.title.setText(packitem.getQuiz_offer_title());
//        holder.examboard.setText(packitem.get);

        if (!packitem.getQuiz_offer_image().contains("images/package/")){
            Picasso.with(context)
                    .load(AppConstants.PACKAGE_IMAGE_URL+packitem.getQuiz_offer_image().replaceAll(" ","%20"))
                    .fit()
                    .placeholder(R.drawable.myexams_replacer)
                    .error(R.drawable.myexams_replacer)
                    .into(holder.testImage);
        }
        if (packitem.getQuiz_offer_image().contains("images/package/")){
            Picasso.with(context)
                    .load(AppConstants.SERVER_URL+packitem.getQuiz_offer_image().replaceAll(" ","%20"))
                    .fit()
                    .placeholder(R.drawable.myexams_replacer)
                    .error(R.drawable.myexams_replacer)
                    .into(holder.testImage);
        }


        Log.e("response",AppConstants.PACKAGE_IMAGE_URL+packitem
                .getQuiz_offer_image()+"   "+packitem.getQuiz_offer_title());

//        if (packitem.getSubjects().equals("null")||packitem.getSubjects().equals(" ")||packitem.getSubjects().equals("0")||packitem.getSubjects().equals("")){
//            holder.examboard.setVisibility(View.GONE);
//        }else{
//            holder.examboard.setVisibility(View.VISIBLE);
//            holder.examboard.setText(packitem.getSubjects());
//        }
        if (packitem.getSubjects()!="null"){
            holder.examboard.setVisibility(View.VISIBLE);
            holder.examboard.setText(packitem.getSubjects());
        }else {
            holder.examboard.setVisibility(View.GONE);
        }

        if (packitem.getQuiz_el().equalsIgnoreCase("Telugu")||
                packitem.getQuiz_el().equalsIgnoreCase("English")||
                packitem.getQuiz_el().equalsIgnoreCase("Hindi")){
             holder.lang.setVisibility(View.VISIBLE);
            holder.lang.setText(packitem.getQuiz_el());
        }else{
            holder.lang.setVisibility(View.GONE);
        }

        holder.noofQuezzes.setText(packitem.getQuiz_num());
        holder.noofquestions.setText(packitem.getQuiz_ques());

        if(!(packitem.getQuiz_tet().equals("0")||
                packitem.getQuiz_tet().equals(" ")||
                packitem.getQuiz_tet().equals("")||
                packitem.getQuiz_tet().equals("null"))){

            holder.examtype.setVisibility(View.VISIBLE);
            holder.examtype.setText(packitem.getQuiz_tet());
        }else{
            holder.examtype.setVisibility(View.GONE);
        }

        holder.packbyacademy.setText(packitem.getQuiz_offer_description());

        if (packitem.getQuiz_offer_price().equals("0")){
            holder.price.setText("Free");
            holder.packbuy.setText("Subscribe");

            holder.priceMarkerExample.setText("Free");
            holder.priceMarkerExample.setTextColor(Color.WHITE);
            holder.priceMarkerExample.setBackgroundResource(R.drawable.gree_ribbon);
        }

        if (!packitem.getQuiz_offer_price().equals("0")){
            holder.price.setText(context.getResources().getString(R.string.rupeecurrency)+" "+packitem.getQuiz_offer_price());
            holder.packbuy.setText("Buy");

            holder.priceMarkerExample.setText("Buy");
            holder.priceMarkerExample.setTextColor(Color.WHITE);
            holder.priceMarkerExample.setBackgroundResource(R.drawable.red_ribbon);
            holder.packbuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPayment myPayment=new MyPayment();
                    myPayment.callEbsKit(context,"45454",packitem.getQuiz_offer_price());
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class PackageHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.pack_title)
        TextView title;
        @BindView(R.id.pack_examboard)
        TextView examboard;
        @BindView(R.id.pack_examlang)
        TextView lang;
        @BindView(R.id.pack_examtype)
        TextView examtype;
        @BindView(R.id.pack_test_image)
        ImageView  testImage;
        @BindView(R.id.pack_noofquizzes)
        TextView noofQuezzes;
        @BindView(R.id.pack_noofquestions)
        TextView noofquestions;
        @BindView(R.id.pack_byacademy)
        TextView packbyacademy;
        @BindView(R.id.pack_price)
        Button price;
        @BindView(R.id.pack_buy)
        Button packbuy;
        @BindView(R.id.pricemakener_text)
        TextView priceMarkerExample;
        @BindView(R.id.exam_flaout)
        FrameLayout tagflLayout;

        public PackageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
