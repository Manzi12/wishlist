package org.wit.wishlistapp.activites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_wish.view.*
import kotlinx.android.synthetic.main.card_wish.view.*
import org.wit.wishlistapp.R
import org.wit.wishlistapp.helpers.readImageFromPath
import org.wit.wishlistapp.models.WishModel

interface WishListener {
    fun onWishClick(wish: WishModel)
}

class WishAdapter constructor(private var wishies: List<WishModel>,
                              private val listener: WishListener) : RecyclerView.Adapter<WishAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_wish, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val wish = wishies[holder.adapterPosition]
        holder.bind(wish, listener)
    }

    override fun getItemCount(): Int = wishies.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wish: WishModel, listener: WishListener) {
            itemView.tv_wish_name.text = wish.name
            itemView.tv_wish_time.text = wish.time
            itemView.tv_wish_description.text = wish.description
            itemView.iv_image_wish.setImageBitmap(readImageFromPath(itemView.context, wish.image))
            itemView.setOnClickListener { listener.onWishClick(wish) }
        }
    }
}
