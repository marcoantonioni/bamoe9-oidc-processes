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
package org.kie.kogito.app;

import java.io.IOException;
import org.infinispan.protostream.MessageMarshaller;

public class CandidateDataMessageMarshaller implements MessageMarshaller<org.kie.kogito.hr.CandidateData> {

    public java.lang.Class<org.kie.kogito.hr.CandidateData> getJavaClass() {
        return org.kie.kogito.hr.CandidateData.class;
    }

    public String getTypeName() {
        return "org.kie.kogito.app.CandidateData";
    }

    public org.kie.kogito.hr.CandidateData readFrom(ProtoStreamReader reader) throws IOException {
        org.kie.kogito.hr.CandidateData value = new org.kie.kogito.hr.CandidateData();
        value.setEmail(reader.readString("email"));
        value.setExperience(reader.readInt("experience"));
        value.setLastName(reader.readString("lastName"));
        value.setName(reader.readString("name"));
        value.setSkills(reader.readCollection("skills", new java.util.ArrayList(), String.class));
        return value;
    }

    public void writeTo(ProtoStreamWriter writer, org.kie.kogito.hr.CandidateData t) throws IOException {
        writer.writeString("email", t.getEmail());
        writer.writeInt("experience", t.getExperience());
        writer.writeString("lastName", t.getLastName());
        writer.writeString("name", t.getName());
        writer.writeCollection("skills", t.getSkills(), String.class);
    }
}
