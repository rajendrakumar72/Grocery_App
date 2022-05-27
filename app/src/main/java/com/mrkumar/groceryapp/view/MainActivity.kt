package com.mrkumar.groceryapp.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mrkumar.groceryapp.R
import com.mrkumar.groceryapp.adapter.UserAdapter
import com.mrkumar.groceryapp.databinding.ActivityMainBinding
import com.mrkumar.groceryapp.databinding.DialogLayoutBinding
import com.mrkumar.groceryapp.model.UserModel
import com.mrkumar.groceryapp.network.NetworkInterface
import com.mrkumar.groceryapp.repository.UserRepository
import com.mrkumar.groceryapp.roomDB.UserRoomDB
import com.mrkumar.groceryapp.viewmodel.MainActivityViewModel
import com.mrkumar.groceryapp.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var _viewModel: MainActivityViewModel
    lateinit var list: List<UserModel>
    lateinit var userAdapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val groceryRepository = UserRepository(UserRoomDB(this))
        val factory = ViewModelFactory(groceryRepository)

        // Initialised View Model
        _viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        //recyclerview1
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            userAdapter = UserAdapter(listOf(),_viewModel)
            adapter = userAdapter
            val divider = DividerItemDecoration(applicationContext,
                StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        // To display all items in recycler view
        _viewModel.allGroceryItems().observe(this, Observer {
            userAdapter.list = it
            userAdapter.notifyDataSetChanged()
        })

        userAdapter.onItemClick={data->updateData(data)}

    }

    private fun updateData(data:UserModel) {
        val dialog= Dialog(this)
        val bnd:DialogLayoutBinding= DialogLayoutBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(bnd.root)
        dialog.setCancelable(true)

        val layoutParams= WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT


        val edtDesc:EditText=bnd.edtUpdateDesc
        val edtTitle:EditText= bnd.edtUpdateTitle

        edtTitle.setText(data.title)
        edtDesc.setText(data.body)

        dialog.findViewById<Button>(R.id.btnUpdateCancel).setOnClickListener {
            dialog.dismiss()
        }

        bnd.btnUpdate.setOnClickListener {
            if (inputCheck(edtTitle.text.toString(),edtDesc.text.toString())){
                val notes=UserModel(data.courseID,data.userId,data.id,edtTitle.text.toString(),edtDesc.text.toString())
                _viewModel.updateData(notes)

                Toast.makeText(this,"Updated Successfully",
                    Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Please Enter All Field",
                    Toast.LENGTH_SHORT).show()
            }
        }


        dialog.show()
        dialog.window!!.attributes=layoutParams
    }

    //Check input from edittext
    private fun inputCheck(tittle: String, desc: String):Boolean{
        return !(TextUtils.isEmpty(tittle)&& TextUtils.isEmpty(desc))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
                if (item.itemId==R.id.deleteMenu){
                    val builder= AlertDialog.Builder(this)
                    builder.setTitle("Are You sure You Want Delete All Data?")
                    builder.setPositiveButton("Yes"){_,_->
                        _viewModel.deleteAll()
                        Toast.makeText(this,"Deleted",
                            Toast.LENGTH_SHORT).show()
                    }

                    builder.setNegativeButton("No") { _, _ ->

                    }
                    builder.create().show()
                }
            if(item.itemId==R.id.refreshMenu){
            val builder=AlertDialog.Builder(this)
            builder.setTitle("Are You sure You Want Refresh Data?")
            builder.setPositiveButton("Yes"){_,_ ->
                val apiInterface = NetworkInterface.create().getUserData()
                apiInterface.enqueue( object : Callback<List<UserModel>> {
                    override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {

                        if (response!!.code() == 200) {
                            _viewModel.deleteAll()
                            if (response.body() != null) {
                                val arrayList = response.body()?.let { ArrayList(it) }
                                Log.d("TAG", "onResponse: $arrayList")
                                for (i in arrayList!!.indices) {
                                    _viewModel.insert(response.body()!![i])
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
                        Log.e("TAG", "onFailure: ${t.toString()}" )
                    }
                })

                Toast.makeText(this,"Refreshed",
                    Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}