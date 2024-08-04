package com.hank.dev

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hank.dev.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var viewModel: MyViewModel
    private val TAG: String? = MainActivity::class.java.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val job = Job() + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //JSON
        viewModel.readJSON()
        //names
        val names = listOf<String>(
            "Aaren", "Abbe", "Adele", "Carlyn", "Carol", "Cassy",
            "Claudis", "Dale", "Debra", "Ellen", "Gilberta", "Hallie",
            "Isabelle", "Jacklyn", "Jaimie", "Jenifer", "Kaitlin", "Kaja"
        )
        //RecycleView
        val recyler = binding.contentView.recycler
        recyler.setHasFixedSize(true)
        recyler.layoutManager = LinearLayoutManager(this)
        viewModel.words.observe(this) { words ->
            recyler.adapter = WordAdapter(words)
        }

    }

    private fun parseJSON(json: String) {
        val jsonObject = JSONObject(json)
        val array = jsonObject.getJSONArray("words")
        for (i in 0..(array.length() - 1)) {
            val w = array.getJSONObject(i)
            val name = w.getString("name")
            val means = w.getString("means")
            Log.d(TAG, "onCreate: ${name} : ${means}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job


}