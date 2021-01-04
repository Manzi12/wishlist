package org.wit.wishlistapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_wish.*
import kotlinx.android.synthetic.main.activity_wish.wish_name
import kotlinx.android.synthetic.main.activity_wish.wish_time
import kotlinx.android.synthetic.main.activity_wish.wish_description
import kotlinx.android.synthetic.main.card_wish.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.wishlistapp.R
import org.wit.wishlistapp.helpers.readImage
import org.wit.wishlistapp.helpers.readImageFromPath
import org.wit.wishlistapp.helpers.showImagePicker
import org.wit.wishlistapp.main.MainApp
import org.wit.wishlistapp.models.WishMemStore
import org.wit.wishlistapp.models.WishModel

class WishActivity : AppCompatActivity(), AnkoLogger {

    private val db = FirebaseFirestore.getInstance()
    private val wishsDB: MutableMap<String, Any> = HashMap()
    val wishes = WishMemStore()

    private var wish = WishModel()

    private val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Wish Activity started..")

        var edit = false

        if (intent.hasExtra("wish_edit")) {
            edit = true
            wish = intent.extras?.getParcelable("wish_edit")!!
            wish_name.setText(wish.name)
            wish_description.setText(wish.description)
            wish_image.setImageBitmap(readImageFromPath(this, wish.image))
            this.chooseImage.setText(R.string.change_wish_image)
            btnAdd.setText(R.string.save_wish)
        }

        btnAdd.setOnClickListener {
            wish.name = wish_name.text.toString()
            wish.time = wish_time.text.toString()
            wish.description = wish_description.text.toString()
            if (wish.name.isEmpty()) {
                toast(R.string.enter_wish_name)
            } else {
                if (edit) {
                    wishes.update(wish.copy())
                } else {
                    wishes.create(wish.copy())
                }
            }

            saveInFireStore(wish.name,wish.time,wish.description,wish.image)

            info("add Button Pressed: $wish")
            setResult(RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }


    private fun saveInFireStore(title: String, time: String, description: String, image: String){
        wishsDB["name"] = title
        wishsDB["time"] = time
        wishsDB["description"]=description
        wishsDB["image"]=image

        db.collection("wishsDB")
                .add(wishsDB)
                .addOnSuccessListener {
                    Toast.makeText(
                            this@WishActivity,
                            "added successfully",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener{
                    Toast.makeText(
                            this@WishActivity,
                            "Failed to added",
                            Toast.LENGTH_SHORT
                    ).show()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wish, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            this.IMAGE_REQUEST -> {
                if (data != null) {
                    wish.image = data.data.toString()
                    wish_image.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_wish_image)
                }
            }
        }
    }
}