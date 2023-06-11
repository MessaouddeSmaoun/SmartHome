package com.example.maisoninteligente


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ViewModelAction: ViewModel() {

    private val database = Firebase.database
    private val refButton1 = database.getReference("refButton1")
    private val refButton2 = database.getReference("refButton2")
    private val state = database.getReference("state")

    private val _statConnexion = MutableSharedFlow<Int>()
    val statConnexion = _statConnexion.asSharedFlow()

    private val _statButton1 = MutableSharedFlow<String>()
    val statButton1 = _statButton1.asSharedFlow()

    private val _colorButton1 = MutableSharedFlow<Int>()
    val colorButton1 = _colorButton1.asSharedFlow()

    private val _statButton2 = MutableSharedFlow<String>()
    val statButton2 = _statButton2.asSharedFlow()

    private val _colorButton2 = MutableSharedFlow<Int>()
    val colorButton2 = _colorButton2.asSharedFlow()

init {
    refButton1.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val txt = snapshot.getValue<String>().toString()
            viewModelScope.launch {
                _statButton1.emit(txt)
                when(txt){
                    "isOn" -> _colorButton1.emit(R.color.orange)
                    "isOff" -> _colorButton1.emit(R.color.green)
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w("tag", "Failed to read value.", error.toException())
        }
    })
    refButton2.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val txt = snapshot.getValue<String>().toString()
            viewModelScope.launch {
                _statButton2.emit(txt)
                when(txt){
                    "isOn" -> _colorButton2.emit(R.color.orange)
                    "isOff" -> _colorButton2.emit(R.color.green)
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w("tag", "Failed to read value.", error.toException())
        }
    })
    state.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val txt = snapshot.getValue<String>().toString()
            viewModelScope.launch {
                when(txt){
                    "notConnected" -> _statConnexion.emit(R.color.grey)
                    "isConnected" -> _statConnexion.emit(R.color.green)
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w("tag", "Failed to read value.", error.toException())
        }
    })
}

    fun setStatButton(ref:String, updateStat: String) {
        val myRef = database.getReference(ref)
        myRef.setValue(updateStat)
    }

    fun setStatConnexion() {
        state.setValue("notConnected")
    }

}