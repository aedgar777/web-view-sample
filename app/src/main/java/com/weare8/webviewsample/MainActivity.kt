package com.weare8.webviewsample

import android.os.Bundle
import android.webkit.CookieManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
class MainActivity : AppCompatActivity() {



    private val retrofitUat = buildUatRetrofitClient()
    private val retrofitProd = buildProdRetrofitClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.uatOnboardingButton).setOnClickListener {
            startActivity(WebActivity.newIntent(this,"https://ee.s-uat.test.aws.the8app.com/onboarding?token=447996717219%7CPAYM%7C1612878589%7C30450221009422dc94b321587a43a57776b24e8b899148a974b77f0c7a66a0fb51dc626ff702205b4633ddb3bc8c3896ca5abc3e7d606755597e82ce848b54e6397a2e4737b1b9"))
        }

        findViewById<Button>(R.id.uatWalletButton).setOnClickListener {
            startActivity(WebActivity.newIntent(this,"https://ee.s-uat.test.aws.the8app.com/wallet?token=447996717219%7CPAYM%7C1612878589%7C30450221009422dc94b321587a43a57776b24e8b899148a974b77f0c7a66a0fb51dc626ff702205b4633ddb3bc8c3896ca5abc3e7d606755597e82ce848b54e6397a2e4737b1b9"))
        }

        findViewById<Button>(R.id.prodOnboardingButton).setOnClickListener {
            startActivity(WebActivity.newIntent(this,"https://ee.s.weare8.com/onboarding?token=447973158966%7CPAYM%7C1618874855%7C3044022042c94a3471306398dbb5ff19776e2a31e8bac99d803757f7a83203f6f1bfa4e50220725b40e6ccf7474613f9dc9148ce9824042c66b359a049194478c3e520d7d6b5"))
        }

        findViewById<Button>(R.id.prodWalletButton).setOnClickListener {
            startActivity(WebActivity.newIntent(this,"https://ee.s.weare8.com/wallet?token=447973158966%7CPAYM%7C1618874855%7C3044022042c94a3471306398dbb5ff19776e2a31e8bac99d803757f7a83203f6f1bfa4e50220725b40e6ccf7474613f9dc9148ce9824042c66b359a049194478c3e520d7d6b5"))
        }

        findViewById<Button>(R.id.makeCallButton).setOnClickListener {
            makeApiCalls()
        }

        findViewById<Button>(R.id.clearCacheButton).setOnClickListener {
            baseContext.cacheDir.deleteRecursively()
           CookieManager.getInstance().removeAllCookies(null)
        }

    }

    override fun onResume() {
        super.onResume()

        makeApiCalls()
    }


    fun makeApiCalls(){

        val uatClient = retrofitUat.create(ApiClient::class.java)
        val prodClient = retrofitProd.create(ApiClient::class.java)

            uatClient.getUatInfo().enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                if (response.isSuccessful){
                    findViewById<TextView>(R.id.uatResponse).text = response.body().toString()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                findViewById<TextView>(R.id.uatResponse).text ="Call failed because ${t.message.toString()}"
            }
        })


        prodClient.getProdInfo().enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                if (response.isSuccessful){
                    findViewById<TextView>(R.id.prodResponse).text = response.body().toString()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                findViewById<TextView>(R.id.prodResponse).text ="Call failed because ${t.message.toString()}"
            }
        })

    }

    private fun buildUatRetrofitClient():Retrofit{

        val API_BASE_URL = "https://api-uat.test.aws.the8app.com"

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJuYmYiOjE2MTkxOTY1MzYsImV4cCI6MTYyMTc4ODUzNiwiaXNzIjoiaHR0cHM6Ly9hcGktdWF0LnRlc3QuYXdzLnRoZThhcHAuY29tL2VpZ2h0YXV0aGFwaSIsImF1ZCI6InVzZXJzLmFwaSIsImNsaWVudF9pZCI6ImJ0LnVrLmNsaWVudCIsInNjb3BlIjpbInVzZXJzLmFwaSJdfQ.b42psnnBWQ74X_HzPdmuopl2dMlIVlFpUd2-ytCodOwOqPAQPeodUCeR0EQHl_7HiDCJzl0URZKQ7DGf4Yql8yiBESx185FIkc8zUhmSlLDCuxe9HsAyv595cSoJhbkZzAJ6y-oFxOLQ2uhGbS0zTdcVlaDktVB8f0NP9BMNFUwlZINCCltim-_zYY97_8LJoMl1on-u9IUt46xO0DlbaxCly6XTw3Lny2jz4Yrf4LpSPy-JoVTAGhJdt2skon_HwcEOdo0X-uz7VjeudmaRCErCU-Fz25knXf_EPNZCW25qh9bXYhTE9AQFYp6-xN1IEu0zY9H0t2gU2c66P3u1TA")
                .build()
            chain.proceed(newRequest)
        }.build()

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

        return builder.client(client).build()

    }

    private fun buildProdRetrofitClient():Retrofit{

        val API_BASE_URL = "https://api-prod.prod.aws.the8app.com"
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJuYmYiOjE2MTkxOTY1OTAsImV4cCI6MTYyMTc4ODU5MCwiaXNzIjoiaHR0cHM6Ly9hcGktcHJvZC5wcm9kLmF3cy50aGU4YXBwLmNvbS9laWdodGF1dGhhcGkiLCJhdWQiOiJ1c2Vycy5hcGkiLCJjbGllbnRfaWQiOiJidC51ay5jbGllbnQiLCJzY29wZSI6WyJ1c2Vycy5hcGkiXX0.Nt56Wj2mmx9rF3KKkSHhH2_GupQ2fyNVl8lymiQ0MNAjgc4TI4TJzwIoN3KdYj6ZEQnpso84scu2LLBLAXHZCixOws-dPQmF-VjdrsB8uJ78XjgX1eHgzS6mBAtDFvKjzoYUUbbHmF0Vtx7FSWkPQM1-Pv2X6S4eCdfqY9nQjNj0Ca72tuRwjSsALeHhJhszcBizusR2BrT301T5DRb9oG-TNzxeTERyMdJ1ydbUBSjstuQDJqvI0B9Vt1tWmmrxCI0VU0ZsGFknr5olk8VlH9_XeoChCSSLDEETe7lGz5X4Dzp5I9lsBWiAYyw8420xYpjq9p05Y7jBmHizVSm6ng")
                .build()
            chain.proceed(newRequest)
        }.build()

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

      return builder.client(client).build()

    }

    public interface ApiClient{

        @GET("eightusersapi/partner-wallet-info/447996717219")
        fun getUatInfo():Call<JsonObject>

        @GET("eightusersapi/partner-wallet-info/447973158966")
        fun getProdInfo(
        ):Call<JsonObject>
    }


}