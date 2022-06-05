package com.example.imagerecognition.ui.mainPage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagerecognition.R
import com.example.imagerecognition.databinding.ActivityMainBinding
import com.example.imagerecognition.logic.LoadingDialog
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {
        val ANIMAL: Int = 1
        val PLANT: Int = 2

    }

    lateinit var binding: ActivityMainBinding

    val mainpageViewModel by lazy { ViewModelProvider(this).get(MainPageViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = FunctionAdapter(this, mainpageViewModel.functionList)
        binding.recyclerView.adapter = adapter



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                LoadingDialog.getInstance(this).show()
//                Toast.makeText(this, "gps", Toast.LENGTH_SHORT).show()
                thread {
                    val location = getLocation(this)
                  val address = getGeoByLocation(location!!)
                    if (location != null) {
                        this.runOnUiThread {
                            binding.textView.visibility = View.VISIBLE

                            binding.longView.text = "经度：${location.longitude}"
                            binding.longView.visibility = View.VISIBLE
                            binding.latiView.text = "纬度：${location.latitude}"
                            binding.latiView.visibility = View.VISIBLE
                            binding.addrView.text = "地址:${address.getAddressLine(0)}"
                            binding.addrView.visibility = View.VISIBLE
                        }
                    }


                }
                LoadingDialog.getInstance(this).hide()
                LoadingDialog.setInstance(null)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.gps -> {

                val checkCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                val checkCallPhonePermission =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED || checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 2)
                    return false;
                }

                LoadingDialog.getInstance(this).show()
//                Toast.makeText(this, "gps", Toast.LENGTH_SHORT).show()
                thread {
                    val location = getLocation(this)
                    val address = getGeoByLocation(location!!)

                    if (location != null) {
                        this.runOnUiThread {
                            binding.textView.visibility = View.VISIBLE

                            binding.longView.text = "经度:${location.longitude}"
                            binding.longView.visibility = View.VISIBLE
                            binding.latiView.text = "纬度:${location.latitude}"
                            binding.latiView.visibility = View.VISIBLE
                            binding.addrView.text = "地址:${address.getAddressLine(0)}"
                            binding.addrView.visibility = View.VISIBLE


                        }
                    }


                }
                LoadingDialog.getInstance(this).hide()
                LoadingDialog.setInstance(null)

            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun getLocation(context: Context): Location? {
        val locMan = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val checkCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val checkCallPhonePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED || checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 2)
         }
        var location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
             location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
         }
        if (location == null) {
            location = locMan.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        }
//        Log.d("test", location.toString())
        return location
    }

         private fun getGeoByLocation(location:Location): Address {
//                Log.d("test", "longitude：${location.longitude}")
//                Log.d("test", "latitude：${location.latitude}")
                val ge = Geocoder(this)
                var addressList =ArrayList<Address>()
                try {
                    addressList = ge.getFromLocation(location.latitude,location.longitude,1) as ArrayList<Address>
//                    Log.d("test", addressList.toString())
                }catch (e: IOException){
                    e.printStackTrace()
                }

                return addressList[0]
             }


}
