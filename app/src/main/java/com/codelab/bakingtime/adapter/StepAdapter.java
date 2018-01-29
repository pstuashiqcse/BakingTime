package com.codelab.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.api.models.StepsModel;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<StepsModel> arrayList;
    private ItemClickListener itemClickListener;

    public StepAdapter(Context context, ArrayList<StepsModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvTitle.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


}
