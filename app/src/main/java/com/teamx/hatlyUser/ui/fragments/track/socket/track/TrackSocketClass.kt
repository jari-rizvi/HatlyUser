package com.teamx.hatlyUser.ui.fragments.track.socket.track

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.ExceptionData
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.MessageSocketClass.getAllMessage
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber

object TrackSocketClass {
    private var userMessageSocket: Socket? = null

    fun connect2(
        token: String, orderId: String, getTrackDataCallBack: GetTrackDataCallBack
    ) {

        val options = IO.Options()
        val headers: MutableMap<String, List<String>> = HashMap()


        headers["Authorization"] = listOf(token)

        options.extraHeaders = headers

        val u: Boolean? = userMessageSocket?.connected()

        if (u == null) {
            userMessageSocket = IO.socket("http://192.168.100.33:8000/track", options)
        } else if (!u) {
            userMessageSocket = IO.socket("http://192.168.100.33:8000/track", options)
        }

        userMessageSocket?.connect()

        Timber.tag("TrackSocketClass").d("EVENT_CONNECT: ${userMessageSocket?.connected()}")

        userMessageSocket?.on(Socket.EVENT_CONNECT) {


            initiateTrack(orderId = orderId)

            userMessageSocket?.on("CURRENT_LOCATION") { args ->
                Log.d("TrackSocketClass", "CURRENT_LOCATION: }${args[0]}")
            }

            userMessageSocket?.on("REMANING_TIME") { args ->
                Log.d("TrackSocketClass", "REMANING_TIME: }${args[0]}")
            }

            onListenerEverything(getTrackDataCallBack)



            shopInformation(orderId = orderId)


        }
        userMessageSocket?.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.tag("TrackSocketClass")
                .d("EVENT_CONNECT_ERROR22:${userMessageSocket?.connected()} ${it[0]}")

        }

    }

    val gson = Gson()


    @SuppressLint("LogNotTimber")
    private fun onListenerEverything(callback2: GetTrackDataCallBack) {
        userMessageSocket?.on("exception") { args ->
            try {
                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
                Log.d("TrackSocketClass", "ExceptionData: ${args[0]}")
            } catch (e: java.lang.Exception) {
                Log.d("TrackSocketClass", "ExceptionData: ${args[0]}")
            }
        }

        userMessageSocket?.on("TRACKING_STARTED") { args ->
            Log.d("TrackSocketClass", "TRACKING_STARTED: }${args[0]}")
        }

        userMessageSocket?.on("TRACKING_LEAVED") { args ->
            Log.d("TrackSocketClass", "TRACKING_LEAVED: }${args[0]}")
        }

        userMessageSocket?.on("TRACKING_LEAVED") { args ->
            Log.d("TrackSocketClass", "TRACKING_LEAVED: }${args[0]}")
        }

        userMessageSocket?.on("PICKED_BY") { args ->
            Log.d("TrackSocketClass", "PICKED_BY: }${args[0]}")
        }

        userMessageSocket?.on("CURRENT_STATUS") { args ->
            Log.d("TrackSocketClass", "CURRENT_STATUS: }${args[0]}")
        }



        userMessageSocket?.on("SHOP") { args ->
            Log.d("TrackSocketClass", "SHOP: }${args[0]}")
        }



//        userMessageSocket?.on("RECEIVE_MESSAGE") { args ->
//            Timber.tag("TrackSocketClass").d("RECEIVE_MESSAGE: }${args[0]}")
//            try {
//                val receiveMessage = gson.fromJson(args[0].toString(), Doc::class.java)
//                Log.d("TrackSocketClass", "RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}")
//                callback2.onGetReceiveMessage(receiveMessage)
//            } catch (e: java.lang.Exception) {
//                Log.d("TrackSocketClass", "RECIEVED_MESSAGE: ${args[0]}")
//            }
//        }
//        userMessageSocket?.on("CHAT_HISTORY") { args ->
//            Log.d("TrackSocketClass", "CHAT_HISTORY: }${args.get(0)}")
//
//        }
//        userMessageSocket?.on("CHAT_LEVED") { args ->
//            Log.d("TrackSocketClass", "CHAT_LEVED: }${args[0]}")
//        }
//        userMessageSocket?.on("ALL_CHATS_READED") { args ->
//            Log.d("TrackSocketClass", "ALL_CHATS_READED: }${args[0]}")
//        }
//        userMessageSocket?.on("READED_SUCCESSFULLY") { args ->
//            Log.d("TrackSocketClass", "READED_SUCCESSFULLY: }${args[0]}")
//
//        }
    }

