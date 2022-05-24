package org.d3if1136.kursusutbk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if1136.kursusutbk.R
import org.d3if1136.kursusutbk.databinding.FragmentFasilitasBinding
import org.d3if1136.kursusutbk.model.KategoriUtbk

class FasilitasFragment : Fragment() {
    private lateinit var binding: FragmentFasilitasBinding
    private val args: FasilitasFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFasilitasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriUtbk) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriUtbk.INTENSIF -> {
                actionBar?.title = getString(R.string.intensif)
                binding.imageView.setImageResource(R.drawable.intensif)
                binding.textView.text = getString(R.string.fasilitas_intensif)
            }
            KategoriUtbk.REGULER -> {
                actionBar?.title = getString(R.string.reguler)
                binding.imageView.setImageResource(R.drawable.reguler)
                binding.textView.text = getString(R.string.fasilitas_reguler)
            }
            KategoriUtbk.BOOTCAMP -> {
                actionBar?.title = getString(R.string.bootcamp)
                binding.imageView.setImageResource(R.drawable.bootcamp)
                binding.textView.text = getString(R.string.fasilitas_bootcamp)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { updateUI(args.kategori) }
}