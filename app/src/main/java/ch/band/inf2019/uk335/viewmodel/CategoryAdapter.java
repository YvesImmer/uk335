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
import java.util.Locale;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.activities.EditSubscritpionActivity;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private static final String TAG = "SubscriptionAdapter";
    private static final String EXTRA_CATEGORIE_ID = "ch.band.inf2019.uk335.EXTRA_CATEGORIE_ID";

    private ArrayList<Categorie> categories = new ArrayList<Categorie>();
    private View.OnClickListener editOnclickListener;

    public void setCategories(ArrayList<Categorie> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Categorie current_item = categories.get(position);

        //TODO implement a method to get the monthly cost of a category
        //holder.text_view_price.setText(current_item.monthlyPrice);
        //holder.text_view_name.setText(current_item.title);

        //DEBUG
        holder.text_view_name.setText("Title");
        holder.text_view_price.setText("Monthly Price");

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

    private void openEditActivity(View v, int subsciriptionid) {
        Intent intent = new Intent(v.getContext(), EditSubscritpionActivity.class);
        intent.putExtra(EXTRA_CATEGORIE_ID, subsciriptionid);
        v.getContext().startActivity(intent);
    }
}
