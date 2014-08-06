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
import com.trouch.coap.messages.headers.MessageType;

import java.net.URISyntaxException;
import java.util.Random;

public class CoapRequest extends CoapMessage {

    public final static MessageCode GET  = MessageCode.GET;
    public final static MessageCode POST = MessageCode.POST;
    public final static MessageCode PUT  = MessageCode.PUT;
    public final static MessageCode DELETE = MessageCode.DELETE;

    private final static Random random = new Random(System.currentTimeMillis());
    private final static int randomMax = (int) Math.pow(2, 16);

    public CoapRequest(MessageType type, MessageCode code, String uri) throws URISyntaxException {
        super(type, code, uri);
        setId(random.nextInt(randomMax));
    }

    public MessageCode getMethodCode() {
        return getCode();
    }

    public void setMethodCode(MessageCode code) {
        setCode(code);
    }

}