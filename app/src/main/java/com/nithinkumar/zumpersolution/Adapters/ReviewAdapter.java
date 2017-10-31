package com.nithinkumar.zumpersolution.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nithinkumar.zumpersolution.Activities.ListScreenActivity;
import com.nithinkumar.zumpersolution.Model.Review;
import com.nithinkumar.zumpersolution.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Nithin on 10/31/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewsViewHolder> {

    List<Review> reviews;
    private Context context;

   public  ReviewAdapter(Context context, List<Review> reviewList)
    {
        this.context = context;
        this.reviews = reviewList;

    }
    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_item_view, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {

       Review review = reviews.get(position);
       holder.bindingData(review);

    }

    public void setData(List<Review> reviewList){
       this.reviews = reviewList;
       notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView reviewDesc;
        TextView time;
        RatingBar reviewRating;
        Review reviewData;

        public ReviewsViewHolder(View itemView) {
            super(itemView);

            author = (TextView)itemView.findViewById(R.id.review_author);
            reviewDesc =  (TextView)itemView.findViewById(R.id.review_description);
            time = (TextView) itemView.findViewById(R.id.review_time);
            reviewRating = (RatingBar) itemView.findViewById(R.id.review_rating);
        }

        public void bindingData(Review review) {
            reviewData = review;
            author.setText(reviewData.getAuthor());
            reviewDesc.setText(reviewData.getReviewText());
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateFormatted = formatter.format(review.getTime());
            if(time!=null)time.setText(dateFormatted);
            reviewRating.setRating((float) reviewData.getRating());
        }
    }
}
