package com.example.maisoninteligente


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
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

class ViewModelAction : ViewModel() {

    var mode1 = "Chrono"
    var mode2 = "Click"
    var timer1 = "10"
    var timer2 = "10"

    private val database = Firebase.database
    private val etatButton1 = database.getReference("optionButton1/etat")
    private val etatButton2 = database.getReference("optionButton2/etat")
    private val modeButton1 = database.getReference("optionButton1/mode")
    private val modeButton2 = database.getReference("optionButton2/mode")
    private val timerButton1 = database.getReference("optionButton1/timer")
    private val timerButton2 = database.getReference("optionButton2/timer")
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

    private val _modeFlowButton1 = MutableSharedFlow<String>()
    val modeFlowButton1 = _modeFlowButton1.asSharedFlow()

    private val _modeFlowButton2 = MutableSharedFlow<String>()
    val modeFlowButton2 = _modeFlowButton2.asSharedFlow()

    private val _timerFlowButton1 = MutableSharedFlow<String>()
    val timerFlowButton1 = _timerFlowButton1.asSharedFlow()

    private val _timerFlowButton2 = MutableSharedFlow<String>()
    val timerFlowButton2 = _timerFlowButton2.asSharedFlow()

    private val _enableButton1 = MutableSharedFlow<Boolean>()
    val enableButton1 = _enableButton1.asSharedFlow()

    private val _enableButton2 = MutableSharedFlow<Boolean>()
    val enableButton2 = _enableButton2.asSharedFlow()

    init {
        state.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>().toString()
                viewModelScope.launch {
                    when (txt) {
                        "notConnected" -> _statConnexion.emit(R.color.grey)
                        "isConnected" -> _statConnexion.emit(R.color.green)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: stat", error.toException())
            }
        })
        etatButton1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>().toString()
                viewModelScope.launch {
                    _statButton1.emit(txt)
                    when (txt) {
                        "isOn" -> {
                            _colorButton1.emit(R.color.red)
                            _enableButton1.emit(false)
                        }
                        "isOff" -> {
                            _colorButton1.emit(R.color.green)
                            _enableButton1.emit(true)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: etat1", error.toException())
            }
        })
        etatButton2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>().toString()
                viewModelScope.launch {
                    _statButton2.emit(txt)
                    when (txt) {
                        "isOn" -> {
                            _colorButton2.emit(R.color.red)
                            _enableButton2.emit(false)
                        }
                        "isOff" -> {
                            _colorButton2.emit(R.color.green)
                            _enableButton2.emit(true)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: etat2", error.toException())
            }
        })
        modeButton1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>().toString()
                viewModelScope.launch {
                    _modeFlowButton1.emit(txt)
                    when (txt) {
                        "Click" -> _enableButton1.emit(false)
                        "Chrono" -> _enableButton1.emit(true)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: mode1", error.toException())
            }
        })
        modeButton2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>().toString()
                viewModelScope.launch {
                    _modeFlowButton2.emit(txt)
                    when (txt) {
                        "Click" -> _enableButton2.emit(false)
                        "Chrono" -> _enableButton2.emit(true)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: mode2", error.toException())
            }
        })
        timerButton1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>()!!.toInt()
                viewModelScope.launch {
                    _timerFlowButton1.emit(txt.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: timer1", error.toException())
            }
        })
        timerButton2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val txt = snapshot.getValue<String>()!!.toInt()
                viewModelScope.launch {
                    _timerFlowButton2.emit(txt.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value: timer2", error.toException())
            }
        })
    }

    fun setTimerButton(ref: Int, updateTimer: Int) {
        when (ref) {
            1 -> timer1 = updateTimer.toString()
            2 -> timer2 = updateTimer.toString()
        }
    }
    fun setModeButton(ref: Int, updateMode: String) {
        when (ref) {
            1 -> mode1 = updateMode
            2 -> mode2 = updateMode
        }

        Log.d("tag","mode1 $mode1")
        Log.d("tag","mode2 $mode2")
    }
    fun setStatButton(context: Context, refButton: Int, etat: String) {

        lateinit var pathFBStat: String
        lateinit var pathFBMode: String
        lateinit var pathFBTimer: String
        lateinit var mode: String
        lateinit var timer: String


        when (refButton) {
            1 -> {
                pathFBStat = context.getString(R.string.refEtat1FB)
                pathFBMode = context.getString(R.string.refMode1FB)
                pathFBTimer = context.getString(R.string.refTimer1FB)
                mode = mode1
                timer = timer1
            }
            2 -> {
                pathFBStat = context.getString(R.string.refEtat2FB)
                pathFBMode = context.getString(R.string.refMode2FB)
                pathFBTimer = context.getString(R.string.refTimer2FB)
                mode = mode2
                timer = timer2
            }
        }

        val myRef = database.getReference(pathFBStat)
        myRef.setValue(etat)
        val myRef1 = database.getReference(pathFBMode)
        myRef1.setValue(mode)
        val myRef2 = database.getReference(pathFBTimer)
        myRef2.setValue(timer)
    }

    fun setStatConnexion() {
        state.setValue("notConnected")
    }

    fun setEnableButton(ref: Int, bool: Boolean) {
        if (ref == 1) viewModelScope.launch { _enableButton1.emit(bool) }
        else viewModelScope.launch { _enableButton2.emit(bool) }
    }

}