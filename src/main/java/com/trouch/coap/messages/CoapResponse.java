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

package com.trouch.coap.messages;

import com.trouch.coap.messages.headers.MessageCode;

import java.net.DatagramPacket;

public class CoapResponse extends CoapMessage {

    // response code 2.XX
    public final static MessageCode OK                      = MessageCode.OK;
    public final static MessageCode CREATED = MessageCode.CREATED;
    public final static MessageCode DELETED = MessageCode.DELETED;
    public final static MessageCode VALID           = MessageCode.VALID;
    public final static MessageCode CHANGED = MessageCode.CHANGED;
    public final static MessageCode CONTENT = MessageCode.CONTENT;

    // response code 4.XX
    public final static MessageCode BAD_REQUEST                     = MessageCode.BAD_REQUEST;
    public final static MessageCode UNAUTHORIZED                    = MessageCode.UNAUTHORIZED;
    public final static MessageCode BAD_OPTION                              = MessageCode.BAD_OPTION;
    public final static MessageCode FORBIDDEN                               = MessageCode.FORBIDDEN;
    public final static MessageCode NOT_FOUND                               = MessageCode.NOT_FOUND;
    public final static MessageCode METHOD_NOT_ALLOWED              = MessageCode.METHOD_NOT_ALLOWED;
    public final static MessageCode NOT_ACCEPTABLE                  = MessageCode.NOT_ACCEPTABLE;
    public final static MessageCode PRECONDITION_FAILED     = MessageCode.PRECONDITION_FAILED;
    public final static MessageCode ENTITY_TOO_LARGE                = MessageCode.ENTITY_TOO_LARGE;
    public final static MessageCode UNSUPPORTED_FORMAT              = MessageCode.UNSUPPORTED_FORMAT;

    // response code 5.XX
    public final static MessageCode INTERNAL_ERROR                  = MessageCode.INTERNAL_ERROR;
    public final static MessageCode NOT_IMPLEMENTED         = MessageCode.NOT_IMPLEMENTED;
    public final static MessageCode BAD_GATEWAY                     = MessageCode.BAD_GATEWAY;
    public final static MessageCode SERVICE_UNAVAILABLE     = MessageCode.SERVICE_UNAVAILABLE;
    public final static MessageCode GATEWAY_TIMEOUT         = MessageCode.GATEWAY_TIMEOUT;
    public final static MessageCode PROXYING_NOT_SUPPORTED  = MessageCode.PROXYING_NOT_SUPPORTED;

    private CoapResponse(DatagramPacket packet) {
        super(packet);
    }


    public static CoapResponse parsePacket(DatagramPacket packet) {
        CoapResponse response = new CoapResponse(packet);
        return response;
    }

    public MessageCode getStatusCode() {
        return getCode();
    }

    public void setStatusCode(MessageCode code) {
        setCode(code);
    }

}