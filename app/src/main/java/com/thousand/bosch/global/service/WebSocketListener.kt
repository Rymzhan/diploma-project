package com.thousand.bosch.global.service

import com.thousand.bosch.model.socket.SocketMainResponseUser
import com.thousand.bosch.model.ws.Event
import com.google.gson.Gson
import io.reactivex.Flowable
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.reactivestreams.Subscriber
import timber.log.Timber

class WebSocketListener : WebSocketListener() {

    private var getMessageSocketResponse: Subscriber<in SocketMainResponseUser>? = null

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Timber.i("WEB_SOCKET onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Timber.i("WEB_SOCKET onClosed")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Timber.i("WEB_SOCKET onOpen")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Timber.i("WEB_SOCKET onFailure ${t.message} response ${response.toString()}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Timber.i("WEB_SOCKET onMessage $text")
        val jsonObject = Event.getSimpleJsonObject(text)
        val message = Gson().fromJson(jsonObject, SocketMainResponseUser::class.java)
        getMessageSocketResponse?.onNext(message)

    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.i("CUST onMessage1")
    }

    fun getMessageSocket(): Flowable<SocketMainResponseUser> = Flowable.fromPublisher {
        getMessageSocketResponse = it
    }
}