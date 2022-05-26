package com.mrkumar.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mrkumar.groceryapp.adapter.UserAdapter
import com.mrkumar.groceryapp.databinding.ActivityMainBinding
import com.mrkumar.groceryapp.model.UserApiModel
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

    lateinit var ViewModel: MainActivityViewModel
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
        ViewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
//        val userAdapter = UserAdapter(listOf(), ViewModel)
//        binding.rvList.layoutManager = LinearLayoutManager(this)
//        val divider = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
//        addItemDecoration(divider)
//        binding.rvList.adapter = userAdapter

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            userAdapter = UserAdapter(listOf(),ViewModel)
            adapter = userAdapter
            val divider = DividerItemDecoration(applicationContext,
                StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }



        // To display all items in recycler view
        ViewModel.allGroceryItems().observe(this, Observer {
            userAdapter.list = it
            userAdapter.notifyDataSetChanged()
        })

        val apiInterface = NetworkInterface.create().getUserData()

        apiInterface.enqueue( object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {

                if(response?.body() != null){
                    var arrayList=ArrayList(response.body())
                    Log.d("TAG", "onResponse: $arrayList")
                    for(i in arrayList.indices){
                        ViewModel.insert(response.body()!![i])
                    }


                }
//                    recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
                Log.e("TAG", "onFailure: ${t.toString()}" )
            }
        })

    }
}