package com.dydl.socketlib.model;



import com.dydl.socketlib.callback.OkCallBack;

import java.io.Serializable;
import java.net.Socket;

public class OkStrBean implements Serializable {
	private Socket socket;
	private String value;
	private OkCallBack listener;

	public Socket getSocket() {
		return socket;
	}

	public OkStrBean setSocket(Socket socket) {
		this.socket = socket;
		return this;
	}

	public String getValue() {
		return value;
	}

	public OkStrBean setValue(String value) {
		this.value = value;
		return this;
	}

	public OkCallBack getListener() {
		return listener;
	}

	public OkStrBean setListener(OkCallBack listener) {
		this.listener = listener;
		return this;
	}
}
