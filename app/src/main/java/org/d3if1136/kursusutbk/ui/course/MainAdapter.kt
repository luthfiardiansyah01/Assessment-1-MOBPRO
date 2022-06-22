package org.d3if1136.kursusutbk.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if1136.kursusutbk.R
import org.d3if1136.kursusutbk.databinding.ListItemBinding
import org.d3if1136.kursusutbk.model.Kursus
import org.d3if1136.kursusutbk.network.KursusApi

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val data = mutableListOf<Kursus>()

    fun updateData(newData: List<Kursus>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(kursus: Kursus) = with(binding) {
            namaTextView.text = kursus.nama
            latinTextView.text = kursus.jenis
//            imageView.setImageResource(hewan.imageResId)
            Glide.with(imageView.context)
                .load(KursusApi.getKursusUrl(kursus.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)

            root.setOnClickListener {
                val message = root.context.getString(R.string.message, kursus.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}