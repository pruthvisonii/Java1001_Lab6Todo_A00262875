package com.example.java1001_lab6todo_a00262875

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class MainActivity() : AppCompatActivity(), View.OnClickListener, Parcelable {
    private var toDoTextEntry: String? = null

    private var linearLayoutVisibility = View.GONE
    private var addEntriesVisibility = View.VISIBLE
    private var deleteAllVisibility = View.GONE

    private var counter = 0

    private val listOfToDos = ArrayList<String>()

    constructor(parcel: Parcel) : this() {
        toDoTextEntry = parcel.readString()
        linearLayoutVisibility = parcel.readInt()
        addEntriesVisibility = parcel.readInt()
        deleteAllVisibility = parcel.readInt()
        counter = parcel.readInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addToDoList = findViewById<Button>(R.id.addToDoList)
        val addToDoEntry = findViewById<Button>(R.id.addText)
        val entriesDone = findViewById<Button>(R.id.entriesDone)
        val deleteToDo = findViewById<Button>(R.id.deleteToDo)
        addToDoList.setOnClickListener(this)
        addToDoEntry.setOnClickListener(this)
        entriesDone.setOnClickListener(this)
        deleteToDo.setOnClickListener(this)


        setDisplay()
    }

    override fun onClick(view: View) {
//      EditText Entry
        val toDoEntry = findViewById<EditText>(R.id.toDoText)
        //      toDoTextEntry value assigned with the EditText value
        toDoTextEntry = toDoEntry.text.toString()
        when (view.id) {
            R.id.addToDoList -> {
                //          Add to Do List button enables user to display the EditText and button to add into the ListView
//          This button disappears once its clicked.
                linearLayoutVisibility = View.VISIBLE
                addEntriesVisibility = View.GONE
            }
            R.id.addText ->
                if (!toDoTextEntry!!.isEmpty()) addToDoListView()
            R.id.entriesDone -> {

                linearLayoutVisibility = View.GONE
                deleteAllVisibility = View.VISIBLE
                checkToDo()
                findViewById<View>(R.id.constraintLayout).setBackgroundResource(R.color.stick_note)
            }
            R.id.deleteToDo -> {
                addEntriesVisibility = View.VISIBLE
                deleteAllVisibility = View.GONE
                deleteAllEntries()
                findViewById<View>(R.id.constraintLayout).setBackgroundResource(R.color.white)
            }
        }

        setDisplay()
    }

    fun setDisplay() {
        val toDoEntry = findViewById<EditText>(R.id.toDoText)
        toDoEntry.text = null
        findViewById<View>(R.id.addToDoList).visibility = addEntriesVisibility
        findViewById<View>(R.id.deleteToDo).visibility = deleteAllVisibility
        val textLinearLayout = findViewById<LinearLayout>(R.id.textLinearLayout)
        textLinearLayout.visibility = linearLayoutVisibility
    }

    fun addToDoListView() {


        val toDoArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_checked, listOfToDos)
        val toDoList = findViewById<ListView>(R.id.toDoList)
        toDoList.adapter = toDoArrayAdapter

        toDoList.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long ->
                val item = view as CheckedTextView
                item.isChecked = false
            }
        counter++
    }

    fun deleteAllEntries() {
        listOfToDos.clear()
        val toDoList = findViewById<ListView>(R.id.toDoList)
        toDoList.adapter = null
        counter = 0
    }

    fun checkToDo() {
        val toDoList = findViewById<ListView>(R.id.toDoList)
        toDoList.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long ->
                val item = view as CheckedTextView
                item.toggle()
            }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(toDoTextEntry)
        parcel.writeInt(linearLayoutVisibility)
        parcel.writeInt(addEntriesVisibility)
        parcel.writeInt(deleteAllVisibility)
        parcel.writeInt(counter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}