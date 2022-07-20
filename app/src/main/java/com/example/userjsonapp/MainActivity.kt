package com.example.userjsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        APIキーとURLの定義
        val apiKey = "cL-kMqHaNUhqcDxYoQEXaGwAjVFHm_Wo4GCoF8Avnlo"
        val mainUrl = "http://10.0.2.2:3000"
//        viewを取得
        val btnUser: Button = findViewById(R.id.btnUser)
        val btnFuture: Button = findViewById(R.id.btnFuture)
        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvAdress: TextView = findViewById(R.id.tvAdress)
        val tvAll: TextView = findViewById(R.id.tvAll)
        val btnClear: Button = findViewById(R.id.btnClear)

//        btnUser押されたら
        btnUser.setOnClickListener {
//            UserのURLを取得
            val userUrl = "$mainUrl/users?code=$apiKey"

//            URLを基に得られた情報の表示
//            コルーチン作成・HTTP通信（ワーカースレッド）・Userデータ表示
            userTask(userUrl) //中身は２へ

        }

        //btnClear押されたら
        btnClear.setOnClickListener {
            tvUserName.text = "ユーザー名"
            tvAdress.text = "アドレス"
            tvAll.text = "全情報"
        }
        // btnFuture押されたら
        btnFuture.setOnClickListener {
            val userUrl = "$mainUrl/users?code=$apiKey"
            futureTask(userUrl) //中身は2−２

        }
    }

    //    ２
    private fun userTask(userUrl: String) {
//        コルーチンスコープを作成
        lifecycleScope.launch {
//            3-2.HTTP通信
            val result = userBackGroundTask(userUrl)
//            4-2.データ表示
            userJsonTask(result)

        }
    }

    // 2−２
    private fun futureTask(userUrl: String) {
//        コルーチンスコープを作成
        lifecycleScope.launch {
//            3-2.HTTP通信
            val result = futureBackGroundTask(userUrl)
//            4-2.データ表示
            futureJsonTask(result)
        }
    }

    //    3の中身
    private suspend fun userBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
//        withContextはスレッドを分離しますよ。
//        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
        val response = withContext(Dispatchers.IO) {
//          取得データ（json形式）を入れる変数の宣言
            var httpResult = ""
//            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
            try {
                val urlObj = URL(userUrl)
//                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
//                これはここだけで動画が一本できるほどややこしいので説明割愛
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
//                httpResult = br.toString() これだとエミュレータでエラーになる
                httpResult = br.readText()
            } catch (e: IOException) {
                e.printStackTrace() //エラー出たよって言う。
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return@withContext httpResult
        }
        return response
    }

    // 3-2
    private suspend fun futureBackGroundTask(userUrl: String): String { //suspendは中断する可能性のある関数に付ける
//        withContextはスレッドを分離しますよ。
//        Dispatcherはどのスレッドで動かすか。mainがメイン、IOがワーカースレッド。
        val response = withContext(Dispatchers.IO) {
//          取得データ（json形式）を入れる変数の宣言
            var httpResult = ""
//            try エラーがあるかもしれないけどやりたい処理をかくcatchエラーになった時の処理をかく
            try {
                val futureObj = URL(userUrl)
//                テキストファイルを読み込むクラス（文字コードを読めるようにする準備（URLオブジェクト）））
//                これはここだけで動画が一本できるほどややこしいので説明割愛
                val br = BufferedReader(InputStreamReader(futureObj.openStream()))
//                httpResult = br.toString() これだとエミュレータでエラーになる
                httpResult = br.readText()
            } catch (e: IOException) {
                e.printStackTrace() //エラー出たよって言う。
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return@withContext httpResult
        }
        return response
    }

    //    4
    private fun userJsonTask(result: String) {
        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvAdress: TextView = findViewById(R.id.tvAdress)
//        val tvAll: TextView = findViewById(R.id.tvAll)

//    まず３で取得したjson全体の生成
        val jsonObj = JSONObject(result)
//        全体の中のnameを抽出
//        val userName = jsonObj.getString("name")
//        tvUserName.text = userName

//        全体の中のusersの中のnameを抽出
        val userJSONArray = jsonObj.getJSONArray("users")
        val userJSON = userJSONArray.getJSONObject(0)
        val userName = userJSON.getString("name")
        tvUserName.text = userName

//        val userJSONArray = jsonObj.getJSONArray("users")
//        val userJSON = userJSONArray.getJSONObject(0)
        val userAdress = userJSON.getString("email")
        tvAdress.text = userAdress

//        val userAll = jsonObj.toString()
//        tvAll.text = userAll
    }

    // 4-2
    private fun futureJsonTask(result: String) {
        val tvAll: TextView = findViewById(R.id.tvAll)
        val jsonObj = JSONObject(result)
        val userAll = jsonObj.toString()
        tvAll.text = userAll

    }
}