package me.sohair.demotask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView
import kotlinx.android.synthetic.main.grid_layout_list_item.view.*
import me.sohair.demotask.R
import me.sohair.demotask.model.Categories
import me.sohair.demotask.util.MySingleton

class CategoriesAdapter(var context: Context, var arrayList: ArrayList<Categories>):
    RecyclerView.Adapter<CategoriesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_layout_list_item, parent, false)
         return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val categories: Categories = arrayList[position]

        holder.icons.setDefaultImageResId(R.drawable.caterer)
        holder.icons.setImageUrl(categories.catUrl, MySingleton.getInstance(context).imageLoader)

        holder.title.text = categories.catName

        holder.icons.setOnClickListener{
            Toast.makeText(context, categories.catName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var icons: NetworkImageView = itemView.categoryIcons_ImageView
        var title: TextView = itemView.catName_textView
        
    }
}
