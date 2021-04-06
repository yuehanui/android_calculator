package com.bytedance.wyh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bytedance.wyh.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private lateinit var binding : FragmentCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        binding.calculatorViewModel = viewModel
        binding.lifecycleOwner = this



        viewModel.alertMsg.observe(this, Observer { newMsg ->
            if (!newMsg.isEmpty()) showToast(newMsg)
        })

        return binding.root
    }

    fun showToast(msg:String) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

}