package me.sohair.demotask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_layout_list_item.view.*
import me.sohair.demotask.R
import me.sohair.demotask.model.Categories

class CategoriesAdapter(var context: Context, var arrayList: ArrayList<Categories>):
    RecyclerView.Adapter<CategoriesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_layout_list_item, parent, false)
         return ItemHolder(itemHolder, context)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val categories: Categories = arrayList[position]
        val imageURL: String? = categories.catUrl
        holder.title.text = categories.catName

        Picasso.get()
            .load(imageURL)
            .placeholder(R.drawable.caterer)
            .fit()
            .into(holder.icons)

        holder.icons.setOnClickListener {
            Toast.makeText(context,"Clicked on ${categories.catName}", Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View, ctx:Context): RecyclerView.ViewHolder(itemView){
        var icons: ImageView = itemView.categoryIcons_ImageView
        var title: TextView = itemView.catName_textView
        val context = ctx

    }
}
