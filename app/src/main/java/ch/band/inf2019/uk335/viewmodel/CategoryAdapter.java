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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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


    /**
     * Is called by the observer to keep us informed when the dataset changes so we always have the newest data.
     * @param categories
     */
    public void setCategories(ArrayList<Categorie> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    /**
     * Is called by the observer to keep us informed when the dataset changes so we always have the newest data.
     * @param subscriptions
     */
    public void setSubscriptions(ArrayList<Subscription> subscriptions){
        this.subscriptions = subscriptions;
        notifyDataSetChanged();

    }

    /**
     * Creates a holder for our view which stores all the metadata related to the view.
     * @param parent
     * @param viewType
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Prepares the view for dispaying by assigning all needed values.
     * @param holder
     * @param position
     */
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
        holder.text_view_price.setText(NumberFormat.getCurrencyInstance(new Locale("DE","CH")).format(mothlyCost(current_item.id)/100.0));
        holder.text_view_name.setText(current_item.title);
        holder.parent_layout.setBackgroundColor(current_item.color);

    }

    /**
     *Is called so the RecyclerView knows how many items it has to display.
     * @return
     */
    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * Class to store the metadata of a particular item inside the RecyclerView.
     */
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

    /**
     * Is called by onClick to open the edit view to edit the category item.
     * @param v
     * @param categoryid
     */
    private void openEditActivity(View v, int categoryid) {
        Intent intent = new Intent(v.getContext(), EditCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORIE_ID, categoryid);
        v.getContext().startActivity(intent);
    }

    /**
     * Calculates the cost of all subscriptions in the category
     * @param cathegorieid
     * @return int cost
     */
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
                cost+= s.price;
            }
        }
        return cost;
    }
}

