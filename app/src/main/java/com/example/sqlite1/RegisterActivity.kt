package com.example.sqlite1
//Nama : Boy Sandy Utomo
//NIM  : 183140714111021
//Kelas: TI4A
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite1.R
import com.example.sqlite1.`object`.LogModelClass
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //merupakan fungsi dari tombol login
        btnRegister.setOnClickListener {
            val r_id = regId!!.text.toString()
            val r_username = regUsername!!.text.toString()
            val r_pass = regPassword!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            //disini di lakukan validasi atau pengecekan pembuatan akun terdapat 3 kodisi
            //yaitu succes, username sudah terpakai, dan password dan username kosong
            if (r_username != "" && r_pass != ""){
                    if (!databaseHandler!!.checkUser(r_username.trim())){

                        var user = LogModelClass(logUsername = r_username.trim(),
                                                logPassword = r_pass.trim())


                        databaseHandler!!.addUser(user)

                        Toast.makeText(applicationContext,"username berhasil dibuat!", Toast.LENGTH_LONG).show()

                        val inte = Intent(this, LoginActivity::class.java)
                        startActivity(inte)

                    } else{
                        Toast.makeText(applicationContext,"username sudah ada", Toast.LENGTH_LONG).show()
                    }
            } else{
                Toast.makeText(applicationContext,"username dan password kosongg", Toast.LENGTH_LONG).show()
            }
        }
    }
}
