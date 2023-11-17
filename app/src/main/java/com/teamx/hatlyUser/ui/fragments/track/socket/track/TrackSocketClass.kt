package com.teamx.hatlyUser.ui.fragments.track.socket.track

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.ExceptionData
import com.teamx.hatlyUser.ui.fragments.track.socket.track.model.rider.TrackRiderModel
import com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop.TrackShopModel
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber

object TrackSocketClass {
    private var trackSocket: Socket? = null

    fun connect2(
        token: String, orderId: String, getTrackDataCallBack: GetTrackDataCallBack
    ) {

        val options = IO.Options()
        val headers: MutableMap<String, List<String>> = HashMap()




        headers["Authorization"] = listOf(token)

        options.extraHeaders = headers

        val u: Boolean? = trackSocket?.connected()

        if (u == null) {
            trackSocket = IO.socket("http://31.220.17.28:8000/track", options)
        } else if (!u) {
            trackSocket = IO.socket("http://31.220.17.28:8000/track", options)
        }

        trackSocket?.connect()

        Timber.tag("TrackSocketClass").d("EVENT_CONNECT: ${trackSocket?.connected()}")

        trackSocket?.on(Socket.EVENT_CONNECT) {


            initiateTrack(orderId = orderId)

            trackSocket?.on("CURRENT_LOCATION") { args ->
                try {
                    val updateLocation = args[0].toString()

                    Log.d("TrackSocketClass", "CURRENT_LOCATION ${updateLocation}")
                    getTrackDataCallBack.getUpdatedLatLng(updateLocation)
                } catch (e: java.lang.Exception) {
                    Log.d("TrackSocketClass", "CURRENT_LOCATION: ${args[0]}")
                }
            }

//            userMessageSocket?.on("REMANING_TIME") { args ->
//                Log.d("TrackSocketClass", "REMANING_TIME: }${args[0]}")
//            }

            trackSocket?.on("REMANING_TIME") { args ->
                try {
                    val remainingdata = args[0].toString()

                    Log.d("TrackSocketClass", "REMANING_TIME ${remainingdata}")
                    getTrackDataCallBack.getRemainingdata(remainingdata)
                } catch (e: java.lang.Exception) {
                    Log.d("TrackSocketClass", "REMANING_TIME: ${args[0]}")
                }
            }

            onListenerEverything(getTrackDataCallBack)



            shopInformation(orderId = orderId)


        }
        trackSocket?.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.tag("TrackSocketClass")
                .d("EVENT_CONNECT_ERROR22:${trackSocket?.connected()} ${it[0]}")

        }

    }

    val gson = Gson()


    @SuppressLint("LogNotTimber")
    private fun onListenerEverything(callback2: GetTrackDataCallBack) {
        trackSocket?.on("exception") { args ->
            try {
                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
                Log.d("TrackSocketClass", "ExceptionData: ${args[0]}")
            } catch (e: java.lang.Exception) {
                Log.d("TrackSocketClass", "ExceptionData: ${args[0]}")
            }
        }

        trackSocket?.on("TRACKING_STARTED") { args ->
            Log.d("TrackSocketClass", "TRACKING_STARTED: }${args[0]}")
        }

        trackSocket?.on("TRACKING_LEAVED") { args ->
            Log.d("TrackSocketClass", "TRACKING_LEAVED: }${args[0]}")
        }

//        userMessageSocket?.on("PICKED_BY") { args ->
//            Log.d("TrackSocketClass", "PICKED_BY: }${args[0]}")
//        }

        trackSocket?.on("CURRENT_STATUS") { args ->
            try {
                val currentStatus = args[0].toString()

                Log.d("TrackSocketClass", "CURRENT_STATUS ${currentStatus}")
                callback2.getCurrentStatus(currentStatus)
            } catch (e: java.lang.Exception) {
                Log.d("TrackSocketClass", "CURRENT_STATUS: ${args[0]}")
            }
        }



//        userMessageSocket?.on("SHOP") { args ->
//            Log.d("TrackSocketClass", "SHOP: }${args[0]}")
//        }


        trackSocket?.on("PICKED_BY") { args ->
            try {
                val trackRiderModel = gson.fromJson(args[0].toString(), TrackRiderModel::class.java)
                Log.d("TrackSocketClass", "PICKED_BY ${trackRiderModel}")
                callback2.getRiderData(trackRiderModel)
            } catch (e: java.lang.Exception) {
                Log.d("TrackSocketClass", "PICKED_BY: ${args[0]}")
            }
        }

        trackSocket?.on("SHOP") { args ->
            try {
                val trackShopModel = gson.fromJson(args[0].toString(), TrackShopModel::class.java)
                Log.d("TrackSocketClass", "SHOP ${trackShopModel}")
                callback2.getShopData(trackShopModel)
            } catch (e: java.lang.Exception) {
                Log.d("TrackSocketClass", "RECIEVED_MESSAGE: ${args[0]}")
            }
        }

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
        trackSocket?.emit("TRACK_ORDER", data, object : Ack {
            override fun call(vararg args: Any?) {
                Log.d("TrackSocketClass", "initiatedTRACK_ORDER: ")
            }
        })
    }

    private fun shopInformation(orderId: String) {
        val arr = listOf(orderId)
        val data = JSONObject().put("orderId", orderId)
        Log.d("TrackSocketClass", "initiateChat:$data ")
        trackSocket?.emit("SHOP_INFORMATION", data, object : Ack {
            override fun call(vararg args: Any?) {
                Log.d("TrackSocketClass", "SHOP_INFORMATION: ")
            }
        })
    }

    interface GetTrackDataCallBack {
        fun getShopData(trackShopModel: TrackShopModel)
        fun getRiderData(trackRiderModel: TrackRiderModel)
        fun getRemainingdata(remainingdata: String)
        fun getCurrentStatus(currentStatus: String)
        fun getUpdatedLatLng(latLng: String)
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
        trackSocket?.disconnect()
        trackSocket?.on(Socket.EVENT_DISCONNECT) {
            Log.d("TrackSocketClass", "EVENT_DISCONNECT: ${trackSocket?.connected()}")

        }
    }


}