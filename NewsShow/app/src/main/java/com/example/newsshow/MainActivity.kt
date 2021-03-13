 package com.example.newsshow

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsshow.ui.news.NewsFragment
import com.example.newsshow.ui.news.NewsViewModel
import com.example.newsshow.ui.news.PagerAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registered.*
import kotlinx.android.synthetic.main.arc_nac_header.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val newsTypes = listOf("shehui","guonei","guoji","yule")
    private val tabTitle = listOf("社会","国内","国际","娱乐")
    private val fragments = ArrayList<NewsFragment>()

    private val takePhoto=1
    private val fromAlbum=2
    private lateinit var imageUri:Uri
    private lateinit var outputImage:File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        for (newsType in newsTypes) {
            fragments.add(NewsFragment(newsType))
        }
        viewPager.adapter = PagerAdapter(supportFragmentManager,fragments,tabTitle)
        tabLayout.setupWithViewPager(viewPager)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        val userName = intent.getStringExtra("userName")
        val userAccount = intent.getStringExtra("userAccount")
        arc_nav.setNavigationItemSelectedListener {
           when(it.itemId) {
               R.id.browsingRecords -> {
                   AlertDialog.Builder(this).apply {
                       setTitle("选择更换头像方式")
                       setCancelable(false)
                       setPositiveButton("相机") {
                               dialog, which ->takePhoto()

                       }

                       setNegativeButton("相册") {
                               dialog,which ->fromAlbum()
                       }
                       show()
                   }
               }

           }

            true
        }
        runOnUiThread {
            try {
                arc_user_name.text = userName
                arc_user_account.text = userAccount
            }catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                userAvatar.setImageBitmap(bitmap)
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {
                            uri ->
                        val bitmap = contentResolver.openFileDescriptor(uri,"r")?.use {
                            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                        }
                        userAvatar.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }


    private fun takePhoto() {
        outputImage= File(externalCacheDir,"output_image.jpg")
        if (outputImage.exists()){
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri=if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this@MainActivity,"com.example.newsshow.FileProvider",outputImage)
        }else{
            Uri.fromFile(outputImage)
        }
        val intent= Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent,takePhoto)
    }

    private fun fromAlbum() {
        val intent =Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type="image/*"
        startActivityForResult(intent,fromAlbum)
    }

}