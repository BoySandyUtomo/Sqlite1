package com.example.sqlite1
//Nama : Boy Sandy Utomo
//NIM  : 183140714111021
//Kelas: TI4A

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite1.`object`.EmpModelClass
import com.example.sqlite1.helper.MyAdapter
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import kotlinx.android.synthetic.main.update_dialog.*
import java.sql.Types.NULL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentObject = intent
        val username = intentObject.getStringExtra("USERNAME")
        usernameanda.text = "Haii $username"

        viewRecord()
    }

    fun saveRecord(view: View){
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        val alamat = u_alamat.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(name.trim()!="" && email.trim()!=""){
            val status = databaseHandler.addEmployee(EmpModelClass(NULL, name, email, alamat))
            if(status > -1){
                Toast.makeText(applicationContext,"saved",Toast.LENGTH_LONG).show()
                u_name.text.clear()
                u_email.text.clear()
                u_alamat.text.clear()
                viewRecord()
            }
        }else{
            Toast.makeText(applicationContext,"data kosong",Toast.LENGTH_LONG).show()
        }

    }
    // fungsi untuk membaca data dari database dan menampilkannya dari listview
    fun viewRecord(){
        // membuat instanisasi databasehandler
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)

        // memamnggil fungsi viewemployee dari databsehandler untuk mengambil data
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        val empArrayAlamat = Array<String>(emp.size){"null"}
        var index = 0

        // setiap data yang didapatkan dari database akan dimasukkan ke array
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            empArrayAlamat[index] = e.userAlamat
            index++
        }

        // membuat customadapter untuk view UI
        val myListAdapter = MyAdapter(this,empArrayId,empArrayName,empArrayEmail,empArrayAlamat)
        listView.adapter = myListAdapter
    }

    // fungsi untuk memperbarui data sesuai id user
    fun updateRecord(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText
        val edtAlamat = dialogView.findViewById(R.id.updateAlamat) as EditText

        dialogBuilder.setTitle("Update ID")
        dialogBuilder.setMessage("Isi Data di Bawah")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            val updateAlamat = edtAlamat.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){

                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateEmail, updateAlamat))
                if(status > -1){
                    Toast.makeText(applicationContext,"Data Updated",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"data kosong",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            // tidak melakukan apa2 :)
        })
        val b = dialogBuilder.create()
        b.show()
    }

    // fungsi untuk menghapus data berdasarkan id
    fun deleteRecord(view: View){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Hapus data")
        dialogBuilder.setMessage("Masukkan id yang akan dihapus")
        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(deleteId.trim()!=""){

                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"","", ""))
                if(status > -1){
                    Toast.makeText(applicationContext,"data terhapus",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"id kosong",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            // tidak melakukan apa2 :)
        })
        val b = dialogBuilder.create()
        b.show()
    }

    fun deleteAllRecord(view: View) {


        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            val status = databaseHandler.deleteAllEmployee()
                 if(status > -1){
                    Toast.makeText(applicationContext,"delete",Toast.LENGTH_LONG).show()
                    viewRecord()
        }

    }
}
