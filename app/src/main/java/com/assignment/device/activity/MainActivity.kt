package com.assignment.device.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.assignment.device.R
import com.assignment.device.data.Device
import com.assignment.device.data.DeviceRepository
import com.assignment.device.data.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google_signIn_btn.setOnClickListener {
            if (Utils.checkForInternet(this@MainActivity))
                signIn()
            else Toast.makeText(this@MainActivity, "Network not available", Toast.LENGTH_LONG)
                .show()
        }
        val repo = DeviceRepository(this)
        repo.deleteAllDevice()
        val deviceList: List<Device> = listOf(
            Device(
                deviceName = "Samsung smart TV",
                ipAddress = "192.168.2.1",
                status = "Active"
            ), Device(
                deviceName = "Amazon Alexa",
                ipAddress = "192.168.1.1",
                status = "Inactive"
            )
        )
        for (device in deviceList)
            repo.insertDevice(device)


    }

    private fun signIn() {

        val signInIntent = mGoogleSignInClient?.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(result?.data)

                try {
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                    if (account != null) {
                        getPreferences(MODE_PRIVATE).edit().putString("token", account?.idToken)
                            .commit();
                        val intent = Intent(this@MainActivity, DeviceList::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: ApiException) {
                    Log.e("TAG", "signInResult:failed code=" + e.statusCode)
                }
            }
        }

    override fun onStart() {
        super.onStart()
        if (Utils.checkForInternet(this@MainActivity)) {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null) {
                val intent = Intent(this@MainActivity, DeviceList::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            mGoogleSignInClient?.signOut()?.addOnCompleteListener(
                this
            ) { Toast.makeText(this@MainActivity, "Signed Out", Toast.LENGTH_LONG).show() }

        }


    }
}