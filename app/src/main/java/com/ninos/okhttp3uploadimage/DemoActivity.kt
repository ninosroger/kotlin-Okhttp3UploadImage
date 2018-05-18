package com.ninos.okhttp3uploadimage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ninos.okhttp3uploadimage.network.Net
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.io.File

/**
 * Created by ninos on 18-5-18.
 */
class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var path = "your image path"
        var file = File(path)

        //create RequestBody instance from file
        var requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        var image = MultipartBody.Part.createFormData("image", file.name, requestFile)

        //if you have other parameter
        var other = MultipartBody.Part.createFormData("other", "value")

//        //多图上传
//        var files = ArrayList<File>()
//        var list = ArrayList<MultipartBody.Part>()
//        files.forEach {
//            list.add(MultipartBody.Part.createFormData(it.name, it.name, RequestBody.create(MediaType.parse("image/*"), it)))
//        }
//
//
//        //if you have other parameter
//        var other = MultipartBody.Part.createFormData("other", "value")
//
//        demoRequest(list, other)

//        interface
//        @FormUrlEncoded
//        @POST("/demo")
//        fun demoRequest(@Part list: ArrayList<MultipartBody.Part>, @Part other: MultipartBody.Part): Observable<String>


        demoRequest(image, other)
    }

    //示例demoRequest为ApiService内接口
    //subscribe内成功返回为Observable<String>中泛型类型
    fun demoRequest(image: MultipartBody.Part, other: MultipartBody.Part) {
        var subscription: Subscription = Net.getService()
                .demoRequest(image, other)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //成功返回
                }, {
                    //错误异常
                })
        addSubscription(subscription)
    }

    //单独抽取做公共类
    companion object {
        protected var mCompositeSubscription: CompositeSubscription? = null

        private fun getInstance() {
            mCompositeSubscription = Holder.compositeSubscription
        }

        fun unSubscription() {
            if (this.mCompositeSubscription != null) {
                this.mCompositeSubscription!!.unsubscribe()
            }
        }

        fun addSubscription(s: Subscription) {
            if (this.mCompositeSubscription == null) {
                getInstance()
            }
            this.mCompositeSubscription!!.add(s)
        }
    }

    private object Holder {
        val compositeSubscription: CompositeSubscription = CompositeSubscription()
    }
}