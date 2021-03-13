package com.example.newsshow.ui.news

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsshow.R
import com.example.newsshow.logic.dao.AccountDatabase
import kotlinx.android.synthetic.main.activity_registered.*

class RegisteredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered)
        val dbHelper = AccountDatabase(this,"Account.db",1)
        registeredButton.setOnClickListener {
            val db = dbHelper.writableDatabase
            val accountUser1 = ContentValues().apply {
                put("userName", userNameEdit.text.toString())
                put("account", accountEdit.text.toString())
                put("password", passwordEdit.text.toString())
            }
            db.insert("Account",null,accountUser1)
            Toast.makeText(this,"注册成功", Toast.LENGTH_SHORT).show()

            val editor = getSharedPreferences("account", Context.MODE_PRIVATE).edit()
            editor.putString("userName", userNameEdit.text.toString())
            editor.putString("account", accountEdit.text.toString())
            editor.putString("password", passwordEdit.text.toString())
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}