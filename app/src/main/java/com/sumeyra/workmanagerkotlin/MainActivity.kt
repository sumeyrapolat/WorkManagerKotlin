package com.sumeyra.workmanagerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intkey",24).build()


        //constraints

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .build()

        /* val myWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)*/


        val myWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, Observer {
            if (it.state==WorkInfo.State.RUNNING){
                println("Running")

            }else if(it.state == WorkInfo.State.CANCELLED){
                println("Canceled")
            }else if (it.state == WorkInfo.State.FAILED){
                println("Failed")
            }
        })

        //Chaining

        /*

        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<RefreshDatabase>()
                .setConstraints(constraints)
                .setInputData(data)
                //.setInitialDelay(5,TimeUnit.HOURS)
                //.addTag("myTag")
                .build()

         WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
             .then(oneTimeWorkRequest)
             .thenx
             .enqueue()

         */
    }



}