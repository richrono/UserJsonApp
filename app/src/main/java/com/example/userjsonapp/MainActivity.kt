package com.example.userjsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
//import com.example.userjsonapp.UsersApiService.UsersApi.retrofitService
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header

//Ver2---------------------------------------
//↓bk2
private val BEARER_TOKEN = "RgxJBukZukKYP433n-Dh66YzHZHYicph3FMeqpCmjkg"
private val BASE_URL = "http://10.0.2.2:3000/api/v1/statuses/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()
private val api = retrofit.create(UsersApiService::class.java)

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") accessToken: String//,
//        @Query("query") searchWord: String? = null
    ):ResponseBody
//    object UsersApi {
//        val retrofitService: UsersApiService by lazy { retrofit.create(UsersApiService::class.java) }
//    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val api = retrofit.create(UsersApiService::class.java)
        val btnFuture: Button = findViewById(R.id.btnFuture)
        val tvAll: TextView = findViewById(R.id.tvAll)
        val btnClear: Button = findViewById(R.id.btnClear)
        btnFuture.setOnClickListener {
//            val query = tvAll.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.getUsers(accessToken = "Bearer $BEARER_TOKEN").string()
                withContext(Dispatchers.Main) {
                    tvAll.text = response
                }
            }
        }
        //btnClear押されたら
        btnClear.setOnClickListener {
//            tvUserName.text = "ユーザー名"
//            tvAddress.text = "アドレス"
            tvAll.text = "全情報"
        }
    }
}

//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
////        APIキーとURLの定義
//        val apiKey = "GhnioHaCuITNJ7l2FMSYXIrzW5CkbHonFy5G4qcaec8"
//        val mainUrl = "http://10.0.2.2:3000/api"
////        viewを取得
//        val btnUser: Button = findViewById(R.id.btnUser)
//        val btnFuture: Button = findViewById(R.id.btnFuture)
//        val tvUserName: TextView = findViewById(R.id.tvUserName)
//        val tvAddress: TextView = findViewById(R.id.tvAddress)
//        val tvAll: TextView = findViewById(R.id.tvAll)
//        val btnClear: Button = findViewById(R.id.btnClear)
//
////        btnUser押されたら
//        btnUser.setOnClickListener {
////            UserのURLを取得
//            val userUrl = "$mainUrl/v1/statuses/users"
//
////            URLを基に得られた情報の表示
////            コルーチン作成・HTTP通信（ワーカースレッド）・Userデータ表示
//            userTask(userUrl) //中身は２へ
//
//        }
//
//        //btnClear押されたら
//        btnClear.setOnClickListener {
//            tvUserName.text = "ユーザー名"
//            tvAddress.text = "アドレス"
//            tvAll.text = "全情報"
//        }
//        // btnFuture押されたら
//        btnFuture.setOnClickListener {
//            val userUrl = "$mainUrl/users?code=$apiKey"
//            futureTask(userUrl) //中身は2−２
//
//        }
//    }
//
//    //    ２
//    private fun userTask(userUrl: String) {
////        コルーチンスコープを作成
//        lifecycleScope.launch {
////            3-2.HTTP通信
//            val result = userBackGroundTask(userUrl)
////            4-2.データ表示
//            userJsonTask(result)
//
//        }
//    }
//
//    // 2−２
//    private fun futureTask(userUrl: String) {
////        コルーチンスコープを作成
//        lifecycleScope.launch {
////            3-2.HTTP通信
//            val result = futureBackGroundTask(userUrl)
////            4-2.データ表示
//            futureJsonTask(result)
//        }
//    }
//
//    //    3の中身
//    private suspend fun userBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
////        withContextはスレッドを分離しますよ。
////        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
//        val response = withContext(Dispatchers.IO) {
////          取得データ（json形式）を入れる変数の宣言
//            var httpResult = ""
////            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
//            try {
//                val urlObj = URL(userUrl)
////                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
////                これはここだけで動画が一本できるほどややこしいので説明割愛
//                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
////                httpResult = br.toString() これだとエミュレータでエラーになる
//                httpResult = br.readText()
//            } catch (e: IOException) {
//                e.printStackTrace() //エラー出たよって言う。
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            return@withContext httpResult
//        }
//        return response
//    }
//
//    // 3-2
//    private suspend fun futureBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
////        withContextはスレッドを分離しますよ。
////        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
//        val response = withContext(Dispatchers.IO) {
////          取得データ（json形式）を入れる変数の宣言
//            var httpResult = ""
////            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
//            try {
//                val futureObj = URL(userUrl)
////                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
////                これはここだけで動画が一本できるほどややこしいので説明割愛
//                val br = BufferedReader(InputStreamReader(futureObj.openStream()))
////                httpResult = br.toString() これだとエミュレータでエラーになる
//                httpResult = br.readText()
//            } catch (e: IOException) {
//                e.printStackTrace() //エラー出たよって言う。
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            return@withContext httpResult
//        }
//        return response
//    }
//
//    //    4
//    private fun userJsonTask(result: String) {
//        val tvUserName: TextView = findViewById(R.id.tvUserName)
//        val tvAddress: TextView = findViewById(R.id.tvAddress)
////        val tvAll: TextView = findViewById(R.id.tvAll)
//
////    まず３で取得したjson全体の生成
//        val jsonObj = JSONObject(result)
////        全体の中のnameを抽出
////        val userName = jsonObj.getString("name")
////        tvUserName.text = userName
//
////        全体の中のusersの中のnameを抽出
//        val userJSONArray = jsonObj.getJSONArray("users")
//        val userJSON = userJSONArray.getJSONObject(0)
//        val userName = userJSON.getString("name")
//        tvUserName.text = userName
//
////        val userJSONArray = jsonObj.getJSONArray("users")
////        val userJSON = userJSONArray.getJSONObject(0)
//        val userAddress = userJSON.getString("email")
//        tvAddress.text = userAddress
//
////        val userAll = jsonObj.toString()
////        tvAll.text = userAll
//    }
//
//    // 4-2
//    private fun futureJsonTask(result: String) {
//        val tvAll: TextView = findViewById(R.id.tvAll)
//        val jsonObj = JSONObject(result)
//        val userAll = jsonObj.toString()
//        tvAll.text = userAll
//
//    }
//}

