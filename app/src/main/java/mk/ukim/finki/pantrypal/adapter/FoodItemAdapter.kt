package mk.ukim.finki.pantrypal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.pantrypal.R
import mk.ukim.finki.pantrypal.model.FoodItem

class FoodItemAdapter(private val dataList: MutableList<FoodItem>) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind("Food Item: " + item.itemName + "\nExpiration date: " + item.expirationDate.toString())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)

        fun bind(item: String) {
            itemTextView.text = item
        }
    }
}