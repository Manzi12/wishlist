package org.wit.wishlistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_wish.*
import kotlinx.android.synthetic.main.activity_wish_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.wishlistapp.activites.WishActivity
import org.wit.wishlistapp.activites.WishAdapter
import org.wit.wishlistapp.activites.WishListener
import org.wit.wishlistapp.main.MainApp
import org.wit.wishlistapp.models.WishModel

class WishListActivity : AppCompatActivity(), WishListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        app = application as MainApp
        tb_toolbar.title = title
        setSupportActionBar(tb_toolbar)

        val layoutManager = LinearLayoutManager(this)
        rv_recyclerView_wish.layoutManager = layoutManager
        rv_recyclerView_wish.adapter = WishAdapter(app.wishies.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_add -> startActivityForResult<WishActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWishClick(wish: WishModel){
        startActivityForResult(intentFor<WishActivity>().putExtra("wish_edit",wish),0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        rv_recyclerView_wish.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}