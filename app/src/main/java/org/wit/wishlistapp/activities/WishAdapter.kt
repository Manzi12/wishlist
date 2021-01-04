package org.wit.wishlistapp.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_wish.view.*
import kotlinx.android.synthetic.main.card_wish.view.wish_name
import kotlinx.android.synthetic.main.card_wish.view.wish_time
import org.wit.wishlistapp.R
import org.wit.wishlistapp.helpers.readImageFromPath
import org.wit.wishlistapp.models.WishModel

interface WishListener {
    fun onPlacemarkClick(wish: WishModel)
}

class WishAdapter constructor(private var wishes: List<WishModel>,
                              private val listener: WishListener) : RecyclerView.Adapter<WishAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_wish, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val wish = wishes[holder.adapterPosition]
        holder.bind(wish, listener)
    }

    override fun getItemCount(): Int = wishes.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(wish: WishModel, listener: WishListener) {
            itemView.wish_name.text = wish.name
            itemView.wish_time.text = wish.time
            itemView.wish_description.text = wish.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, wish.image))
            itemView.setOnClickListener { listener.onPlacemarkClick(wish) }
        }
    }
}