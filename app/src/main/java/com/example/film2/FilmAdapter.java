package com.example.film2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<com.example.film2.Movie> movies;
    private RecyclerViewClickListener mListener;

    public FilmAdapter(Context context, List<Movie> movies, RecyclerViewClickListener mListener) {
        this.context = context;
        this.movies = movies;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new RecyclerViewAdapter(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        holder.tv_title.setText(movies.get(position).getTitle());
        holder.tv_price.setText(movies.get(position).getPrice());
        holder.tv_description.setText(movies.get(position).getDescription());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(movies.get(position).getImage())
                .apply(requestOptions)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_title, tv_price, tv_description;
        private RelativeLayout relativeLayout;
        private RecyclerViewClickListener itemClickListener;
        private RecyclerViewClickListener mListener;
        private RelativeLayout mRowContainer;
        private ImageView mImage;

        public RecyclerViewAdapter(View view, RecyclerViewClickListener listener){
            super(view);
            mImage=view.findViewById(R.id.list_image);
            tv_title = view.findViewById(R.id.title);
            tv_price=view.findViewById(R.id.price);
            tv_description=view.findViewById(R.id.description);
            relativeLayout=view.findViewById(R.id.relative_layout);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onRowClick(mRowContainer, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);

    }

}
