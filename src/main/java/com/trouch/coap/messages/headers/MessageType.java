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

package com.trouch.coap.messages.headers;

import java.util.ArrayList;
import java.util.List;

public class MessageType {

    private static List<MessageType> types = new ArrayList<MessageType>();

    public final static MessageType CONFIRMABLE = new MessageType(0, "CON");
    public final static MessageType NON_CONFIRMABLE = new MessageType(1, "NON");
    public final static MessageType ACKNOWLEDGMENT = new MessageType(2, "ACK");
    public final static MessageType RESET = new MessageType(3, "RST");

    private int type;
    private String description;

    private MessageType(int type, String description) {
        this.type = type;
        this.description = description;
        types.add(this);
    }

    public int toInt() {
        return type;
    }

    @Override
    public String toString() {
        return description;
    }

    public static MessageType fromInt(int type) {
        for (MessageType coapType : types) {
            if (coapType.toInt() == type) {
                return coapType;
            }
        }
        return null;
    }

}