package com.jufaja.jangry01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "ProfilActivity"
class ProfilActivity : PostActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profil, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            Log.i(TAG, "Gebruiker wil uitloggen")
            FirebaseAuth.getInstance().signOut()
            val intent = (Intent(this, LoginActivity::class.java))
            startActivity(intent)


        }
        return super.onOptionsItemSelected(item)
    }
}