//ver.1----------------------------------------------------------
// ↓bk
// class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
////        APIキーとURLの定義
//        val apiKey = "cL-kMqHaNUhqcDxYoQEXaGwAjVFHm_Wo4GCoF8Avnlo"
//        val mainUrl = "http://10.0.2.2:3000"
////        viewを取得
//        val btnUser: Button = findViewById(R.id.btnUser)
//        val btnFuture: Button = findViewById(R.id.btnFuture)
//        val tvUserName: TextView = findViewById(R.id.tvUserName)
//        val tvAddress: TextView = findViewById(R.id.tvAddress)
//        val tvAll: TextView = findViewById(R.id.tvAll)
//        val btnClear: Button = findViewById(R.id.btnClear)
//
////        btnUser押されたら
//        btnUser.setOnClickListener {
////            UserのURLを取得
//            val userUrl = "$mainUrl/users?code=$apiKey"
//
////            URLを基に得られた情報の表示
////            コルーチン作成・HTTP通信（ワーカースレッド）・Userデータ表示
//            userTask(userUrl) //中身は２へ
//
//        }
//
//        //btnClear押されたら
//        btnClear.setOnClickListener {
//            tvUserName.text = "ユーザー名"
//            tvAddress.text = "アドレス"
//            tvAll.text = "全情報"
//        }
//        // btnFuture押されたら
//        btnFuture.setOnClickListener {
//            val userUrl = "$mainUrl/users?code=$apiKey"
//            futureTask(userUrl) //中身は2−２
//
//        }
//    }
//
//    //    ２
//    private fun userTask(userUrl: String) {
////        コルーチンスコープを作成
//        lifecycleScope.launch {
////            3-2.HTTP通信
//            val result = userBackGroundTask(userUrl)
////            4-2.データ表示
//            userJsonTask(result)
//
//        }
//    }
//
//    // 2−２
//    private fun futureTask(userUrl: String) {
////        コルーチンスコープを作成
//        lifecycleScope.launch {
////            3-2.HTTP通信
//            val result = futureBackGroundTask(userUrl)
////            4-2.データ表示
//            futureJsonTask(result)
//        }
//    }
//
//    //    3の中身
//    private suspend fun userBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
////        withContextはスレッドを分離しますよ。
////        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
//        val response = withContext(Dispatchers.IO) {
////          取得データ（json形式）を入れる変数の宣言
//            var httpResult = ""
////            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
//            try {
//                val urlObj = URL(userUrl)
////                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
////                これはここだけで動画が一本できるほどややこしいので説明割愛
//                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
////                httpResult = br.toString() これだとエミュレータでエラーになる
//                httpResult = br.readText()
//            } catch (e: IOException) {
//                e.printStackTrace() //エラー出たよって言う。
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            return@withContext httpResult
//        }
//        return response
//    }
//
//    // 3-2
//    private suspend fun futureBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
////        withContextはスレッドを分離しますよ。
////        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
//        val response = withContext(Dispatchers.IO) {
////          取得データ（json形式）を入れる変数の宣言
//            var httpResult = ""
////            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
//            try {
//                val futureObj = URL(userUrl)
////                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
////                これはここだけで動画が一本できるほどややこしいので説明割愛
//                val br = BufferedReader(InputStreamReader(futureObj.openStream()))
////                httpResult = br.toString() これだとエミュレータでエラーになる
//                httpResult = br.readText()
//            } catch (e: IOException) {
//                e.printStackTrace() //エラー出たよって言う。
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            return@withContext httpResult
//        }
//        return response
//    }
//
//    //    4
//    private fun userJsonTask(result: String) {
//        val tvUserName: TextView = findViewById(R.id.tvUserName)
//        val tvAddress: TextView = findViewById(R.id.tvAddress)
////        val tvAll: TextView = findViewById(R.id.tvAll)
//
////    まず３で取得したjson全体の生成
//        val jsonObj = JSONObject(result)
////        全体の中のnameを抽出
////        val userName = jsonObj.getString("name")
////        tvUserName.text = userName
//
////        全体の中のusersの中のnameを抽出
//        val userJSONArray = jsonObj.getJSONArray("users")
//        val userJSON = userJSONArray.getJSONObject(0)
//        val userName = userJSON.getString("name")
//        tvUserName.text = userName
//
////        val userJSONArray = jsonObj.getJSONArray("users")
////        val userJSON = userJSONArray.getJSONObject(0)
//        val userAddress = userJSON.getString("email")
//        tvAddress.text = userAddress
//
////        val userAll = jsonObj.toString()
////        tvAll.text = userAll
//    }
//
//    // 4-2
//    private fun futureJsonTask(result: String) {
//        val tvAll: TextView = findViewById(R.id.tvAll)
//        val jsonObj = JSONObject(result)
//        val userAll = jsonObj.toString()
//        tvAll.text = userAll
//
//    }
//}