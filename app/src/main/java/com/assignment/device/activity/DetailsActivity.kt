package com.assignment.device.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.assignment.device.R
import com.assignment.device.data.DetailsIP
import com.assignment.device.data.NetworkUtility
import com.assignment.device.data.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val actionbar = supportActionBar
        actionbar?.title = "Details"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val ipAddressUrl = URL(Utils.IP_ADDRESS)

            val ipAddress = NetworkUtility.request(ipAddressUrl)

            val getDetailsUrl =
                URL(Utils.IP_DETAILS + JSONObject(ipAddress).getString("ip") + "/geo")

            val detailsIPData = NetworkUtility.request(getDetailsUrl)
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.INVISIBLE
                val gson = Gson()
                val detailsIP = gson.fromJson(detailsIPData, DetailsIP::class.java)
                ipAddressText.text = detailsIP.ip
                (getString(R.string.city) + " " + detailsIP.city).also { city.text = it }
                (getString(R.string.region) + " " + detailsIP.region).also { region.text = it }
                (getString(R.string.company) + " " + detailsIP.org).also { org.text = it }
                postal.text = detailsIP.postal

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}