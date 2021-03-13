package com.example.newsshow.ui.news

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsshow.MainActivity
import com.example.newsshow.R
import com.example.newsshow.logic.dao.AccountDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.arc_nac_header.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = AccountDatabase(this,"Account.db",1)
        setContentView(R.layout.activity_login)
        var isRegistered = false
        loginButton.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Account", null,null,null,null,null,null)
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndex("account"))==accountEdit.text.toString()&&
                            cursor.getString(cursor.getColumnIndex("password"))==passwordEdit.text.toString()) {
                        isRegistered = true//
                        if (isRegistered) {
                            val intent = Intent(this, MainActivity::class.java).apply {
                                putExtra("userAccount",cursor.getString(cursor.getColumnIndex("account")))
                                putExtra("userName",cursor.getString(cursor.getColumnIndex("userName")))
                            }
                            startActivity(intent)
                        }
                    }
                }while (cursor.moveToNext())
            }
            cursor.close()
            if (!isRegistered) {
                Toast.makeText(this,"登陆失败，账号或密码错误",Toast.LENGTH_SHORT).show()
            }
        }

        registeredButton.setOnClickListener {
            val intent = Intent(this,RegisteredActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val accountRegistered = getSharedPreferences("account", Context.MODE_PRIVATE)
        val account = accountRegistered.getString("account","")
        val password = accountRegistered.getString("password","")

        runOnUiThread {
            accountEdit.setText(account)
            passwordEdit.setText(password)
        }
    }


}