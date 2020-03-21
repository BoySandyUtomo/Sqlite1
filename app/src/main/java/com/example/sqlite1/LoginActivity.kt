package com.example.sqlite1
//Nama : Boy Sandy Utomo
//NIM  : 182140714111021
//Kelas: TI4A
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqlite1.R
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //merupakan fungsi dari tombol login
        btnLogin.setOnClickListener {
            val u_username = logUsername!!.text.toString()
            val u_pass = logPassword!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            if (u_username.trim() != "" && u_pass.trim() != ""){

                //disini di lakukan validasi atau pengecekan username dan password terdapat 3 kodisi
                //yaitu password salah, username salah, dan password dan username kosong
                if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' })) {

                    if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' },
                            u_pass.trim { it <= ' ' })) {
                        val inte = Intent(this, MainActivity::class.java)
                        inte.putExtra("USERNAME", u_username.trim { it <= ' ' })
                        logUsername.setText(null)
                        logPassword.setText(null)
                        startActivity(inte)
                    } else {
                        Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_LONG).show()
                    }

                } else{
                    Toast.makeText(applicationContext, "Username salah", Toast.LENGTH_LONG).show()
                }

            } else{
                Toast.makeText(applicationContext,"username dan password kosong", Toast.LENGTH_LONG).show()
            }


        }

        btnToRegister.setOnClickListener {
            val inte = Intent(this, RegisterActivity::class.java)
            startActivity(inte)
        }
    }
}
