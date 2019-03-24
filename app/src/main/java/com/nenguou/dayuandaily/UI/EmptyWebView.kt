package com.nenguou.dayuandaily.UI

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nenguou.dayuandaily.R

class EmptyWebView : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var empty_webview_toolbar_title : TextView
    lateinit var type:String
    lateinit var m_url:String
    lateinit var webView: WebView
    lateinit var net1 : NestedScrollView
    lateinit var net2 : NestedScrollView

    var unbinder : Unbinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.myAppTheme)
        setContentView(R.layout.activity_empty_web_view)
        initId()
        initViews()
        setListener()
    }

    fun initId(){
        type = intent.getStringExtra("tag");
        toolbar = findViewById(R.id.empty_webview_toolbar);
        setSupportActionBar(toolbar)
        empty_webview_toolbar_title = findViewById(R.id.empty_webview_toolbar_title)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        webView = findViewById(R.id.empty_webview)
        net1 = findViewById(R.id.webview_net1)
        net2 = findViewById(R.id.webview_net2)
    }

    fun initViews(){
        toolbar.setTitle("")
        when(type){
            "bus" -> {
                empty_webview_toolbar_title.setText("实时公交")
                m_url = "http://bus.powerpeak.top/Bus/QueryBus/Index?city=%E6%A6%86%E6%AC%A1#/"
            }
            "school_card" -> {
                empty_webview_toolbar_title.setText("一卡通")
                m_url = "http://www.poscard-app.com.cn/app/"
                net1.visibility = View.GONE
                net2.visibility = View.VISIBLE
            }
            "library" -> {
                empty_webview_toolbar_title.text = "我去图书馆"
                m_url = "http://wechat.v2.traceint.com/index.php/register.html"
                Toast.makeText(this,"微信关注公众号「我去图书馆」使用此功能！",Toast.LENGTH_LONG).show()
            }
            "putonghua" -> {
                empty_webview_toolbar_title.text = "普通话水平测试成绩查询系统"
                m_url = "http://shanxi.cltt.org/Web/Login/PSCP01001.aspx"
            }
            "calender" ->{
                empty_webview_toolbar_title.text = "校历"
                m_url = "https://ssl.liuyinxin.com/static/img/calendar.jpg"
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.loadUrl(m_url)
        webView.webViewClient = WebViewClient()

    }

    fun setListener(){
        toolbar.setNavigationOnClickListener {
            this.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_setting,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.copy_url -> {
                val clipboardManager :ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboardManager.primaryClip = ClipData.newPlainText("url",m_url)
                Toast.makeText(this,"复制成功",Toast.LENGTH_SHORT).show()
            }
            R.id.open_in_browser -> {
                val intent = Intent()
                intent.setAction("android.intent.action.VIEW")
                val uri = Uri.parse(m_url)
                intent.setData(uri)
                startActivity(intent)
            }
            R.id.share_url -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                intent.putExtra(Intent.EXTRA_TEXT,m_url + " from 「大猿日常」https://fir.im/DayuanDaily")
                startActivity(Intent.createChooser(intent,"分享"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack()
            return  true
        }else{
            this.finish()
        }
        return true
    }
}