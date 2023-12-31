package com.brezker.myapplication.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.brezker.myapplication.R
import com.brezker.myapplication.databinding.FragmentNuevoDoctorBinding
import com.brezker.myapplication.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_doctor"
private const val ARG_PARAM2 = "param2"

private var id_doctor: Int = 0

private lateinit var binding: FragmentNuevoDoctorBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoDoctorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_doctor: String? = null
    private var param2: String? = null

    private var _binding: FragmentNuevoDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_doctor = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNuevoDoctorBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_doctor != null) {
            var gson = Gson()
            var objDoctor = gson.fromJson(json_doctor, Models.Doctor::class.java)

            id_doctor = objDoctor.id
            binding.edtNombre.setText(objDoctor.nombre)
            binding.edtCedula.setText(objDoctor.cedula)
            binding.edtEspecialidad.setText(objDoctor.especialidad)
            binding.edtTurno.setText(objDoctor.turno)
            binding.edtTelefono.setText(objDoctor.telefono)
            binding.edtEmail.setText(objDoctor.email)

        }
        binding.btnGuardar.setOnClickListener{
            guardarDatos()
        }
        binding.btnEliminar.setOnClickListener{
            eliminarDatos()
        }
        return  view
    }

    fun guardarDatos() {
        val client = OkHttpClient()
        val formBody: RequestBody = FormBody.Builder()
            .add("id", id_doctor.toString())
            .add("nombre", binding.edtNombre.text.toString())
            .add("cedula", binding.edtCedula.text.toString())
            .add("especialidad", binding.edtEspecialidad.text.toString())
            .add("turno", binding.edtTurno.text.toString())
            .add("telefono", binding.edtTelefono.text.toString())
            .add("email", binding.edtEmail.text.toString())
            .build()

        val request = Request.Builder()
            //.url("http://yourip:8000/api/paciente")
            .url("http://192.168.100.19:8000/api/doctor")
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Ocurrio un error: " + e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()
                println(respuesta)
                activity?.runOnUiThread {
                    activity?.onBackPressed()
                }
            }
        })
    }

    fun eliminarDatos(){
        val client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("id", id_doctor.toString())
            .build()

        val request = Request.Builder()
            //.url("http://yourip:8000/api/paciente")
            .url("http://192.168.100.19:8000/api/doctor/delete")
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Ocurrio un error: " + e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()
                println(respuesta)
                activity?.runOnUiThread {
                    activity?.onBackPressed()
                }
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoDoctorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoDoctorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}