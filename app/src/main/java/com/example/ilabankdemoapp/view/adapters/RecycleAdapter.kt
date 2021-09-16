package com.example.ilabankdemoapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ilabankdemoapp.R
import java.util.ArrayList

class RecycleAdapter(val context:Context, var dataSet: List<String>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {
    private var matchedData: ArrayList<String> = arrayListOf()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_recycler, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position]
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun search(text: String?) {
        matchedData = arrayListOf()

        text?.let {
            dataSet.forEach { data ->
                if (data.contains(text, true) ||
                    data.toString().contains(text, true)
                ) {
                    matchedData.add(data)
                }
            }
            dataSet = matchedData
            notifyDataSetChanged()
        }
    }

    fun updateList(dummyList: List<String>) {
        dataSet = dummyList
        notifyDataSetChanged()

    }
}