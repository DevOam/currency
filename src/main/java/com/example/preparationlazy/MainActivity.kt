package com.example.preparationlazy

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    lateinit var  btn: Button
    lateinit var entre:TextInputEditText
    lateinit var sortie: TextInputEditText
    lateinit var country:AutoCompleteTextView
    lateinit var toCountry: AutoCompleteTextView
    lateinit var toolbar:Toolbar
    lateinit var share:Item
    //elt drop down
    private val dollar = "Dollar-Americain"
    private val dirham = "Dirham-Marocain"
    private val euro = "Euro"

     val values = mapOf(
         dollar to 1.0,
        dirham to 11.0 ,
        euro to 1.0

    )
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intializeViews()
        populateDropDown()
        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.setOnMenuItemClickListener(){menuItem->
            when (menuItem.itemId) {
                R.id.share -> {
                    Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "${entre.text.toString()} ${country.text} is equal to ${sortie.text.toString()}  ${toCountry.text}")
                    if (shareIntent.resolveActivity(packageManager)!=null){
                        startActivity(shareIntent)
                    }else{
                        Toast.makeText(this, "No activity found to handle this intent", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.contact -> {
                    Toast.makeText(this, "contact clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    Toast.makeText(this, "settings clixked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        btn.setOnClickListener {
            calcurateResult()
            country.setOnItemClickListener { adapterView, view, i, l ->
                toCountry.text.toString()
                calcurateResult()
            }
            toCountry.setOnItemClickListener{ adapterView, view, i, l ->
                calcurateResult()
            }
        }
    }

    private fun calcurateResult(){
        if (entre.text.toString().isNotEmpty()){
            val stoker = entre.text.toString().toDouble()
            val toValue = values[this.toCountry.text.toString()]
            val fromValue = values[this.country.text.toString()]
            val resulta = stoker.times(toValue!!.div(fromValue!!))
            val formatedResult = String.format("%.2f", resulta)
            sortie.setText(formatedResult.toString())
        }else{
            entre.setError("enter amount")
            var snackbar = Snackbar.make(entre, "value is empty!!", Snackbar.LENGTH_LONG)
            snackbar.show()
            snackbar.setAction("ok"){
                Toast.makeText(this, "enter amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun intializeViews(){
        btn= findViewById(R.id.btn)
        entre = findViewById(R.id.s1)
        sortie  = findViewById(R.id.s2)
        country = findViewById(R.id.listCountry)
        toCountry= findViewById(R.id.listToCountry)
        toolbar=findViewById(R.id.toolbar)
    }

    private fun populateDropDown(){
        val listCountry = listOf(dollar, dirham, euro)
        val adapter = ArrayAdapter(this, R.layout.text_view, listCountry)
        toCountry.setAdapter(adapter)
        country.setAdapter(adapter)
    }

}