package com.example.mobilaappfinalproject
//절대지켜
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView//SearchView사용
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mobilaappfinalproject.databinding.ActivitySecondBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SecondActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding:ActivitySecondBinding

    var gMap:GoogleMap?=null//구글맵참조변수
   // val binding by lazy{ActivitySecondBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

       //구글맵
     //이건 왜 오류가 뜰까?  (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)!!.getMapAsync(this)
       val mapFragment = SupportMapFragment.newInstance()
       supportFragmentManager
           .beginTransaction()
           .add(R.id.map, mapFragment)
           .commit()
       mapFragment.getMapAsync(this)


       //툴바
        //관련 참고 블로그: https://kiwinam.com/posts/25/android-toolbar-add-button/
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) //액션바에 표시되는 제목의 표시유무를 설정합니다. false로 해야 custom한 툴바의 이름이 화면에 보이게 됩니다.
        supportActionBar?.setDisplayShowTitleEnabled(false) //액션바에 표시되는 제목의 표시유무를 설정합니다. false로 해야 custom한 툴바의 이름이 화면에 보이게 됩니다.
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white)  // 왼쪽 버튼 이미지 설정

        binding.backBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
    //구글맵
    override fun onMapReady(googleMap: GoogleMap) {
       gMap=googleMap
//        gMap?.mapType=GoogleMap.MAP_TYPE_NORMAL
        val latLng=LatLng(37.52487, 126.92723)
        val position:CameraPosition=CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        gMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        gMap!!.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }
    //상단 검색창
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_find,menu)
        val menuSearch = menu?.findItem(R.id.menu_search)
        val serachView=menuSearch?.actionView as SearchView
        serachView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            //serachView에서 사용자 입력값 받아ㅗ오기

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tv.text=query
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}