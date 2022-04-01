package org.d3if1136.kursusutbk

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if1136.kursusutbk.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this, R.array.study_groups, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.utbkSpinner.adapter = adapter

        binding.utbkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_study_group_saintek,
                        android.R.layout.simple_spinner_item
                    )
                    binding.utbkTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
                if (p2 == 2) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_study_group_soshum,
                        android.R.layout.simple_spinner_item
                    )
                    binding.utbkTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
                if (p2 == 3) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_study_group_campuran,
                        android.R.layout.simple_spinner_item
                    )
                    binding.utbkTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.examSpinner.adapter = adapterChild
                    binding.examGroupTextView.text = ""
                }
                binding.buttonHitung.setOnClickListener{ hitungKursus() }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun hitungKursus(){
        val name = binding.namaLengkapInp.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val utbk = binding.utbkSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(utbk)) {
            Toast.makeText(this, R.string.utbk_name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val exam = binding.examSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(exam)) {
            Toast.makeText(this, R.string.study_group_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val time = binding.waktuKursusInp.text.toString()
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, R.string.time_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val timeHours = time.toInt()
        timeHours.rangeTo(7)
        when (binding.utbkSpinner.selectedItem.toString()) {
            "Saintek" -> {
                val priceSaintek = 4 * timeHours * getString(R.string.biaya_saintek).toDouble()
                binding.paymentTextView.text = formatRupiah(priceSaintek)
                binding.message.text = getString(R.string.short_message)
            }
            "Soshum" -> {
                val priceSoshum = 4 * timeHours * getString(R.string.biaya_soshum).toDouble()
                binding.paymentTextView.text = formatRupiah(priceSoshum)
                binding.message.text = getString(R.string.short_message)
            }
            else -> {
                val priceCampuran = 4 * timeHours * getString(R.string.biaya_campuran).toDouble()
                binding.paymentTextView.text = formatRupiah(priceCampuran)
                binding.message.text = getString(R.string.short_message)
            }
        }
    }

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }
}

