package com.network;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveString(TCPConnection tcpConnection, String value);//get string
    void onDisconnect(TCPConnection tcpConnection);//disconect
    void onException(TCPConnection tcpConnection, Exception e);
}
