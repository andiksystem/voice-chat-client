/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andikhermawan.voice.chat.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author andik
 */
public class RecorderThread extends Thread {

    private TargetDataLine audioIn;
    private DatagramSocket datagramSocket;
    private final static byte byteBuffers[] = new byte[512];
    private InetAddress serverIp;
    private int serverPort;

    @Override
    public void run() {
        while (VoiceChatClient.isCalling()) {
            try {
                audioIn.read(byteBuffers, 0, byteBuffers.length);
                DatagramPacket data = new DatagramPacket(byteBuffers, byteBuffers.length, serverIp, serverPort);
                datagramSocket.send(data);   
            } catch (IOException ex) {
                Logger.getLogger(RecorderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        audioIn.close();
        audioIn.drain();
        System.out.println("thread stop");
    }

    public TargetDataLine getAudioIn() {
        return audioIn;
    }

    public void setAudioIn(TargetDataLine audioIn) {
        this.audioIn = audioIn;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public InetAddress getServerIp() {
        return serverIp;
    }

    public void setServerIp(InetAddress serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
