package com.example.ilabankdemoapp.view.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.ilabankdemoapp.R
import com.example.ilabankdemoapp.databinding.ActivityScrollingBinding
import com.example.ilabankdemoapp.model.ImageDetail
import com.example.ilabankdemoapp.view.adapters.RecycleAdapter
import com.example.ilabankdemoapp.view.adapters.ViewPagerAdapter
import com.example.ilabankdemoapp.viewmodel.DummyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var dots: Array<ImageView?>? = null
    private var imageList = mutableListOf<ImageDetail>()
    private var originalList = mutableListOf<ImageDetail>()
    private var currentPage = 0
    private var selectedDot = 0
    private var unselectedDot = 0
    private lateinit var binding: ActivityScrollingBinding
    lateinit var dummyViewModel: DummyViewModel
    lateinit var adapter: RecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dummyViewModel = ViewModelProvider(this).get(DummyViewModel::class.java)

        val typedArray = applicationContext.theme.obtainStyledAttributes(
            null,
            R.styleable.ImageSlider,
            0,
            0
        )
        selectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_iss_selected_dot,
            R.drawable.default_selected_dot
        )
        unselectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_iss_unselected_dot,
            R.drawable.default_unselected_dot
        )
        binding.contentView.recyclerView.layoutManager = LinearLayoutManager(this)
        dummyViewModel.fetchDymmyData()

        dummyViewModel.dummmyData.observe(this, {
            imageList.clear()
            imageList.addAll(it)
            originalList = imageList
            viewPagerAdapter = ViewPagerAdapter(this, imageList)
            binding.viewPager.adapter = viewPagerAdapter
            adapter = RecycleAdapter(this, imageList[0].dummyList)
            binding.contentView.recyclerView.adapter = adapter
            setupDots(imageList.size)
            dummyViewModel.dummmyData.removeObservers(this)

        })

        performSearch()
    }

    private fun setupDots(size: Int) {
        binding.pagerDots.removeAllViews()
        dots = arrayOfNulls(size)

        for (i in 0 until size) {
            dots!![i] = ImageView(applicationContext)
            dots!![i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    unselectedDot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.pagerDots.addView(dots!![i], params)
        }
        dots!![0]!!.setImageDrawable(ContextCompat.getDrawable(applicationContext, selectedDot))


        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                adapter.updateList(imageList[currentPage].dummyList)
                for (dot in dots!!) {
                    dot!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            unselectedDot
                        )
                    )
                }
                dots!![position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        selectedDot
                    )
                )

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun performSearch() {
        binding.searchView.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                adapter.updateList(originalList[currentPage].dummyList)
                return false
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    adapter.updateList(originalList[currentPage].dummyList)
                }else {
                    adapter.search(newText)
                }
                    return true
            }
        })
    }
}