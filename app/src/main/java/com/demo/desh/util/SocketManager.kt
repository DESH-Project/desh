package com.demo.desh.util

import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

object SocketManager {
    private lateinit var socket: Socket

    fun init() {
        try { socket = Socket("http://localhost", 9000) }
        catch (e: Exception) { e.printStackTrace() }
    }

    fun send(msg: String) {
        val ost = socket.getOutputStream()
        val dos = DataOutputStream(ost)

        dos.writeUTF(msg)

        dos.close()
        ost.close()
    }

    fun recv() {
        val ist = socket.getInputStream()
        val dis = DataInputStream(ist)
        val msg = dis.readUTF()

        Log.e("recv()", msg)

        dis.close()
        ist.close()
    }
}