import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if1136.kursusutbk.R
import org.d3if1136.kursusutbk.databinding.ItemHistoriBinding
import org.d3if1136.kursusutbk.db.UtbkEntity
import org.d3if1136.kursusutbk.model.KategoriUtbk
import org.d3if1136.kursusutbk.model.hitungUtbk
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter:
    ListAdapter<UtbkEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<UtbkEntity>() {
                override fun areItemsTheSame(
                    oldData: UtbkEntity, newData: UtbkEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: UtbkEntity, newData: UtbkEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: UtbkEntity) = with(binding) {
            val hasilUtbk = item.hitungUtbk()
            kategoriTextView.text = hasilUtbk.kategori.toString().substring(0, 1)
            val colorRes = when(hasilUtbk.kategori) {
                KategoriUtbk.BOOTCAMP -> R.color.bootcamp
                KategoriUtbk.REGULER -> R.color.reguler
                else -> R.color.intensif
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            biayaTextView.text = root.context.getString(R.string.hasil_x,
                hasilUtbk.price, hasilUtbk.kategori.toString())
            val jenisUjian = root.context.getString(
                when (hasilUtbk.kategori) {
                    KategoriUtbk.REGULER -> R.string.soshum
                    KategoriUtbk.BOOTCAMP -> R.string.campuran
                    else -> {R.string.saintek}
                }
            )
            dataTextView.text = root.context.getString(R.string.data_x, jenisUjian) }
    }
}