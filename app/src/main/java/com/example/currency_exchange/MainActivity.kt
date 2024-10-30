package com.example.currency_exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstConversion: EditText
    private lateinit var etSecondConversion: EditText
    private lateinit var spinnerFirstConversion: Spinner
    private lateinit var spinnerSecondConversion: Spinner

    // Sample currency conversion rates
    private val currencyRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.75,
        "VND" to 25355.0,
        "JPY" to 110.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // views binding
        etFirstConversion = findViewById(R.id.et_firstConversion)
        etSecondConversion = findViewById(R.id.et_secondConversion)
        spinnerFirstConversion = findViewById(R.id.spinner_firstConversion)
        spinnerSecondConversion = findViewById(R.id.spinner_secondConversion)

        // Set up the spinner adapters with currency options
        val currencyList = currencyRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFirstConversion.adapter = adapter
        spinnerSecondConversion.adapter = adapter

        // Set default selection for spinners
        spinnerFirstConversion.setSelection(currencyList.indexOf("USD"))
        spinnerSecondConversion.setSelection(currencyList.indexOf("EUR"))

        // Add listener for when user changes input in the first conversion EditText
        etFirstConversion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateConversion()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Add listeners for spinners to update the conversion when currency changes
        spinnerFirstConversion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                calculateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerSecondConversion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                calculateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun calculateConversion() {
        val inputText = etFirstConversion.text.toString()
        if (inputText.isNotEmpty()) {
            val inputAmount = inputText.toDoubleOrNull() ?: return

            val fromCurrency = spinnerFirstConversion.selectedItem.toString()
            val toCurrency = spinnerSecondConversion.selectedItem.toString()

            val fromRate = currencyRates[fromCurrency] ?: 1.0
            val toRate = currencyRates[toCurrency] ?: 1.0

            val result = inputAmount * (toRate / fromRate)
            val formattedResult = DecimalFormat("#.##").format(result)
            etSecondConversion.setText(formattedResult)
        } else {
            etSecondConversion.text.clear()
        }
    }
}
