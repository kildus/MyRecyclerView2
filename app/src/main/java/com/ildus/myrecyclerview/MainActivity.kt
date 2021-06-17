package com.ildus.myrecyclerview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "myTag"

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var putTextButton: Button
    private lateinit var data: MutableList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editTextView)
        putTextButton = findViewById(R.id.putTextButton)

        fillList()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomRecyclerAdapter(data)
        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        putTextButton.setOnClickListener {
            if (!editText.text.isEmpty()) {
//                Toast.makeText(applicationContext, "isEmpty", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "isEmpty")
                data.add(editText.text.toString())
                recyclerView.adapter?.notifyItemChanged(data.size - 1)
            }
        }




//        editText.doAfterTextChanged {
//            Log.d(TAG, "doAfterTextChanged")
//        }

//        editText.setOnClickListener {
//            Toast.makeText(this, "editText", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "editText")
//        }
    }

    private fun fillList(): List<String> {
        data = mutableListOf<String>()
        (0..3).forEach { i -> data.add("$i element") }
        return data
    }

}

class CustomRecyclerAdapter(private val names: MutableList<String>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d(TAG, "onClick " + names[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {

           names.removeAt(adapterPosition)
           notifyItemRemoved(adapterPosition)

           return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = names[position]
        holder.smallTextView.text = "кот"
    }

    override fun getItemCount() = names.size
}