
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

public class MessageCode {

    private static List<MessageCode> codes = new ArrayList<MessageCode>();

    public final static MessageCode NONE = new MessageCode(0, "NONE");

    // request code
    public final static MessageCode GET = new MessageCode(1, "GET");
    public final static MessageCode POST = new MessageCode(2, "POST");
    public final static MessageCode PUT = new MessageCode(3, "PUT");
    public final static MessageCode DELETE = new MessageCode(4, "DELETE");

    // response code 2.XX
    public final static MessageCode OK = new MessageCode(64, "2.00 OK");
    public final static MessageCode CREATED = new MessageCode(65, "2.01 Created");
    public final static MessageCode DELETED = new MessageCode(66, "2.02 Deleted");
    public final static MessageCode VALID = new MessageCode(67, "2.03 Valid");
    public final static MessageCode CHANGED = new MessageCode(68, "2.04 Changed");
    public final static MessageCode CONTENT = new MessageCode(69, "2.05 Content");

    // response code 4.XX
    public final static MessageCode BAD_REQUEST = new MessageCode(128, "4.00 Bad Request");
    public final static MessageCode UNAUTHORIZED = new MessageCode(129, "4.01 Unauthorized");
    public final static MessageCode BAD_OPTION = new MessageCode(130, "4.02 Bad Option");
    public final static MessageCode FORBIDDEN = new MessageCode(131, "4.03 Forbidden");
    public final static MessageCode NOT_FOUND = new MessageCode(132, "4.04 Not Found");
    public final static MessageCode METHOD_NOT_ALLOWED = new MessageCode(133, "4.05 Method Not Allowed");
    public final static MessageCode NOT_ACCEPTABLE = new MessageCode(134, "4.06 Not Acceptable");
    public final static MessageCode PRECONDITION_FAILED = new MessageCode(140, "4.12 Precondition Failed");
    public final static MessageCode ENTITY_TOO_LARGE = new MessageCode(141, "4.13 Request Entity Too Large");
    public final static MessageCode UNSUPPORTED_FORMAT = new MessageCode(143, "4.15 Unsupported Content-Format");

    // response code 5.XX
    public final static MessageCode INTERNAL_ERROR = new MessageCode(160, "5.00 Internal Server Error");
    public final static MessageCode NOT_IMPLEMENTED = new MessageCode(161, "5.01 Not Implemented");
    public final static MessageCode BAD_GATEWAY = new MessageCode(162, "5.02 Bad Gateway");
    public final static MessageCode SERVICE_UNAVAILABLE = new MessageCode(163, "5.03 Service Unavailable");
    public final static MessageCode GATEWAY_TIMEOUT = new MessageCode(164, "5.04 Gateway Timeout");
    public final static MessageCode PROXYING_NOT_SUPPORTED = new MessageCode(165, "5.05 Proxying Not Supported");

    private int code;
    private String description;

    private MessageCode(int code, String description) {
        this.code = code;
        this.description = description;
        codes.add(this);
    }

    public int getInt() {
        return code;
    }

    @Override
    public String toString() {
        return description;
    }

    public static MessageCode fromInt(int code) {
        for (MessageCode coapCode : codes) {
            if (coapCode.getInt() == code) {
                return coapCode;
            }
        }
        return null;
    }

}