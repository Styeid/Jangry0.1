package com.jufaja.jangry01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jufaja.jangry01.models.User
import kotlinx.android.synthetic.main.activity_create.*

private const val TAG = "CreateActivity"
private const val PICK_PHOTO_CODE = 1234
class CreateActivity : AppCompatActivity() {
    private var signInUser: User? = null
    private var photoUri: Uri? = null
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "Ingelogd Persoon: $signInUser")

            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Gegevens ophalen $signInUser mislukt", exception)
            }

        btnPickImage.setOnClickListener {
            Log.i(TAG, "Open foto's op telefoon")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if (imagePickerIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
            }
        }

        btnSubmit.setOnClickListener {
            handelSubmitButtonClick()
        }

    }

    private fun handelSubmitButtonClick() {
        if (photoUri == null) {
            Toast.makeText(this, "Geen foto gekozen", Toast.LENGTH_SHORT).show()
            return
        }
        if (etDescription.text.isBlank()) {
            Toast.makeText(this, "Geen omschrijving, verzin een LEUKE omschrijving", Toast.LENGTH_SHORT).show()
            return
        }
        if (signInUser == null) {
            Toast.makeText(this, "Geen ingelogde user, Wachten A.U.B.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                imageView.setImageURI(photoUri)
            } else {
                Toast.makeText(this, "Openen foto's is gecanceld", Toast.LENGTH_SHORT).show()
            }
        }

    }
}