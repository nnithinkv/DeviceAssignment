package com.assignment.device.data

import android.content.Context
import android.os.AsyncTask

class DeviceRepository(context: Context) {

    var db: DeviceDao = AppDatabase.getInstance(context)?.deviceDao()!!


    //Fetch All the Devices
    fun getAllDevices(): List<Device> {
        return db.gelAllDevice()
    }

    // Insert new device
    fun insertDevice(devices: Device) {
        insertAsyncTask(db).execute(devices)
    }

    // update device
    fun updateDevice(device: Device) {
        db.updateDevice(device)
    }

    // Delete device
    fun deleteAllDevice() {
        db.deleteAllDevice()
    }

    private class insertAsyncTask internal constructor(private val devicesDao: DeviceDao) :
        AsyncTask<Device, Void, Void>() {

        override fun doInBackground(vararg params: Device): Void? {
            devicesDao.insertDevice(params[0])
            return null
        }
    }
}