package com.assignment.device.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeviceDao {

    @Insert
    fun insertDevice(device: Device)

    @Query("Select * from device")
    fun gelAllDevice(): List<Device>

    @Update
    fun updateDevice(device: Device)

    @Query("DELETE FROM device")
    fun deleteAllDevice()

}