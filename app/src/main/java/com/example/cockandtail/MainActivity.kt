package com.example.cockandtail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.cockandtail.ListAdapter
import com.example.cockandtail.UIdata.ApiService
import com.example.cockandtail.UIdata.Model.JsonResponse
import com.example.cockandtail.UIdata.Model.cocktail
import com.example.cockandtail.ViewModels.MainViewModel
import com.example.cockandtail.ViewModels.ViewModelFactory
import com.example.cockandtail.utils.UIstatus
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(){


    lateinit var viewModel:MainViewModel

    lateinit var list:ArrayList<cocktail>

    lateinit var listAdapter: ListAdapter

    @Inject
    lateinit var viewModelFactory:ViewModelFactory
    @Inject
    lateinit var apiService: ApiService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)
        viewModel= ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        listAdapter= ListAdapter()
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
         recyclerView.adapter = listAdapter


        var searchbar=findViewById<EditText>(R.id.editTextTextPersonName)
        var search_button=findViewById<Button>(R.id.searchButton)

        searchbar.addTextChangedListener(onTextChanged = { a, b, c, d ->
            viewModel.getDrinksByName(a.toString())
        }, afterTextChanged = { text ->
            viewModel.getDrinksByName(text.toString())
        })

        viewModel.drinksList.removeObservers(this)
        viewModel.drinksList.observe(this, {
            if (it != null && viewModel.favouriteView.value == false) {
                when (it.status) {
                    UIstatus.Loading -> {

                    }
                    UIstatus.Error -> {

                    }
                    UIstatus.Success -> {
                        list = it.data?.drinks?.let { it1 -> it1 }?: ArrayList()
                        listAdapter.cocktail = list
                        listAdapter.notifyDataSetChanged()
                        if (viewModel.listPopupPosition >= 0)
                            onItemClick(viewModel.listPopupPosition)
                    }
                }
            }
        })

        listAdapter.itemClickListener.observe(this,{
            viewModel.listPopupPosition = it
            if (it != null && it >= 0){
                onItemClick(it)
            }
        })

        listAdapter.FavtitemClickListener.observe(this,{
            if (it!= null && it >=0){
                viewModel.DbOpsofAddfavts(list[it])
            }
        })

        var favtButton=findViewById<FloatingActionButton>(R.id.favtButton)

        favtButton.setOnClickListener{
            viewModel.favouriteView.value = viewModel.favouriteView.value != true
        }

        viewModel.favouriteView.removeObservers(this)
        viewModel.favouriteView.observe(this,{
            if (it == false){
                if (viewModel.drinksList.value?.data != null){
                    list = viewModel.drinksList.value?.data?.drinks?.let { it1 -> it1 }?: ArrayList()
                    listAdapter.cocktail = list
                    listAdapter.notifyDataSetChanged()
                    if (viewModel.listPopupPosition >= 0)
                        onItemClick(viewModel.listPopupPosition)
                }

            }else{
                if (viewModel.getAllFavourites.value != null) {
                    val cocktailList: ArrayList<cocktail> = ArrayList()
                    for( cocktaildb in viewModel.getAllFavourites.value!!){
                        cocktailList.add(cocktail(cocktaildb.id, cocktaildb.instruction, cocktaildb.name, cocktaildb.imageurl))
                    }
                    list=cocktailList

                    listAdapter.cocktail=cocktailList
                    listAdapter.notifyDataSetChanged()
                }
            }
        })

        viewModel.getAllFavourites.removeObservers(this)
        viewModel.getAllFavourites.observe(this, {
            if (viewModel.favouriteView.value == true &&it != null){
                val cocktailList: ArrayList<cocktail> = ArrayList()
                for( cocktaildb in viewModel.getAllFavourites.value!!){
                    cocktailList.add(cocktail(cocktaildb.id, cocktaildb.instruction, cocktaildb.name, cocktaildb.imageurl))
                }
                list=cocktailList

                listAdapter.cocktail=cocktailList
                listAdapter.notifyDataSetChanged()
            }
        })




    }

    fun onItemClick(position: Int) {
       var mdialog:Dialog= Dialog(this)
        mdialog.setContentView(R.layout.custom_popup)
        var pop_Image=mdialog.findViewById<ImageView>(R.id.image_popup)
        var pop_name=mdialog.findViewById<TextView>(R.id.cocktail_name_card)
        var pop_recipe=mdialog.findViewById<TextView>(R.id.cocktail_recipe)
        var close_pop=mdialog.findViewById<ImageView>(R.id.close_pop)

        val manager: RequestManager = Glide.with(pop_Image)
        manager.load(list[position].imageurl).into(pop_Image)

        pop_name.setText(list[position].name)
        pop_recipe.setText(list[position].instruction)

        close_pop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                mdialog.dismiss()
                listAdapter.resetItemPosition()
            }

        })
        //mdialog.window!!.setBackgroundDrawable(object : ColorDrawable(Color.TRANSPARENT))
        mdialog.show()

    }

    fun onLongClick(){


    }
}