//    private fun getAllMessagesListener(callback: GetAllMessageCallBack) {
//        userMessageSocket?.on("CHAT_HISTORY") { args ->
//            try {
//                val exception = gson.fromJson(args[0].toString(), GetAllMessageData::class.java)
//                Log.d("TrackSocketClass", "Get_ALL_MESSAGES: ${exception.docs.size}")
//                callback.onGetAllMessage(exception)
//            } catch (e: Exception) {
//                Log.d("TrackSocketClass", "Get_ALL_MESSAGES: ${args[0]}")
//            }
//        }
//    }


//    fun exception() {
//        userMessageSocket?.on("exception") { args ->
//            try {
//                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
//                Log.d("TrackSocketClass", "ExceptionData: ${exception.message}")
//            } catch (e: java.lang.Exception) {
//                Log.d("TrackSocketClass", "ExceptionData: ${args[0]}")
//            }
//        }
//    }


//    var roomId: String = ""
//    var from: String = ""
//    var iAm: String = ""
//
//    fun sendMessageTo(content: String, callback: ReceiveSendMessageCallback) {
//        val data = JSONObject().put(
//            "message",
//            content
//        )/*.put("from", *//*this.*//*iAm).put("room_id", roomId)*/
//
//        userMessageSocket?.emit("SEND_MESSAGE", data, object : Ack {
//            override fun call(vararg args: Any?) {
//                Timber.tag("TrackSocketClass").d("callSEND_MESSAGE: ")
//                callback.responseMessage2(content)
//            }
//        })
//
//    }

//    private fun sendMessageTo(callback: ReceiveSendMessageCallback) {
//        userMessageSocket?.on ("ALL_CHAT") { args ->
//            try {
//                val receiveMessage = gson.fromJson(args[0].toString(), Doc::class.java)
//                Log.d("TrackSocketClass", "RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}")
//                callback.onGetReceiveMessage(receiveMessage)
//            } catch (e: java.lang.Exception) {
//                Log.d("TrackSocketClass", "RECIEVED_MESSAGE: ${args[0]}")
//            }
//        }
//
//    }


    private fun initiateTrack(orderId: String) {
        val arr = listOf(orderId)
        val data = JSONObject().put("orderId", orderId)
        Log.d("TrackSocketClass", "initiateChat:$data ")
        userMessageSocket?.emit("TRACK_ORDER", data, object : Ack {
            override fun call(vararg args: Any?) {
                Log.d("TrackSocketClass", "initiatedTRACK_ORDER: ")
            }
        })
    }

    private fun shopInformation(orderId: String) {
        val arr = listOf(orderId)
        val data = JSONObject().put("orderId", orderId)
        Log.d("TrackSocketClass", "initiateChat:$data ")
        userMessageSocket?.emit("SHOP_INFORMATION", data, object : Ack {
            override fun call(vararg args: Any?) {
                Log.d("TrackSocketClass", "SHOP_INFORMATION: ")
            }
        })
    }

    interface GetTrackDataCallBack {
        fun getShopData()
        fun getRiderData()
    }

//    interface ReceiveSendMessageCallback {
//        fun onGetReceiveMessage(getAllChatsData: Doc)
//        fun responseMessage2(str: String)
//    }

//    private fun getAllMessage(limit: Int, page : Int, callback: GetShopDataCallBack) {
//        val data = JSONObject().put("limit", 5).put("page",1)
//
//        Log.d("TrackSocketClass", "getallmessage:$data ")
//        userMessageSocket?.emit("ALL_CHAT", data, object : Ack {
//            override fun call(vararg args: Any?) {
//                Log.d("TrackSocketClass", "Recieve12121212")
//                callback.responseMessage()
//            }
//        })
//        getAllMessagesListener(callback)
//
//    }


    fun disconnect() {
        userMessageSocket?.disconnect()
        userMessageSocket?.on(Socket.EVENT_DISCONNECT) {
            Log.d("TrackSocketClass", "EVENT_DISCONNECT: ${userMessageSocket?.connected()}")

        }
    }


}