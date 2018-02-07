package com.example.divyamsingh.music_player.Activities
import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.divyamsingh.music_player.Adapters.NavigationDrawerAdapter
import com.example.divyamsingh.music_player.Fragments.MainScreenFragment
import com.example.divyamsingh.music_player.Fragments.SongPlayingFragment
import com.example.divyamsingh.music_player.R
import android.widget.Toast
import android.content.pm.PackageManager



class MainActivity : AppCompatActivity(){


    var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer: IntArray = intArrayOf(R.drawable.navigation_allsongs, R.drawable.navigation_favorites,
            R.drawable.navigation_settings,R.drawable.navigation_aboutus)

    @SuppressLint("StaticFieldLeak")
    object Statified{
        var drawerLayout: DrawerLayout? = null      // static variable of type "DrawerLayout"
        var notificationManager : NotificationManager? = null
    }

    var trackNotificationBuilder : Notification? = null


    // Auto-generated
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        // Adding values to the arrayList
        navigationDrawerIconsList.add("All Songs")
        navigationDrawerIconsList.add("Favorites")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("About Us")

        val toggle = ActionBarDrawerToggle(this@MainActivity, MainActivity.Statified.drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.Statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()

        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, mainScreenFragment, "MainScreenFragment")
                .commit()

        var _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconsList, images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()

        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter=_navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        val intent = Intent(this@MainActivity, MainActivity::class.java)
        val preIntent = PendingIntent.getActivity(this@MainActivity, System.currentTimeMillis().toInt() as Int, intent, 0)

        trackNotificationBuilder = Notification.Builder(this)
                .setContentTitle("Echo is playing music")
                .setSmallIcon(R.drawable.echo_logo)
                .setContentIntent(preIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .build()

        Statified.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }



    override fun onStart() {
        try{
            Statified.notificationManager?.cancel(1111)
        }catch (e : Exception){
            e.printStackTrace()
        }
        super.onStart()
    }

    override fun onResume() {
        try{
            Statified.notificationManager?.cancel(1111)
        }catch (e : Exception){
            e.printStackTrace()
        }
        super.onResume()
    }

    override fun onStop() {
        try{
            if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1111, trackNotificationBuilder)
            }
        }catch(e : Exception) {
            e.printStackTrace()
        }
        super.onStop()
    }

    override fun onDestroy() {
        try{
            if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1111, trackNotificationBuilder)
            }
        }catch(e : Exception) {
            e.printStackTrace()
        }

        //Statified.notificationMAnager?.cancelAll()
        super.onDestroy()
    }
}