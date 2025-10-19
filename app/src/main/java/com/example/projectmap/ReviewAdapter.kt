package com.example.projectmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReviewerName: TextView = itemView.findViewById(R.id.tv_reviewer_name)
        val tvProductName: TextView = itemView.findViewById(R.id.tv_review_product_name)
        val rbRating: RatingBar = itemView.findViewById(R.id.rb_review_rating)
        val tvReviewText: TextView = itemView.findViewById(R.id.tv_review_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.tvReviewerName.text = review.reviewerName
        holder.tvProductName.text = "Produk: ${review.productName}"
        holder.rbRating.rating = review.rating
        holder.tvReviewText.text = review.reviewText
    }

    override fun getItemCount() = reviewList.size
}