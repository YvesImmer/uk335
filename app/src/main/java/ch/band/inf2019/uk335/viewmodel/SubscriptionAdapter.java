package ch.band.inf2019.uk335.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.activities.EditSubscritpionActivity;
import ch.band.inf2019.uk335.db.Subscription;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>{
    public static final String EXTRA_SUBSCRIPTION_ID = "ch.band.inf2019.uk335.EXTRA_SUBSCRIPTION_ID";

    private static final String TAG = "SubscriptionAdapter";

    private ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
    private View.OnClickListener editOnclickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(editOnclickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Subscription current_item = subscriptions.get(position);

        //TODO implement method to get category name for a Subscription
        //holder.text_view_category.setText(current_item.getCategory());
        //RELEASE
//        holder.text_view_abo.setText(current_item.title);
//        holder.text_view_price.setText(current_item.price);
//        holder.text_view_duedate.setText(String.valueOf(current_item.dayofnextPayment));
        editOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditActivity(v, current_item.subsciriptionid);
            }
        };
        holder.parent_layout.setOnClickListener(editOnclickListener);

    }

    private void openEditActivity(View v, int subsciriptionid) {
        Intent intent = new Intent(v.getContext(), EditSubscritpionActivity.class);
        intent.putExtra(EXTRA_SUBSCRIPTION_ID, subsciriptionid);
        v.getContext().startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions){
        this.subscriptions = subscriptions;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parent_layout;
        TextView text_view_price;
        TextView text_view_duedate;
        TextView text_view_abo;
        TextView text_view_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.relative_layout_card_parent);
            text_view_price = itemView.findViewById(R.id.text_view_price);
            text_view_duedate = itemView.findViewById(R.id.text_view_duedate);
            text_view_abo = itemView.findViewById(R.id.text_view_abo);
            text_view_category = itemView.findViewById(R.id.text_view_category);
        }
    }
}
