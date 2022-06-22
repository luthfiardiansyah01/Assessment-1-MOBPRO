package org.d3if1136.kursusutbk.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import org.d3if1136.kursusutbk.R
import org.d3if1136.kursusutbk.data.SettingDataStore
import org.d3if1136.kursusutbk.data.dataStore
import org.d3if1136.kursusutbk.databinding.FragmentHitungBinding
import org.d3if1136.kursusutbk.db.UtbkDb
import org.d3if1136.kursusutbk.model.HasilUtbk
import org.d3if1136.kursusutbk.model.KategoriUtbk
import java.text.NumberFormat
import java.util.*


class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    private lateinit var layoutDataStore: SettingDataStore

    private val viewModel: HitungViewModel by lazy {
        val db = UtbkDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Create an ArrayAdapter
        val adapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.study_groups, android.R.layout.simple_spinner_item) }
        // Specify the layout to use when the list of choices appears
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.utbkSpinner.adapter = adapter

        binding.utbkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_study_group_saintek,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.utbkTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
                if (p2 == 2) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_study_group_soshum,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.utbkTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
                if (p2 == 3) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_study_group_campuran,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.utbkTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.buttonHitung.setOnClickListener{ hitungKursus() }
        binding.programButton.setOnClickListener {
            viewModel.mulaiNavigasi()
        }
        binding.shareButton.setOnClickListener { shareData() }
        binding.buttonKursus.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_hitungFragment_to_mainFragment)
//            viewModel.kembaliNavigasi()
        }
        viewModel.getHasilUtbk().observe(requireActivity(), { showResult(it) })
        viewModel.getNavigasi().observe(viewLifecycleOwner) {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections
                    .actionHitungFragmentToFasilitasFragment(it)
            )
            viewModel.selesaiNavigasi()
        }
//        viewModel.getBack().observe(viewLifecycleOwner) {
//            if (it == null) return@observe
//            findNavController().navigate(
//                HitungFragmentDirections.actionHitungFragmentToKursusFragment()
//            )
//            viewModel.selesaiNavigasiKursus()
//        }
        layoutDataStore = SettingDataStore(requireContext().dataStore)
        layoutDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner, {
                value -> activity?.invalidateOptionsMenu()
            }
        )
    }

    private fun shareData() {
        val utbk = binding.utbkSpinner.selectedItem.toString()
        val exam = binding.examSpinner.selectedItem.toString()
        val message = getString(R.string.bagikan_template,
            binding.namaLengkapInp.text,
            utbk,
            exam,
            binding.paymentTextView.text,
            binding.classTextView.text
        )
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

//    private fun replaceFragment(fragment: Fragment){
//        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentController, fragment)
//        fragmentTransaction.commit()
//    }

    private fun hitungKursus(){
        val name = binding.namaLengkapInp.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, R.string.name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val studyGroup = binding.utbkSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(studyGroup)) {
            Toast.makeText(context, R.string.utbk_name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val examGroup = binding.examSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(examGroup)) {
            Toast.makeText(context, R.string.study_group_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val time = binding.waktuKursusInp.text.toString()
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(context, R.string.time_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungKursus(
            time.toFloat(),
            studyGroup == R.id.utbkSpinner.toString(),
            examGroup, name, studyGroup
        )
    }

    private fun getKategoriLabel(kategori: KategoriUtbk): String {
        val stringRes = when (kategori) {
            KategoriUtbk.REGULER -> R.string.reguler
            KategoriUtbk.INTENSIF -> R.string.intensif
            KategoriUtbk.BOOTCAMP -> R.string.bootcamp
        }
        return getString(stringRes)
    }

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }

    private fun showResult(result: HasilUtbk?) {
        if (result == null) return
        binding.paymentTextView.text = formatRupiah(result.price)
        binding.message.text = getString(R.string.short_message)
        binding.classTextView.text = getString(R.string.kelas, getKategoriLabel(result.kategori))
        binding.buttonGroup.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_historiFragment
                )
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

