package com.springboot.service.send.tcpClient;

import java.io.IOException;


public interface TCPClientService {

  String transportOut(String msg) throws IOException;
}

