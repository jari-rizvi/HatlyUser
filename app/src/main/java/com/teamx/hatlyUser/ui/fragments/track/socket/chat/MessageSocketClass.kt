package com.teamx.hatlyUser.ui.fragments.track.socket.chat

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.teamx.hatlyUser.constants.AppConstants.ApiConfiguration.Companion.APP_URL
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.Doc
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.GetAllMessageData
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber

object MessageSocketClass {
    var userMessageSocket: Socket? = null

    fun connect2(token: String, orderId: String, callback1: ReceiveSendMessageCallback, callback: GetAllMessageCallBack
    ) {

        val options = IO.Options()
        val headers: MutableMap<String, List<String>> = HashMap()


        headers["Authorization"] = listOf(token)

        options.extraHeaders = headers

        val u: Boolean? = userMessageSocket?.connected()

        if (u == null) {
            userMessageSocket = IO.socket("${APP_URL}chat",options)
        } else if (!u) {
            userMessageSocket = IO.socket("${APP_URL}chat", options)


        }

        userMessageSocket?.connect()
        Timber.tag("MessageSocketClass").d("EVENT_CONNECT: ${userMessageSocket?.connected()}")

        userMessageSocket?.on(Socket.EVENT_CONNECT) {
            onListenerEverything(callback,callback1)
            Timber.tag("MessageSocketClass").d("EVENT_CONNECT1: ${userMessageSocket?.connected()}")
            initiateChat(orderId = orderId)
            getAllMessage(5,5, callback)


//            userMessageSocket?.disconnect()


            Timber.tag("MessageSocketClass").d("EVENT_CONNECT12: ${userMessageSocket?.connected()}")

            sendMessageTo(callback1)

            getAllMessagesListener(callback)

        }
        userMessageSocket?.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.tag("MessageSocketClass")
                .d("EVENT_CONNECT_ERROR22:${userMessageSocket?.connected()} ${it[0]}")

        }

    }

    val gson = Gson()


    @SuppressLint("LogNotTimber")
    private fun onListenerEverything(callback: GetAllMessageCallBack, callback2: ReceiveSendMessageCallback) {
        userMessageSocket?.on("exception") { args ->

            try {
                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
//                Timber.tag("MessageSocketClass").d("ExceptionData: ${exception.message}")
                Log.d("MessageSocketClass", "ExceptionData: ${args[0]}")
            } catch (e: java.lang.Exception) {
//                Timber.tag("MessageSocketClass").d("ExceptionData: ${args[0]}")
                Log.d("MessageSocketClass", "ExceptionData: ${args[0]}")
                
            }

        }
        userMessageSocket?.on("CHAT_INICIATED") { args ->

//            Timber.tag("MessageSocketClass").d("ExceptionData2: }${args[0]}")
            Log.d("MessageSocketClass", "ExceptionData2: }${args[0]}")
//            sendMessageTo("${args.get(0)}")

        }
        userMessageSocket?.on("RECEIVE_MESSAGE") { args ->
            Timber.tag("MessageSocketClass").d("RECEIVE_MESSAGE: }${args[0]}")
            try {
                val receiveMessage = gson.fromJson(args[0].toString(), Doc::class.java)

//                Timber.tag("MessageSocketClass").d("RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}")

                Log.d("MessageSocketClass", "RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}")
                callback2.onGetReceiveMessage(receiveMessage)
            } catch (e: java.lang.Exception) {
//                Timber.tag("MessageSocketClass").d("RECIEVED_MESSAGE: ${args[0]}")
                Log.d("MessageSocketClass", "RECIEVED_MESSAGE: ${args[0]}")
            }




        }
        userMessageSocket?.on("CHAT_HISTORY") { args ->
//            Timber.tag("MessageSocketClass").d("CHAT_HISTORY: }${args.get(0)}")
            Log.d("MessageSocketClass", "CHAT_HISTORY: }${args.get(0)}")

        }
        userMessageSocket?.on("CHAT_LEVED") { args ->
//            Timber.tag("MessageSocketClass").d("CHAT_LEVED: }${args.get(0)}")
            Log.d("MessageSocketClass", "CHAT_LEVED: }${args[0]}")
        }
        userMessageSocket?.on("ALL_CHATS_READED") { args ->
//            Timber.tag("MessageSocketClass").d("ALL_CHATS_READED: }${args.get(0)}")
            Log.d("MessageSocketClass", "ALL_CHATS_READED: }${args[0]}")
        }
        userMessageSocket?.on("READED_SUCCESSFULLY") { args ->
//            Timber.tag("MessageSocketClass").d("READED_SUCCESSFULLY: }${args.get(0)}")
            Log.d("MessageSocketClass", "READED_SUCCESSFULLY: }${args[0]}")

        }
    }

    private fun getAllMessagesListener(callback: GetAllMessageCallBack) {
        userMessageSocket?.on("CHAT_HISTORY") { args ->
            try {
                val exception = gson.fromJson(args[0].toString(), GetAllMessageData::class.java)
                Log.d("MessageSocketClass", "Get_ALL_MESSAGES: ${exception.docs.size}")
//                Timber.tag("MessageSocketClass").d("Get_ALL_MESSAGES: ${exception.docs.size}")
                callback.onGetAllMessage(exception)
            } catch (e: Exception) {
                Log.d("MessageSocketClass", "Get_ALL_MESSAGES: ${args[0]}")
//                Timber.tag("MessageSocketClass").d("Get_ALL_MESSAGES: ${args[0]}")
            }
        }
    }


    fun exception() {
        userMessageSocket?.on("exception") { args ->
            try {
                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
                Log.d("MessageSocketClass", "ExceptionData: ${exception.message}")
//                Timber.tag("MessageSocketClass").d("ExceptionData: ${exception.message}")
            } catch (e: java.lang.Exception) {
                Log.d("MessageSocketClass", "ExceptionData: ${args[0]}")
//                Timber.tag("MessageSocketClass").d("ExceptionData: ${args[0]}")
            }
        }
    }



