package com.standuptracker.ui.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.standuptracker.R
import com.standuptracker.dto.Note
import com.standuptracker.dto.Photo
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private var photo = Photo()
    private lateinit var currentPhotoPath: String
    private val SAVE_IMAGE_REQUEST_CODE: Int = 2001
    private val CAMERA_REQUEST_CODE: Int = 1998
    private val CAMERA_PERMISSION_REQUEST_CODE = 1997
    private val AUTH_REQUEST_CODE = 2002
    private lateinit var user: FirebaseUser
    private lateinit var note: Note
    private var photoURI: Uri? = null
    private val firestore = Firebase.firestore

    private lateinit var homeViewModel: HomeViewModel

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
        //when the Logon button is clicked, the function logon() is executed
        btnLogon.setOnClickListener {
            logon()
        }
        //when the Photo button is clicked, the prepTakePhoto() function is executed
        btnTakePhoto.setOnClickListener {
            prepTakePhoto()
        }

        //The HomeViewModel has an arrayList of Note objects that we want to populate the spinner, so we set an Observer on it.
        homeViewModel.notes.observe(this, Observer { notes ->
            spnNotes.setAdapter(
                ArrayAdapter(
                    context!!,
                    R.layout.support_simple_spinner_dropdown_item,
                    notes
                )
            )
        })

        //when the Save button is clicked, the saveNote() function is executed
        btnSave.setOnClickListener {
                saveNote()
        }

        //when the AddNote button is clicked
        btnAddNote.setOnClickListener {
            txtNoteID.setText("") //clear the NoteID textbox
            txtNote.setText("") //clear the Note textbox
            note = Note("", "", "", "") //set the note variable to an empty Note object
            photo = Photo("", "", "", "", "") //set the photo variable to an empty Photo object
            imageView2.setImageResource(R.drawable.ic_launcher_background) //set the ImageView to display the placeholder texture
        }
        //when the DeleteNote button is clicked, execute the confirmDeleteNote() function
        btnDeleteNote.setOnClickListener {
            confirmDeleteNote()
        }

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView() //update the textbox that displays the date.
            }

        // pop calendar open when user clicks on the date field
        txtDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                activity?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener, // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
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
                txtNoteID.setText(" ")
                txtNote.setText(" ")
                txtDate.setText(" ")
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
                // use this note object to populate our UI fields
                txtNote.setText(note.content)
                txtDate.setText(note.dateCreated)
                txtNoteID.setText(note.noteId)
                if (note.uri != null && !note.uri.isEmpty()) {
                    Picasso.get().load(note.uri).into(imageView2) //use the Picasso package to load the photo into the ImageView using the public-facing Firestore url
                } else {
                    imageView2.setImageResource(R.drawable.ic_launcher_background)
                }
                homeViewModel.note = note //set the ViewModel's note variable to the selected note from the spinner


            }


        }

    }


    private fun confirmDeleteNote() {
        if (note.noteId != null && !note.noteId.isEmpty()) {
            // updating existing
            val document = firestore.collection("notes").document(note.noteId) //get the documentReference in Firestore by the note's ID
            val deleteTask = document.delete() //delete the note in Firestore
            //when then the delete is successful, display a Toast
            deleteTask.addOnSuccessListener {
                Toast.makeText(activity!!, "You deleted a note", Toast.LENGTH_LONG).show()
            }
            //when the delete is unsuccessful, display a Toast
            deleteTask.addOnFailureListener {
                Toast.makeText(activity!!, "The note couldn't be deleted", Toast.LENGTH_LONG).show()
            }
        } else {
            //there was no ID in the textbox, so there was no note to delete.
            Toast.makeText(activity!!, "You must choose a note to delete", Toast.LENGTH_LONG).show()
        }
    }

    //function that is called back on external intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

             when(requestCode){
                 // log the user in
                 AUTH_REQUEST_CODE -> {
                     user = FirebaseAuth.getInstance().currentUser!!
                     Toast.makeText(activity!!,"You have been successfully logged in.",Toast.LENGTH_LONG).show()
                 }
                 CAMERA_REQUEST_CODE -> {}
                 // allow camera access
                 SAVE_IMAGE_REQUEST_CODE -> {
                     Toast.makeText(context, "Image Saved", Toast.LENGTH_LONG).show()
                     imageView2.setImageURI(photoURI) //display the image you just took
                     photo.localUri = photoURI.toString() //save the local URI of the photo for use to save it into Firestore

                 }
             }

        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDate!!.text = sdf.format(cal.getTime())
        homeViewModel.filterNotes(txtDate.text.toString()) //repopulate the spinner with the notes for that date.
    }

    private fun logon() {
        var providers = arrayListOf(
            //give user ability to login with email or google
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .setIsSmartLockEnabled(false) //setIsSmartLockEnabled is used to prevent a strange error that prevents users from logging in.
                .build(), AUTH_REQUEST_CODE
        )
    }


    private fun prepTakePhoto() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // if permission is granted, take the photo
            takePhoto()
        } else {
            //otherwise, request permission
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)
            if (takePictureIntent == null) {
                Toast.makeText(context, "Unable to save photo", Toast.LENGTH_LONG).show()
            } else {
                // if we are here, we have a valid intent.
                val photoFile: File = createImageFile()
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        activity!!.applicationContext,
                        "com.standuptracker.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir: File? = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("StandupTracker${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }


    private fun saveNote() {
        //the try catch block is used to catch any error in the save() function
       try{
           if (txtNote.text.isNullOrEmpty()){
               Toast.makeText(context, "Note cannot be blank", Toast.LENGTH_LONG).show()
           }else{
           storeNote()

           homeViewModel.save(note,photo,user) //pass the Note, Photo, and User to the save() function in HomeViewModel

           note = Note() //once the save() function finishes, set the fragment's note variable to an empty Note object
           }

       }catch (t: Throwable){
           print(t.message)
           if (t.message == "lateinit property user has not been initialized"){
               Toast.makeText(context, "You must log in to make changes", Toast.LENGTH_LONG).show() //the most likely failure is that the user hasn't logged in
           } else {
               Toast.makeText(context, t.localizedMessage, Toast.LENGTH_LONG).show() //show stack trace error
           }
       }


    }


    private fun storeNote() {


        note.apply {

            content = txtNote.text.toString()
            dateCreated = txtDate.text.toString()

            noteId = txtNoteID.text.toString()

        }
        homeViewModel.note = note  //save the fragment's Note object to the note variable in the HomeViewModel


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
