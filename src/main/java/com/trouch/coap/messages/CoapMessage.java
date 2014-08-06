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
import com.trouch.coap.messages.headers.Option;
import com.trouch.coap.messages.headers.OptionName;
import com.trouch.coap.messages.headers.OptionName.DataType;
import com.trouch.coap.messages.options.ContentFormat;

import java.net.DatagramPacket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CoapMessage {

    public final static int DEFAULT_PORT = 5683;

    private int version;
    private MessageType type;
    private MessageCode code;
    private byte[] token;
    private int id;
    private List<Option> options;
    private String host;
    private int port;
    private String path;
    private String payload;
    private ContentFormat content_format;

    protected CoapMessage() {
        version = 1;
        type = MessageType.CONFIRMABLE;
        code = MessageCode.NONE;
        token = null;
        id = 0;
        host = null;
        port = -1;
        path = "";
        payload = null;
        options = new ArrayList<Option>();
    }

    protected CoapMessage(MessageType type, MessageCode code, String uri) throws URISyntaxException {
        URI _uri = new URI(uri);

        if (!_uri.getScheme().equals("coap")) {
            throw new URISyntaxException(uri, "URI scheme not supported");
        }

        this.version = 1;
        this.type = type;
        this.code = code;
        this.token = null;
        this.id = 0;
        this.host = _uri.getHost();
        this.port = _uri.getPort() > 0 ? _uri.getPort() : DEFAULT_PORT;
        this.path = _uri.getPath();
        this.payload = null;
        this.options = new ArrayList<Option>();
    }

    protected CoapMessage(DatagramPacket packet) {
        this.options = new ArrayList<Option>();
        this.path = "";

        byte bytes[] = packet.getData();
        int packetLength = packet.getLength();

        this.version = (bytes[0] & 0xC0) >> 6;
        this.type = MessageType.fromInt((bytes[0] & 0x30) >> 4);
        int token_length = bytes[0] & 0x0F;
        int index = 4;
        this.token = new byte[token_length];
        System.arraycopy(bytes, index, this.token, 0, token_length);
        index += token_length;

        this.code = MessageCode.fromInt(bytes[1] & 0xFF);
        this.id = ((bytes[2] & 0xFF) << 8) | bytes[3] & 0xFF;

        int number = 0;
        int delta = 0;
        int length = 0;

        while (index < packetLength && (bytes[index] & 0xFF) != 0xFF) {

            delta = (bytes[index] & 0xF0) >> 4;
            length = bytes[index] & 0x0F;
            int offset = 1;
            // delta extended with 1 byte
            if (delta == 13) {
                delta += bytes[index + offset];
                offset += 1;
            }
            // delta extended with 2 bytes
            else if (delta == 14) {
                delta += 255 + ((bytes[index + offset] << 8) | bytes[index + offset + 1]);
                offset += 2;
            }

            // length extended with 1 byte
            if (length == 13) {
                length += bytes[index + offset];
                offset += 1;
            }

            // length extended with 2 bytes
            else if (length == 14) {
                length += 255 + ((bytes[index + offset] << 8) | bytes[index + offset + 1]);
                offset += 2;
            }

            number += delta;
            OptionName optionName = OptionName.fromInt(number);
            byte valueBytes[] = new byte[length];
            System.arraycopy(bytes, index + offset, valueBytes, 0, length);

            Object value = null;

            // opaque option value
            if (optionName.getType() == DataType.OPAQUE) {
                value = ByteBuffer.wrap(valueBytes);
            }
            // integer option value
            else if (optionName.getType() == DataType.INTEGER) {
                int tmp = 0;
                for (byte b : valueBytes) {
                    tmp <<= 8;
                    tmp |= b;
                }
                value = new Integer(tmp);
            }
            // string option value
            else if (optionName.getType() == DataType.STRING) {
                value = new String(valueBytes);
            }
            this.options.add(new Option(optionName, value));
            index += offset + length;
        }

        index += 1; // skip 0xFF / end-of-options

        byte payloadBytes[] = new byte[packetLength - index];
        System.arraycopy(bytes, index, payloadBytes, 0, packetLength - index);
        this.payload = new String(payloadBytes);

        for (Option option : this.options) {
            if (option.getName().equals(OptionName.URI_PATH)) {
                this.path += "/" + option.getValue();
            } else if (option.getName().equals(OptionName.CONTENT_FORMAT)) {
                this.content_format = ContentFormat.fromInt((Integer) option.getValue());
            }
        }

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    protected MessageType getType() {
        return type;
    }

    protected void setType(MessageType type) {
        this.type = type;
    }

    protected MessageCode getCode() {
        return code;
    }

    protected void setCode(MessageCode code) {
        this.code = code;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ContentFormat getContentFormat() {
        return content_format;
    }

    public void setContentFormat(ContentFormat content_format) {
        this.content_format = content_format;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    private static int getOptionHeaderValue(int value) {
        if (value > 268)
            return 14;

        if (value > 12)
            return 13;

        return value;
    }

    private static byte[] getOptionHeaderExtension(int value) {
        byte buff[];
        int v = getOptionHeaderValue(value);

        if (v == 14) {
            value -= 269;
            buff = new byte[2];
            buff[0] = (byte) ((value & 0xFF00) >> 8);
            buff[1] = (byte) (value & 0x00FF);
        } else if (v == 13) {
            value -= 13;
            buff = new byte[1];
            buff[0] = (byte) value;
        } else buff = new byte[0];
        return buff;
    }

    private static int appendOption(ByteBuffer buffer, int lastnumber, OptionName option, byte[] data) {
        int delta = option.getInt() - lastnumber;
        int length = data.length;

        int d = getOptionHeaderValue(delta);
        int l = getOptionHeaderValue(length);

        byte b, ext[];

        b = (byte) ((d << 4) & 0xF0);
        b |= (byte) (l & 0x0F);
        buffer.put(b);

        ext = getOptionHeaderExtension(delta);
        for (byte bb : ext) {
            buffer.put(bb);
        }

        ext = getOptionHeaderExtension(length);
        for (byte bb : ext) {
            buffer.put(bb);
        }

        for (int i = 0; i < data.length; i++) {
            buffer.put(data[i]);
        }

        return option.getInt();
    }

    public byte[] getBytes() {
        ByteBuffer buff = ByteBuffer.allocate(1500);
        int tokenLen = token != null ? Math.min(token.length, 8) : 0;

        byte b = 0;
        b |= (byte) ((version & 0x03) << 6);
        b |= (byte) ((type.toInt() & 0x03) << 4);
        b |= (byte) tokenLen;
        buff.put(b);

        buff.put((byte) code.getInt());
        buff.put((byte) ((id & 0xFF00) >> 8));
        buff.put((byte) (id & 0x00FF));

        for (int i = 0; i < tokenLen; i++) {
            buff.put(token[i]);
        }

        int lastnumber = 0;

        if ((path != null) && (path.length() > 0)) {
            String subpath[] = path.split("/");
            for (String s : subpath) {
                if (s.length() > 0) {
                    lastnumber = appendOption(buff, lastnumber, OptionName.URI_PATH, s.getBytes());
                }
            }
        }

        buff.put((byte) 0xFF);

        byte data[];
        if ((payload != null) && (payload.length() > 0)) {
            data = payload.getBytes();
            for (byte d : data) {
                buff.put(d);
            }
        }

        byte result[] = new byte[buff.position()];
        System.arraycopy(buff.array(), 0, result, 0, result.length);

        return result;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("Version: ").append(version).append("\n");
        buff.append("Type: ").append(type).append("\n");
        buff.append("Code: ").append(code).append("\n");
        buff.append("Token: ");
        if (token != null) buff.append(new String(token));
        buff.append("\n");
        buff.append("Id: ").append(String.format("0x%04X", id)).append("\n");
        buff.append("Uri-Path: ").append(path).append("\n");
        buff.append("Content-Format: ").append(content_format).append("\n");
        buff.append("Payload: ").append(payload).append("\n");
        return buff.toString();
    }

    public static void printHexDump(byte[] bytes) {
        int i = 0;
        for (byte bb : bytes) {
            int b = bb & 0xFF;
            System.out.println(String.format("%03d: 0x%02X - %03d - %c", i, b, b, (char) b));
            i++;
        }
    }


}