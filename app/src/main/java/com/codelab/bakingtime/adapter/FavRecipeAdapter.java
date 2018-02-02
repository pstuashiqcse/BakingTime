package com.codelab.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.RecipeModel;

import java.util.ArrayList;

public class FavRecipeAdapter extends RecyclerView.Adapter<FavRecipeAdapter.ViewHolder> {

    private final ArrayList<RecipeModel> arrayList;
    private ItemClickListener itemClickListener;
    private Context context;

    public FavRecipeAdapter(Context context, ArrayList<RecipeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvTitle;
        private final RadioButton rbMarker;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            rbMarker = (RadioButton) itemView.findViewById(R.id.rb_marker);

            rbMarker.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_recipe, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvTitle.setText(arrayList.get(position).getName());
        if(arrayList.get(position).isSelected()) {
            holder.rbMarker.setChecked(true);
        } else {
            holder.rbMarker.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


}
