package org.wit.wishlistapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_wish.*
import kotlinx.android.synthetic.main.card_wish.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.wishlistapp.R
import org.wit.wishlistapp.helpers.readImage
import org.wit.wishlistapp.helpers.readImageFromPath
import org.wit.wishlistapp.helpers.showImagePicker
import org.wit.wishlistapp.main.MainApp
import org.wit.wishlistapp.models.WishModel

class WishActivity : AppCompatActivity(), AnkoLogger {

    var db = FirebaseFirestore.getInstance()
    val wishDB: MutableMap<String, Any> = HashMap()

    var wish = WishModel()
    val IMAGE_REQUEST = 1
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish)
        tb_toolbar.title = title
        setSupportActionBar(tb_toolbar)
        info("Wishies Activity started..")

        app = application as MainApp
        var edit = false

        if (intent.hasExtra("wish_edit")){
            edit = true
            wish = intent.extras?.getParcelable<WishModel>("wish_edit")!!
            et_wish_name.setText(wish.name)
            et_wish_time.setText(wish.time)
            et_wish_describe.setText(wish.description)
            iv_wish_image.setImageBitmap(readImageFromPath(this,wish.image))
            if(wish.image != null){
                chooseImage.setText("Change Wish Image")
            }
            btnAdd
        }

        btnAdd.setOnClickListener(){
            wish.name = et_wish_name.text.toString()
            wish.time = et_wish_time.text.toString()
            wish.description = et_wish_describe.text.toString()
            if(wish.name.isEmpty()){
                toast("Please enter a wish name")
            } else{
                if(edit){
                    app.wishies.update(wish.copy())
                } else {
                    app.wishies.create(wish.copy())
                }
            }

            saveInFirestore(wish.name,wish.time,wish.description,wish.image)

            info("Add button Pressed: $et_wish_name")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this,IMAGE_REQUEST)
        }
    }

    fun saveInFirestore(name: String, time: String, description: String, image: String){
        wishDB["name" ]= name
        wishDB["time"] = time
        wishDB["description"] = description
        wishDB["image"] = image

        db.collection("wishDB")
                .add(wishDB)
                .addOnSuccessListener {
                    Toast.makeText(this@WishActivity, "added Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this@WishActivity, "Failed to add", Toast.LENGTH_SHORT).show()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wish,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null){
                    wish.image = data.getData().toString()
                    iv_image_wish.setImageBitmap(readImage(this,resultCode,data))
                    chooseImage.setText("Change placemark image")
                }
            }
        }
    }

}