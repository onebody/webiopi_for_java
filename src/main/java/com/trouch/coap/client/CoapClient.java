/* Copyright 2012-2013 Eric Ptak - trouch.com
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
*/
package com.trouch.coap.client;

import com.trouch.coap.messages.CoapRequest;
import com.trouch.coap.messages.CoapResponse;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class CoapClient {

    private DatagramSocket socket;

    public CoapClient() {
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(1000);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CoapResponse sendRequest(CoapRequest request) throws IOException {
        byte requestData[] = request.getBytes();
        InetAddress addr = InetAddress.getByName(request.getHost());

        DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, addr, request.getPort());

        ByteBuffer buff = ByteBuffer.allocate(1500);
        DatagramPacket responsePacket = new DatagramPacket(buff.array(), buff.capacity());

        int sent = 0;
        while (sent < 4) {
            try {
                socket.send(requestPacket);
                socket.receive(responsePacket);
                CoapResponse response = CoapResponse.parsePacket(responsePacket);
                return response;
            } catch (SocketTimeoutException e) {
                sent++;
            }
        }
        return null;
    }
}