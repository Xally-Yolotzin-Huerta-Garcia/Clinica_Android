package com.brezker.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brezker.myapplication.databinding.FragmentHomeBinding
import com.brezker.myapplication.extras.Models
import com.brezker.myapplication.extras.PacienteAdapter
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
//import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
//import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun obtenerDatos(){
        var url="http://127.0.0.1:8000/api/pacientes"

        val request=Request.Builder()
            .url(url)
            .header("accept", "application/json")
            .get()
            .build()
        val client =OkHttpClient()
        var gson= Gson()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "ocurrio un error: "+ e.message.toString(), Toast.LENGTH_LONG).show();

                }
            }


            override fun onResponse(call: Call, response: Response) {
                var respuesta= response.body?.toString()
                println("respuesta: "+ respuesta)

                activity?.runOnUiThread {
                    var listItems= gson.fromJson(respuesta, Array<Models.Paciente>::class.java)

                    val adapter= PacienteAdapter(listItems.toMutableList())
                    binding.rvDatosPaciente.layoutManager= LinearLayoutManager(context)
                    binding.rvDatosPaciente.adapter=adapter
                }
            }
        })
    }
}