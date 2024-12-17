package org.kie.kogito.hr;

import org.kie.kogito.hr.HiringModel;

public class HiringProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<HiringModel> {

    public HiringProcessInstance(org.kie.kogito.hr.HiringProcess process, HiringModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public HiringProcessInstance(org.kie.kogito.hr.HiringProcess process, HiringModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    public HiringProcessInstance(org.kie.kogito.hr.HiringProcess process, HiringModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, processRuntime, wpi);
    }

    public HiringProcessInstance(org.kie.kogito.hr.HiringProcess process, HiringModel value, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, wpi);
    }

    public HiringProcessInstance(org.kie.kogito.hr.HiringProcess process, HiringModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.kogito.correlation.CompositeCorrelation correlation) {
        super(process, value, businessKey, processRuntime, correlation);
    }

    protected java.util.Map<String, Object> bind(HiringModel variables) {
        if (null != variables)
            return variables.toMap();
        else
            return new java.util.HashMap();
    }

    protected void unbind(HiringModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
