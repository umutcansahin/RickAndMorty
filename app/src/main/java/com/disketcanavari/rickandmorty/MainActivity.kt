package com.disketcanavari.rickandmorty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.disketcanavari.rickandmorty.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


val client = OkHttpClient()

private var recylerViewAdapter: RecylerViewAdapter?=null
lateinit var charList: ArrayList<Results>
var isLastPage: Boolean = false
var isLoading: Boolean = false
 var next_page_url = ""
class MainActivity : AppCompatActivity(),SetOnClickinRecyler {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recylerView.layoutManager = layoutManager
        getCharData()

        binding.recylerView?.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                if(next_page_url.isNotEmpty()){
                     getMoreCharData(next_page_url)
                }
            }


        })
        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                println(charList.find{ it.name.startsWith(binding.editTextTextPersonName.text) })
            }

        })


    }
    fun getCharData(){

        val request = Request.Builder()
            .url("https://rickandmortyapi.com/api/character")
            .build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val chars = Gson().fromJson(body,GetData::class.java)
                charList = chars.result
                next_page_url = chars.info.next
                runOnUiThread {
                    recylerViewAdapter = RecylerViewAdapter(this@MainActivity)
                    binding.recylerView.adapter = recylerViewAdapter
                    recylerViewAdapter?.setData(charList)!!
                }
            }

        })

    }

    fun getMoreCharData(url: String){
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val chars =  Gson().fromJson(body,GetData::class.java)
                charList.addAll(chars.result)
                isLoading = false
                runOnUiThread {
                    next_page_url = chars.info.next

                    recylerViewAdapter?.setData(charList)
                }

            }

        })
    }

    override fun onItemClicked(character: Results) {
        trasferData(character)
    }

    fun trasferData(character: Results){


        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("transfer", character)
        startActivity(intent)


    }
}