package com.chat.server;

import com.network.TCPConnection;
import com.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {

    public static void main(String[] args) {
        new ChatServer();

    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer(){
        System.out.println("server running...");
        try(ServerSocket serverSocket = new ServerSocket(8800)){
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());

                } catch (IOException e){
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  synchronized void onConnectionReady(com.network.TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnection("Client connect: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(com.network.TCPConnection tcpConnection, String value) {

    }

    @Override
    public synchronized void onDisconnect(com.network.TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnection("Client disconnected: " + tcpConnection);

    }

    @Override
    public synchronized void onException(com.network.TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnection(String value){
        System.out.println(value);
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).sendString(value);

        }
    }
}
