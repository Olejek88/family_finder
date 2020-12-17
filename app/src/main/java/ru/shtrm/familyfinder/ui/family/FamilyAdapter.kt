package ru.shtrm.familyfinder.ui.family

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.interfaces.OnRecyclerViewItemClickListener
import ru.shtrm.familyfinder.util.FileUtils
import java.text.SimpleDateFormat
import java.util.*

class FamilyAdapter internal constructor(private val context: Context, private val list: MutableList<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater

    private var listener: OnRecyclerViewItemClickListener? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(inflater.inflate(R.layout.family_user_item_row,
                parent, false), listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        val pvh = holder as UserViewHolder
        val sDate = SimpleDateFormat("dd.MM.yy HH:mm", Locale.US).format(item.changedAt)
        pvh.textViewTime.setText(sDate)
        pvh.textViewName.setText(item.username)
        pvh.textViewName.setTypeface(null, Typeface.BOLD)
        pvh.textViewDistance.setText(item.location)
        if (item.image!=null && item.image != "") {
            val path = FileUtils.getPicturesDirectory(this.context)
            val userBitmap: Bitmap? = FileUtils.getBitmapByPath(path, item.image)
            if (userBitmap!=null) {
                pvh.circleImageView.setImageBitmap(FileUtils.getBitmapByPath(path, item.image))
            } else {
                pvh.textViewImageText.text = item.username.substring(0, 1)
            }
        } else {
            pvh.textViewImageText.text = item.username.substring(0, 1)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnRecyclerViewItemClickListener(listener: OnRecyclerViewItemClickListener) {
        this.listener = listener
    }

    /**
     * Update the data. Keep the data is the latest.
     * @param list The data.
     */
    fun updateData(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View, private val listener: OnRecyclerViewItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnCreateContextMenuListener {
        internal var textViewName: TextView
        internal var textViewDistance: TextView
        internal var textViewTime: TextView
        internal var textViewImageText: AppCompatTextView
        internal var circleImageView: CircleImageView

        init {
            circleImageView = itemView.findViewById(R.id.card_user_image)
            textViewName = itemView.findViewById(R.id.card_user_name)
            textViewDistance = itemView.findViewById(R.id.card_user_distance)
            textViewTime = itemView.findViewById(R.id.card_user_time)
            textViewImageText = itemView.findViewById(R.id.card_user_image_text)
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(v: View) {
            if (this.listener != null) {
                listener.onItemClick(v, layoutPosition)
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        }
    }
}
