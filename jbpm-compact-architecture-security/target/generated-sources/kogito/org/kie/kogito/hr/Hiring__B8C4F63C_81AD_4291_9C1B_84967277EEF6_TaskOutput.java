/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.kie.kogito.hr;

import java.util.HashMap;
import java.util.Map;
import org.kie.kogito.MapOutput;
import org.kie.kogito.UserTask;
import org.kie.kogito.UserTaskParam.ParamType;
import org.kie.kogito.UserTaskParam;

@UserTask(taskName = "HRInterview", processName = "hiring")
public class Hiring__B8C4F63C_81AD_4291_9C1B_84967277EEF6_TaskOutput implements MapOutput {

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("offer", this.offer);
        params.put("approve", this.approve);
        return params;
    }

    public static Hiring__B8C4F63C_81AD_4291_9C1B_84967277EEF6_TaskOutput fromMap(Map<String, Object> params) {
        org.kie.kogito.hr.Hiring__B8C4F63C_81AD_4291_9C1B_84967277EEF6_TaskOutput result = new Hiring__B8C4F63C_81AD_4291_9C1B_84967277EEF6_TaskOutput();
        result.offer = (org.kie.kogito.hr.Offer) params.get("offer");
        result.approve = (java.lang.Boolean) params.get("approve");
        return result;
    }

    @UserTaskParam(value = ParamType.OUTPUT)
    private org.kie.kogito.hr.Offer offer;

    public org.kie.kogito.hr.Offer getOffer() {
        return offer;
    }

    public void setOffer(org.kie.kogito.hr.Offer offer) {
        this.offer = offer;
    }

    @UserTaskParam(value = ParamType.OUTPUT)
    private java.lang.Boolean approve;

    public java.lang.Boolean getApprove() {
        return approve;
    }

    public void setApprove(java.lang.Boolean approve) {
        this.approve = approve;
    }
}
//Task output for user task 'HR Interview' in process 'hiring'
