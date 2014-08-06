
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

package com.trouch.coap.messages.options;

import com.trouch.coap.messages.headers.Option;
import com.trouch.coap.messages.headers.OptionName;

import java.util.ArrayList;
import java.util.List;

public class ContentFormat extends Option {

    private static List<ContentFormat> formats = new ArrayList<ContentFormat>();

    public final static ContentFormat PLAIN                 = new ContentFormat(00, "text/plain");
    public final static ContentFormat LINK_FORMAT   = new ContentFormat(40, "application/link-format");
    public final static ContentFormat XML                   = new ContentFormat(41, "application/xml");
    public final static ContentFormat OCTET_STREAM  = new ContentFormat(42, "application/octet-stream");
    public final static ContentFormat EXI                   = new ContentFormat(47, "application/exi");
    public final static ContentFormat JSON                  = new ContentFormat(50, "application/json");

    private String mime;

    private ContentFormat(int value, String mime) {
        super(OptionName.CONTENT_FORMAT, value);
        this.mime = mime;
        formats.add(this);
    }

    @Override
    public String toString() {
        return mime;
    }

    public static ContentFormat fromInt(int format) {
        for (ContentFormat fmt : formats) {
            if ((Integer)fmt.getValue() == format) {
                return fmt;
            }
        }
        return null;
    }

}