package com.standuptracker.ui.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.Contacts
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.standuptracker.R
import com.standuptracker.dto.Note
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

import com.standuptracker.dto.Photo


//import android.widget.Toast.makeText as Toast.makeText


class HomeFragment : Fragment() {
    private val SAVE_IMAGE_REQUEST_CODE: Int = 2001    
    private val CAMERA_REQUEST_CODE: Int = 1998
    private val CAMERA_PERMISSION_REQUEST_CODE = 1997
    private val AUTH_REQUEST_CODE = 2002
    private var user: FirebaseUser? = null
    private lateinit var note: Note

    private val firestore = Firebase.firestore

    private lateinit var homeViewModel: HomeViewModel
   // private lateinit var applicationViewModel: HomeViewModel
    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnLogon.setOnClickListener {
            logon()
        }

        btnTakePhoto.setOnClickListener {
            prepTakePhoto()
        }

        homeViewModel.notes.observe(this, Observer {
                notes -> spnNotes.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, notes))
        })

        btnSave.setOnClickListener {
            saveNote()
        }



        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        txtDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                activity?.let {
                    DatePickerDialog(
                        it, dateSetListener, // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
                }
            }
        })



        spnNotes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            /**
             * Callback method to be invoked when the selection disappears from this
             * view. The selection can disappear for instance when touch is activated
             * or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            /**
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                note = parent?.getItemAtPosition(position) as Note
                // use this specimen object to populate our UI fields
                txtNote.setText(note.content)
                txtDate.setText(note.dateCreated)

                txtNoteID.setText(note.noteId)

                homeViewModel.note = note


            }


        }
    }




    //function that is called back on external intent
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (resultCode == RESULT_OK) {
             if (requestCode == AUTH_REQUEST_CODE) {
                 user = FirebaseAuth.getInstance().currentUser
                 Toast.makeText(activity!!,"You have been successfully logged in.",Toast.LENGTH_LONG).show()
             }

             
             if (requestCode == CAMERA_REQUEST_CODE) {
                 val imageBitmap = data!!.extras!!.get("data") as Bitmap
                 imageView2.setImageBitmap(imageBitmap)
            }else if (requestCode == SAVE_IMAGE_REQUEST_CODE) {
                 Toast.makeText(context, "Image Saved", Toast.LENGTH_LONG).show()

               //  var photo = Photo(localUri = photoURI.toString())

             }
         }
     }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDate!!.text = sdf.format(cal.getTime())
        homeViewModel.filterNotes(txtDate.text.toString())
    }

    private fun logon() {
        var providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(), AUTH_REQUEST_CODE
        )
    }


    /**
     * See if we have permission or not.
     */
    private fun prepTakePhoto() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }



    internal fun saveNote() {
        storeNote()

        homeViewModel.save(note)

        note = Note()

    }

    /** TODO: implement this functionality */
    /*  private fun searchNotes() {
          val intent = Intent(activity!!, SearchNotes::class.java)
          startActivity(intent)
      }*/
    internal fun storeNote() {

        note.apply {

            content = txtNote.text.toString()
            dateCreated = txtDate.text.toString()

            noteId = txtNoteID.text.toString()

        }
        homeViewModel.note=note
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    takePhoto()
                } else {
                    //Toast is Android's built in modal messages
                    Toast.makeText(
                        context,
                        "Unable to take photo without permission",
                        Toast.LENGTH_LONG
                    ).show()



                }
            }
        }
    }

 }