//      private fun getAllChats2(callback: GetAllChatCallBack) {
//          userMessageSocket?.on("Get_ALL_CHATS") { args ->
//              try {
//                  val allChatsData = gson.fromJson(args[0].toString(), GetAllChatsData::class.java)
//                  callback.getAllChatsModel(allChatsData)
//                  Timber.tag("MessageSocketClass").d("Get_ALL_CHATS: ${allChatsData.size}")
//              } catch (e: java.lang.Exception) {
//                  Timber.tag("MessageSocketClass").d("Get_ALL_CHATS: ${args[0]}")
//              }
//          }
//      }*/




    var roomId: String = ""
    var from: String = ""
    var iAm: String = ""

    fun sendMessageTo(
        content: String, callback: ReceiveSendMessageCallback
    ) {
        val data = JSONObject().put(
            "message",
            content
        )/*.put("from", *//*this.*//*iAm).put("room_id", roomId)*/

        userMessageSocket?.emit("SEND_MESSAGE", data, object : Ack {
            override fun call(vararg args: Any?) {
                Timber.tag("MessageSocketClass").d("callSEND_MESSAGE: ")
                callback.responseMessage2(content)
            }
        })

    }

    private fun sendMessageTo(callback: ReceiveSendMessageCallback) {
        userMessageSocket?.on ("ALL_CHAT") { args ->
            try {
                val receiveMessage = gson.fromJson(args[0].toString(), Doc::class.java)
                Log.d("MessageSocketClass", "RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}")
//                Timber.tag("MessageSocketClass").d("RECIEVED_MESSAGE1212: ${args[0].toString()} message12312312321 ${receiveMessage.message}"
//                )
                callback.onGetReceiveMessage(receiveMessage)
            } catch (e: java.lang.Exception) {
                Log.d("MessageSocketClass", "RECIEVED_MESSAGE: ${args[0]}")
//                Timber.tag("MessageSocketClass").d("RECIEVED_MESSAGE: ${args[0]}")
            }
        }

    }


    fun initiateChat(orderId: String) {
        val arr = listOf(orderId)
//        val data = JSONObject().put("orderId", "$arr")
        val data = JSONObject().put("orderId", orderId)
//        Timber.tag("MessageSocketClass").d("initiateChat:$data ")
        Log.d("MessageSocketClass", "initiateChat:$data ")
        userMessageSocket?.emit("INICIATE_CHAT", data, object : Ack {
            override fun call(vararg args: Any?) {
                Log.d("MessageSocketClass", "initiatedChat: ")
//                Timber.tag("MessageSocketClass").d("initiatedChat: ")
            }
        })
    }

    interface GetAllMessageCallBack {
        fun onGetAllMessage(getAllChatsData: GetAllMessageData)
        fun responseMessage()
    }

    interface ReceiveSendMessageCallback {
        fun onGetReceiveMessage(getAllChatsData: Doc)
        fun responseMessage2(str: String)
//        fun responseMessage2(str: String, strImg: String)
    }

    fun getAllMessage(
        limit: Int, page : Int, callback: GetAllMessageCallBack
    ) {
        val data = JSONObject().put("limit", 5).put("page",1)

//        Timber.tag("MessageSocketClass").d("getallmessage:$data ")
        Log.d("MessageSocketClass", "getallmessage:$data ")
        userMessageSocket?.emit("ALL_CHAT", data, object : Ack {
            override fun call(vararg args: Any?) {
//                Timber.tag("MessageSocketClass").d("Recieve12121212")
                Log.d("MessageSocketClass", "Recieve12121212")
                callback.responseMessage()
            }
        })
        getAllMessagesListener(callback)

    }


    fun disconnect() {
        userMessageSocket?.disconnect()
        userMessageSocket?.on(Socket.EVENT_DISCONNECT) {
            Log.d("MessageSocketClass", "EVENT_DISCONNECT: ${userMessageSocket?.connected()}")
//            Timber.tag("MessageSocketClass")
//                .d("EVENT_DISCONNECT: ${userMessageSocket?.connected()}")
        }
    }


    //Events to listen for
    //MEssage to Emit
    //Listener provide runtime data
    //always disconnect socket when changing fragment
    //always start socket when when id is available
    //always disconnect socket and null it if it is not connect for the first three time otherwise it will take memory and crash


}