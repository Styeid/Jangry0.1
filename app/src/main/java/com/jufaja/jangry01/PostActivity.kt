package com.jufaja.jangry01

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jufaja.jangry01.models.Post
import com.jufaja.jangry01.models.User
import kotlinx.android.synthetic.main.activity_post.*


private const val TAG = "PostActivity"
private const val EXTRA_USERNAME = "EXTRA_USERNAME"
open class PostActivity : AppCompatActivity() {

    private var signInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: Postsadapter
    @SuppressLint("NotifyDataSetChanged")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        // create layoutfile which reprsents one post (zie item_post.xml)
        // create data source -
        posts = mutableListOf()
        // create adapter
        adapter = Postsadapter(this, posts)
        // bind the adapter and layoutManager to the recyclerview
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)
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

        var postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("datum_tijd_ms", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            supportActionBar?.title = username
            postsReference = postsReference.whereEqualTo("user.username", username)
        }

        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postlist = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postlist)
            adapter.notifyDataSetChanged()
            for (post in postlist) {
                Log.i(TAG, "Post $post")
            }
        }
        fabCreate.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }


    }
    // query to Firestore to retrieve data
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.icProfile) {
            val intent = Intent(this, ProfilActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, signInUser?.username)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)

    }
}
