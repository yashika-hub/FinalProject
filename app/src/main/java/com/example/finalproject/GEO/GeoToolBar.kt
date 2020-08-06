package com.example.finalproject.geo

import android.content.Intent
import android.net.Uri
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R


open class GeoToolBar: AppCompatActivity(){
    /**
     * @return Boolean
     * inflate the menu into layout
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.geo_toolbar_menu,menu)
        return true
    }
    /**
     * @return Boolean
     * action when specific item is selected in toolbar
     */



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){


                        R.id.tbInstruction -> {
                val alertDialogBuilder = AlertDialog.Builder(this)
               alertDialogBuilder.setTitle("Input the lattitude and longititude. After that click Search button, the destination will appear. If U want save this destination, U can click on the save icon to memorize.")
                alertDialogBuilder.setNegativeButton("Exit", null)
                alertDialogBuilder.create().show()
            }
            R.id.APItn ->{
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW

                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse("https://www.geodatasource.com/web-service")
                startActivity(intent)
            }
                        R.id.tbDonate -> {
                val alertDialogBuilder1 = AlertDialog.Builder(this)
                alertDialogBuilder1.setTitle("Give me generously $$$")
                alertDialogBuilder1.setMessage("How much money do you want to donate?")
                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT
                alertDialogBuilder1.setView(input)

                // add the buttons
                alertDialogBuilder1.setPositiveButton("Thank you", null)
                alertDialogBuilder1.setNegativeButton("Cancel", null)              // create and show the alert dialog
                val dialog = alertDialogBuilder1.create()
                dialog.show()
            }

            R.id.tbABout ->{
                val about = layoutInflater.inflate(R.layout.geo_about_dialog, null)

                val builder = AlertDialog.Builder(this)
                builder.setMessage("About")
                        .setPositiveButton("OK") { dialog, which -> }
                        .setView(about)
                builder.create().show()
            }

            R.id.tbSaved ->{
                startActivity(Intent(applicationContext, SavedCities::class.java))
            }
        }
        return true
    }
}