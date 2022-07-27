package com.assignment.device.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.device.R
import kotlinx.android.synthetic.main.layout_device_list.view.*

class DeviceListAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var deviceList = mutableListOf<Device>()

    var clickListener: ListClickListener<Device>? = null

    fun setDevices(devices: List<Device>) {
        this.deviceList = devices.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_device_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val device = deviceList[position]
        holder.ipAddress.text = device.ipAddress
        holder.deviceName.text = device.deviceName
        holder.status.text = device.status
        holder.layout.setOnClickListener {
            clickListener?.onClick(device, position)
        }


    }

    fun setOnItemClick(listClickListener: ListClickListener<Device>) {
        this.clickListener = listClickListener
    }

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val deviceName = view.text_device_name
    val ipAddress = view.text_ip_address
    val status = view.text_status
    val layout = view.layout
}


interface ListClickListener<T> {
    fun onClick(data: T, position: Int)
}
