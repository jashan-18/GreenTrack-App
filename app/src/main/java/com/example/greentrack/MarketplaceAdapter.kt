package com.example.greentrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

data class MarketplaceItem(
    val imageResId: Int,
    val name: String,
    val description: String,
    val price: String
)

class MarketplaceAdapter(
    private val context: Context,
    private val itemList: List<MarketplaceItem>
) : RecyclerView.Adapter<MarketplaceAdapter.MarketplaceViewHolder>() {

    inner class MarketplaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val btnBuy: Button = view.findViewById(R.id.btnBuy)
        val btnContact: Button = view.findViewById(R.id.btnContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketplaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_marketplace, parent, false)
        return MarketplaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketplaceViewHolder, position: Int) {
        val item = itemList[position]

        holder.imgProduct.setImageResource(item.imageResId)
        holder.tvName.text = item.name
        holder.tvDescription.text = item.description
        holder.tvPrice.text = item.price

        holder.btnBuy.setOnClickListener {
            Toast.makeText(context, "Buying ${item.name}...", Toast.LENGTH_SHORT).show()
        }

        holder.btnContact.setOnClickListener {
            Toast.makeText(context, "Contacting seller for ${item.name}...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = itemList.size
}
