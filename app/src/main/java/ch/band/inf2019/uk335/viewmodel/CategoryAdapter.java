package ch.band.inf2019.uk335.viewmodel;

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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.activities.EditCategoryActivity;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private static final String TAG = "SubscriptionAdapter";
    public static final String EXTRA_CATEGORIE_ID = "ch.band.inf2019.uk335.EXTRA_CATEGORIE_ID";
    private ArrayList<Subscription> subscriptions;
    private ArrayList<Categorie> categories = new ArrayList<Categorie>();


    public void setCategories(ArrayList<Categorie> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }
    public void setSubscriptions(ArrayList<Subscription> subscriptions){
        this.subscriptions = subscriptions;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Categorie current_item = categories.get(position);
        holder.parent_layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditActivity(v,current_item.id);
            }
        });
        //TODO implement a method to get the monthly cost of a category
        holder.text_view_price.setText(String.valueOf(mothlyCost(current_item.id)/100.0));
        holder.text_view_name.setText(current_item.title);
        holder.parent_layout.setBackgroundColor(current_item.color);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parent_layout;
        TextView text_view_name;
        TextView text_view_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.relative_layout_card_category_parent);
            text_view_price = itemView.findViewById(R.id.text_view_category_price);
            text_view_name = itemView.findViewById(R.id.text_view_category_name);
        }
    }

    private void openEditActivity(View v, int categoryid) {
        Intent intent = new Intent(v.getContext(), EditCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORIE_ID, categoryid);
        v.getContext().startActivity(intent);
    }
    private int mothlyCost(int cathegorieid){
        int cost = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        List<Subscription> subscriptionscopy = new ArrayList<Subscription>();
        for (Subscription s:subscriptions
             ) {
            if(s.categorieid == cathegorieid){
                subscriptionscopy.add(new Subscription(
                        s.title,
                        s.dayofnextPayment,
                        s.price,
                        s.categorieid,
                        s.frequency
                ));
            }

        }
        calendar.add(Calendar.MONTH,1);
        long monthInFuture = calendar.getTimeInMillis();
        for (Subscription s:subscriptionscopy
        ) {
            if(s.dayofnextPayment<monthInFuture){
                Log.d("Cost Calc",s.dayofnextPayment+" - "+monthInFuture+" = "+ (s.dayofnextPayment-monthInFuture) + s.title);
                cost+= s.price;
            }



        }
        Log.d("Cost Calc","Cost= "+cost);
        return cost;
    }
}

