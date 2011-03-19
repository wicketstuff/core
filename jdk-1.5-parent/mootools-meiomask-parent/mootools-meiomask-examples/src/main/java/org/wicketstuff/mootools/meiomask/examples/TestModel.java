/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wicketstuff.mootools.meiomask.examples;

import java.io.Serializable;

/**
 *
 * @author inaiat
 */
public class TestModel implements Serializable {
    
    private String fixed;
    private String fixedPhone;
    private Long fixedPhoneUs;
    private String fixedCpf;

    /**
     * @return the fixed
     */
    public String getFixed() {
        return fixed;
    }

    /**
     * @param fixed the fixed to set
     */
    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    /**
     * @return the fixedPhone
     */
    public String getFixedPhone() {
        return fixedPhone;
    }

    /**
     * @param fixedPhone the fixedPhone to set
     */
    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    /**
     * @return the fixedCpf
     */
    public String getFixedCpf() {
        return fixedCpf;
    }

    /**
     * @param fixedCpf the fixedCpf to set
     */
    public void setFixedCpf(String fixedCpf) {
        this.fixedCpf = fixedCpf;
    }

    /**
     * @return the fixedPhoneUs
     */
    public Long getFixedPhoneUs() {
        return fixedPhoneUs;
    }

    /**
     * @param fixedPhoneUs the fixedPhoneUs to set
     */
    public void setFixedPhoneUs(Long fixedPhoneUs) {
        this.fixedPhoneUs = fixedPhoneUs;
    }

}
