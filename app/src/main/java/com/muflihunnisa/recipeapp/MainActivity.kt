package com.muflihunnisa.recipeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.muflihunnisa.recipeapp.adapter.RecipeAdapter
import com.muflihunnisa.recipeapp.model.Recipes
import com.muflihunnisa.recipeapp.utils.Const.COLLECTION_PATH
import com.muflihunnisa.recipeapp.utils.Const.PATH_NAME
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemReselectedListener
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    val db = FirebaseFirestore.getInstance()
    val user : MutableMap<String, Any> = HashMap()
    private lateinit var recipeAdapter : FirestoreRecyclerAdapter<Recipes, RecipeAdapter.ViewHolder>
    private val recipeCollection = db.collection(COLLECTION_PATH)
    private val recipeQuery = recipeCollection.orderBy(PATH_NAME, Query.Direction.ASCENDING)

    companion object{
        fun getLaunchService(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            fun getLaunchService(from: Context) = Intent(from, MainActivity::class.java).apply{
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        }
    }

//    private val mOnNavigationItemSelectedListener =
//            BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.nav_home -> {
//                        val fragment = HomeFragment.newInstance()
//                        implementFragment(fragment)
//                        return@OnNavigationItemSelectedListener true
//                    }
//
//                    R.id.nav_add -> {
//                        val fragment = AddFragment()
//                        implementFragment(fragment)
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    R.id.nav_save -> {
//                        val fragment = BookmarkFragment()
//                        implementFragment(fragment)
//                        return@OnNavigationItemSelectedListener true
//                    }
//                }
//                false
//            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setConfigurationUi()
        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
        setUp()
        supportActionBar?.hide()
//        nav_bottom_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        findViewById<BottomNavigationView>(R.id.nav_bottom_view)
//                .setOnNavigationItemReselectedListener{}
        val fragment = HomeFragment.newInstance()
        implementFragment(fragment)
    }
    private fun setUp() {
        val options =
                FirestoreRecyclerOptions.Builder<Recipes>().setQuery(recipeQuery, Recipes::class.java)
                        .build()

        recipeAdapter = RecipeAdapter(this, recipeCollection, options)
        recipeAdapter.notifyDataSetChanged()
        rv_main.adapter = recipeAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        nav_bottom_view.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
    private fun setConfigurationUi() {
        btn_sign_out.setOnClickListener {
            signOut()
        }
        supportActionBar?.title = "Recipe App"
        rv_main.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
    private fun signOut() {
        startActivity(LoginActivity.getLaunchService(this))
        FirebaseAuth.getInstance().signOut()
    }

    override fun onStart() {
        super.onStart()
        recipeAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        recipeAdapter.stopListening()
    }

    fun implementFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
                .commit()

    }


}