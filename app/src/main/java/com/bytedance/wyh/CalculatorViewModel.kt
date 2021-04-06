package com.bytedance.wyh

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asFlow
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class CalculatorViewModel : ViewModel() {

    private val _theInput = MutableLiveData<String>()
    val theInput : LiveData<String>
        get() = _theInput

    private val _alertMsg = MutableLiveData<String>()
    val alertMsg : LiveData<String>
        get() = _alertMsg

    private var isResult = false
    init {
        _theInput.value = "0"
    }

    fun onPressed(newInput : Char) {
        if (_theInput.value?.length!! > 20) return
        if (!isSymbol(newInput)){
            if (_theInput.value == "0" || isResult) _theInput.value = newInput.toString()
            else  _theInput.value += newInput
        } else {
            if(isSymbol(_theInput.value!![_theInput.value!!.length-1])) {
                onDelete()
            }
            _theInput.value += newInput

        }
        isResult = false
    }

    fun onClear() {
        _theInput.value = "0"
    }

    fun onDelete() {
        if (_theInput.value != null) {
            _theInput.value = _theInput.value!!.substring(0, _theInput.value!!.length - 1)
        }
    }

    fun onCalculate()  {
        if (_theInput.value != null && !hasSymbol(_theInput.value!!)) return
        val symbolStack = Stack<Char>()
        val numberStack = Stack<Double>()
        var numStr = ""

        val charIterator = _theInput.value?.iterator()
        if (charIterator != null) {
            while (charIterator.hasNext()) {
                var char = charIterator.next()
                Log.e("char: ",char.toString())
                if (isSymbol(char)) {
                    numberStack.add(numStr.toDouble())
                    numStr = ""
                    symbolStack.add(char)
                } else {
                    numStr += char
                }
            }
            numberStack.add(numStr.toDouble())
        }
        Log.e("err",numberStack.toString())
        val symbolHelperStack = Stack<Char>()
        val numberHelperStack = Stack<Double>()
        symbolHelperStack.add(symbolStack.pop())
        numberHelperStack.add(numberStack.pop())
        while (!symbolStack.empty() || !symbolHelperStack.empty()){
            if (symbolHelperStack.peek() in listOf('*','÷') || symbolStack.empty()) {
                try {
                    val result = calculate(numberStack.pop(),numberHelperStack.pop(),symbolHelperStack.pop())
                    numberStack.add(result)
                } catch (e:Exception) {
                    _alertMsg.value = "Wrong Format. Dividend cannot be 0!"
                    return
                }
            }
            if (symbolStack.empty() && symbolHelperStack.empty()) break
            if (!symbolStack.empty()) {
                symbolHelperStack.add(symbolStack.pop())
                numberHelperStack.add(numberStack.pop())
            }
        }
        var result = numberStack.pop()
        if (Math.floor(result) == result) _theInput.value = result.toInt().toString()
        else _theInput.value = result.toString()
        isResult = true

    }
    private fun calculate(a:Double, b:Double, c:Char) : Double{
        if ('÷' == c && 0.0 == a) throw Exception()
        when (c) {
            '+' -> return a+b
            '-' -> return a-b
            '*' -> return a*b
            '÷' -> return a/b
            else -> throw Exception()
        }

    }

    private fun isSymbol(c : Char): Boolean {
        return (c in listOf('+','-','*','÷'))
    }

    private fun hasSymbol(s : String) : Boolean {
        for (i in 0..s.length-1) {
            if (isSymbol(s.get(i))) return true
        }
        return false
    }



}