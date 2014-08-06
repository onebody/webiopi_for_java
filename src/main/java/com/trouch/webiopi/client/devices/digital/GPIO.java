/* Copyright 2013 Eric Ptak - trouch.com
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

package com.trouch.webiopi.client.devices.digital;

import com.trouch.webiopi.client.PiClient;
import com.trouch.webiopi.client.devices.Device;

public class GPIO extends Device {

    public final static String OUT = "out";
    public final static String IN = "in";
    public final static String PWM = "pwm";
    public final static String ADC_DAC = "float";
    public final static String DAC_W = "volt";

    public GPIO(PiClient client, String deviceName) {
        super(client, deviceName, null);
    }

    public String getFunction(int channel) {
        return this.sendRequest("GET", "/" + channel + "/function");
    }

    public String setFunction(int channel, String function) {
        return this.sendRequest("POST", "/" + channel + "/function/" + function);
    }

    public String callMacro(String macro, String args) {
        this.path = "/macros";
        String res = this.sendRequest("POST", "/" + macro + "/" + args);
        this.path = "/GPIO";
        return res;
    }


    public String enablePWM(int channel) {
        return this.sendRequest("POST", "/" + channel + "/pwm/enable");
    }

    public String disablePWM(int channel) {
        return this.sendRequest("POST", "/" + channel + "/pwm/disable");
    }

    public String pulse(int channel) {
        return this.sendRequest("POST", "/" + channel + "/pulse");
    }

    public String pulseRatio(int channel, String ratio) {
        return this.sendRequest("POST", "/" + channel + "/pulseRatio/" + ratio);
    }

    public String pulseAngle(int channel, String angle) {
        return this.sendRequest("POST", "/" + channel + "/pulseAngle/" + angle);
    }

    public boolean digitalRead(int channel) {
        String res = this.sendRequest("GET", "/" + channel + "/value");
        if ("1".equals(res)) {
            return true;
        }
        return false;
    }

    public boolean digitalWrite(int channel, boolean value) {
        String res = this.sendRequest("POST", "/" + channel + "/value/" + (value ? "1" : "0"));
        if ("1".equals(res)) {
            return true;
        }
        return false;
    }

}
