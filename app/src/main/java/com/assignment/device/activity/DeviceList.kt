package com.assignment.device.activity

import android.content.Context
import android.content.Intent
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.device.R
import com.assignment.device.data.Device
import com.assignment.device.data.DeviceListAdapter
import com.assignment.device.data.DeviceRepository
import com.assignment.device.data.ListClickListener
import kotlinx.android.synthetic.main.activity_device_list.*

class DeviceList : AppCompatActivity() {
    private lateinit var nsdManager: NsdManager
    lateinit var adapter: DeviceListAdapter
    val repo: DeviceRepository by lazy {
        DeviceRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        adapter = DeviceListAdapter()
        recyclerview_devices.layoutManager = LinearLayoutManager(this)
        recyclerview_devices.adapter = adapter
        adapter.setOnItemClick(object : ListClickListener<Device> {
            override fun onClick(data: Device, position: Int) {
                val intent = Intent(this@DeviceList, DetailsActivity::class.java)
                startActivity(intent)
            }

        })
        // networkServiceDiscovery()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        fetchUsers()
    }

    private fun fetchUsers() {
        val allUsers = repo.getAllDevices()
        adapter.setDevices(allUsers)
    }

    private fun networkServiceDiscovery() {
        nsdManager = (getSystemService(Context.NSD_SERVICE) as NsdManager)
        nsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    private val discoveryListener = object : NsdManager.DiscoveryListener {
        private val TAG = "DISCOVERY_LISTENER"

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            Log.d(TAG, "Service discovery success $service")

        }

        override fun onServiceLost(service: NsdServiceInfo) {
            Log.e(TAG, "service lost: $service")

        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }
    }

}