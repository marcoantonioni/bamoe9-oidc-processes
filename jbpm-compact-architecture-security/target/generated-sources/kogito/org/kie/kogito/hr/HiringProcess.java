package org.kie.kogito.hr;

import org.kie.kogito.hr.HiringModel;
import org.kie.api.definition.process.Process;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.jbpm.process.core.datatype.impl.type.ObjectDataType;
import org.drools.core.util.KieFunctions;
import org.jbpm.process.core.datatype.impl.type.BooleanDataType;

@jakarta.enterprise.context.ApplicationScoped()
@jakarta.inject.Named("hiring")
@io.quarkus.runtime.Startup()
public class HiringProcess extends org.kie.kogito.process.impl.AbstractProcess<org.kie.kogito.hr.HiringModel> {

    @jakarta.inject.Inject()
    public HiringProcess(org.kie.kogito.app.Application app, org.kie.kogito.correlation.CorrelationService correlations, org.kie.kogito.process.ProcessInstancesFactory factory) {
        super(app, java.util.Arrays.asList(), correlations, factory);
        activate();
    }

    public HiringProcess() {
    }

    @Override()
    public org.kie.kogito.hr.HiringProcessInstance createInstance(org.kie.kogito.hr.HiringModel value) {
        return new org.kie.kogito.hr.HiringProcessInstance(this, value, this.createProcessRuntime());
    }

    public org.kie.kogito.hr.HiringProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.hr.HiringModel value) {
        return new org.kie.kogito.hr.HiringProcessInstance(this, value, businessKey, this.createProcessRuntime());
    }

    public org.kie.kogito.hr.HiringProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.correlation.CompositeCorrelation correlation, org.kie.kogito.hr.HiringModel value) {
        return new org.kie.kogito.hr.HiringProcessInstance(this, value, businessKey, this.createProcessRuntime(), correlation);
    }

    @Override()
    public org.kie.kogito.hr.HiringModel createModel() {
        return new org.kie.kogito.hr.HiringModel();
    }

    public org.kie.kogito.hr.HiringProcessInstance createInstance(org.kie.kogito.Model value) {
        return this.createInstance((org.kie.kogito.hr.HiringModel) value);
    }

    public org.kie.kogito.hr.HiringProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.Model value) {
        return this.createInstance(businessKey, (org.kie.kogito.hr.HiringModel) value);
    }

    public org.kie.kogito.hr.HiringProcessInstance createInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.kie.kogito.hr.HiringProcessInstance(this, this.createModel(), this.createProcessRuntime(), wpi);
    }

    public org.kie.kogito.hr.HiringProcessInstance createReadOnlyInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.kie.kogito.hr.HiringProcessInstance(this, this.createModel(), wpi);
    }

    protected org.kie.api.definition.process.Process process() {
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("hiring", true);
        factory.variable("hr_approval", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), java.util.Map.of("customTags", "internal", "ItemSubjectRef", "_hr_approvalItem", "hr_approval", "hr_approval"));
        factory.variable("it_approval", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), java.util.Map.of("customTags", "internal", "it_approval", "it_approval", "ItemSubjectRef", "_it_approvalItem"));
        factory.variable("candidateData", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(org.kie.kogito.hr.CandidateData.class), java.util.Map.of("customTags", "input", "candidateData", "candidateData", "ItemSubjectRef", "_candidateDataItem"));
        factory.variable("offer", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(org.kie.kogito.hr.Offer.class), java.util.Map.of("offer", "offer", "customTags", "output", "ItemSubjectRef", "_offerItem"));
        factory.name("hiring");
        factory.packageName("org.kie.kogito.hr");
        factory.dynamic(false);
        factory.version("1.0");
        factory.type("BPMN");
        factory.visibility("Public");
        factory.metaData("jbpm.enable.multi.con", null);
        factory.metaData("elementname", "Realm Role: HR\n(see Groups value)");
        factory.metaData("TargetNamespace", "http://www.omg.org/bpmn20");
        org.jbpm.ruleflow.core.factory.ActionNodeFactory<?> actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67 = factory.actionNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_940F2A0C-8CC6-4ECA-B504-49363AF68B67"));
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.name("Send Offer to Candidate");
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("UniqueId", "_940F2A0C-8CC6-4ECA-B504-49363AF68B67");
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("elementname", "Send Offer to Candidate");
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("NodeType", "ScriptTask");
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("x", 1294);
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("width", 154);
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("y", 403);
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.metaData("height", 102);
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.action(kcontext -> {
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            org.kie.kogito.hr.Offer offer = (org.kie.kogito.hr.Offer) kcontext.getVariable("offer");
            System.out.println("###################################");
            System.out.println("To: " + candidateData.getEmail());
            System.out.println("Subject: Congratulations you made it!");
            System.out.println("Dear " + candidateData.getFullName() + ", we are happy to tell you that you've successfully went through the hiring process. You'll find the final Offer details in attached.");
            System.out.println("Job Category: " + offer.getCategory());
            System.out.println("Base salary: " + offer.getSalary());
            System.out.println("###################################");
        });
        actionNode_940F2A0C_8CC6_4ECA_B504_49363AF68B67.done();
        org.jbpm.ruleflow.core.factory.ActionNodeFactory<?> actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C = factory.actionNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_57DBE42C-6850-4A08-AC73-2BB0FFDFCC8C"));
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.name("Log Offer");
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("UniqueId", "_57DBE42C-6850-4A08-AC73-2BB0FFDFCC8C");
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("elementname", "Log Offer");
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("NodeType", "ScriptTask");
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("x", 581);
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("width", 154);
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("y", 93);
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.metaData("height", 102);
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.action(kcontext -> {
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            org.kie.kogito.hr.Offer offer = (org.kie.kogito.hr.Offer) kcontext.getVariable("offer");
            System.out.println("###################################");
            System.out.println("Generated offer for candidate: " + candidateData.getFullName());
            System.out.println("Job Category: " + offer.getCategory());
            System.out.println("Base salary: " + offer.getSalary());
            System.out.println("###################################");
        });
        actionNode_57DBE42C_6850_4A08_AC73_2BB0FFDFCC8C.done();
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1 = factory.endNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_6CE52250-BCEE-485C-8AE4-F6CACF2EF5A1"));
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.name("End");
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.terminate(false);
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.metaData("UniqueId", "_6CE52250-BCEE-485C-8AE4-F6CACF2EF5A1");
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.metaData("x", 1498);
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.metaData("width", 56);
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.metaData("y", 426);
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.metaData("height", 56);
        endNode_6CE52250_BCEE_485C_8AE4_F6CACF2EF5A1.done();
        org.jbpm.ruleflow.core.factory.RuleSetNodeFactory<?> ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A = factory.ruleSetNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A"));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.name("Generate base offer");
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.decision("https://kiegroup.org/dmn/_0DCC505E-71B4-4C29-8848-4006623FE51A", "New Hiring Offer", null, () -> {
            return app.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://kiegroup.org/dmn/_0DCC505E-71B4-4C29-8848-4006623FE51A", "New Hiring Offer");
        });
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_fileNameInputX", "fileName", "java.lang.String", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("fcc25c8f-c057-4725-9549-6facbd540027", "EXPRESSION (NewHiringOffer.dmn)", "java.lang.Object", "NewHiringOffer.dmn"), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_fileNameInputX", "fileName", "java.lang.String", null))), null));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_namespaceInputX", "namespace", "java.lang.String", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("ff28f218-ce97-4d55-a77e-fbc575d5cfdf", "EXPRESSION (https://kiegroup.org/dmn/_0DCC505E-71B4-4C29-8848-4006623FE51A)", "java.lang.Object", "https://kiegroup.org/dmn/_0DCC505E-71B4-4C29-8848-4006623FE51A"), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_namespaceInputX", "namespace", "java.lang.String", null))), null));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_modelInputX", "model", "java.lang.String", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("4971d420-5113-4a95-b9d8-5b221be49b5a", "EXPRESSION (New Hiring Offer)", "java.lang.Object", "New Hiring Offer"), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_modelInputX", "model", "java.lang.String", null))), null));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("candidateData", "candidateData", "org.kie.kogito.hr.CandidateData", null)), new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_CandidateDataInputX", "CandidateData", "org.kie.kogito.hr.CandidateData", null), null, null));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A_OfferOutputX", "Offer", "org.kie.kogito.hr.Offer", null)), new org.jbpm.workflow.core.impl.DataDefinition("offer", "offer", "org.kie.kogito.hr.Offer", null), null, null));
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("UniqueId", "_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A");
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("elementname", "Generate base offer");
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("x", 384);
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("width", 154);
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("y", 93);
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.metaData("height", 102);
        ruleSetNode_F4D56F6C_4CFE_4D5C_BF5E_67261F68EF1A.done();
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366 = factory.endNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_457DC13A-0AEC-4390-A860-5CCFFB67B366"));
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.name("End");
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.terminate(false);
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.metaData("UniqueId", "_457DC13A-0AEC-4390-A860-5CCFFB67B366");
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.metaData("x", 1498);
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.metaData("width", 56);
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.metaData("y", 270);
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.metaData("height", 56);
        endNode_457DC13A_0AEC_4390_A860_5CCFFB67B366.done();
        org.jbpm.ruleflow.core.factory.ActionNodeFactory<?> actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0 = factory.actionNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_4B7FF147-F096-435B-8CAF-48318EF786B0"));
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.name("Application denied");
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("UniqueId", "_4B7FF147-F096-435B-8CAF-48318EF786B0");
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("elementname", "Application denied");
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("NodeType", "ScriptTask");
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("x", 1294);
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("width", 154);
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("y", 247);
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.metaData("height", 102);
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.action(kcontext -> {
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            System.out.println("###################################");
            System.out.println("Candidate " + candidateData.getFullName() + " don't meet the requirements for the position but we'll keep it on records for the future!");
            System.out.println("###################################");
        });
        actionNode_4B7FF147_F096_435B_8CAF_48318EF786B0.done();
        org.jbpm.ruleflow.core.factory.JoinFactory<?> joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7 = factory.joinNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"));
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.name("Join");
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.type(2);
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.metaData("UniqueId", "_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7");
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.metaData("x", 1173);
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.metaData("width", 56);
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.metaData("y", 270);
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.metaData("height", 56);
        joinNode_30C8C01B_4149_4AE5_8D86_D27D57BCA8E7.done();
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04 = factory.splitNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_A51A809E-03C0-4EA7-91F1-7B3C5E29AD04"));
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.name("Split");
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.type(2);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("UniqueId", "_A51A809E-03C0-4EA7-91F1-7B3C5E29AD04");
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("x", 431);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("width", 56);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("y", 270);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("Default", "_28B49D89-0D19-4B62-9BF8-EDCFA0942335");
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.metaData("height", 56);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.constraint(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), "_C6E61C53-FD35-4347-B69E-30AA93AE4404", "DROOLS_DEFAULT", "java", kcontext -> {
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            return candidateData.getExperience() < 1 || candidateData.getSkills().size() == 0;
        }, 0, false);
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.done();
        splitNode_A51A809E_03C0_4EA7_91F1_7B3C5E29AD04.constraint(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), "_C6E61C53-FD35-4347-B69E-30AA93AE4404", "DROOLS_DEFAULT", "java", kcontext -> {
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            return candidateData.getExperience() < 1 || candidateData.getSkills().size() == 0;
        }, 0, false);
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D = factory.splitNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B9662806-84F3-4444-AD62-4790CDA7C85D"));
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.name("Split");
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.type(2);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("UniqueId", "_B9662806-84F3-4444-AD62-4790CDA7C85D");
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("x", 1172);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("width", 56);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("y", 426);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("Default", "_B7FC63DD-C08F-4CB3-A51A-79C1B8B18E6E");
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.metaData("height", 56);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.constraint(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_940F2A0C-8CC6-4ECA-B504-49363AF68B67"), "_B11455DE-F77A-4251-A85B-4C66636E3CD9", "DROOLS_DEFAULT", "java", kcontext -> {
            java.lang.Boolean it_approval = (java.lang.Boolean) kcontext.getVariable("it_approval");
            java.lang.Boolean hr_approval = (java.lang.Boolean) kcontext.getVariable("hr_approval");
            return hr_approval && it_approval;
        }, 0, false);
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.done();
        splitNode_B9662806_84F3_4444_AD62_4790CDA7C85D.constraint(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_940F2A0C-8CC6-4ECA-B504-49363AF68B67"), "_B11455DE-F77A-4251-A85B-4C66636E3CD9", "DROOLS_DEFAULT", "java", kcontext -> {
            java.lang.Boolean it_approval = (java.lang.Boolean) kcontext.getVariable("it_approval");
            java.lang.Boolean hr_approval = (java.lang.Boolean) kcontext.getVariable("hr_approval");
            return hr_approval && it_approval;
        }, 0, false);
        org.jbpm.ruleflow.core.factory.ActionNodeFactory<?> actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347 = factory.actionNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_834B21EF-9229-44F8-A5DB-D96EBB39A347"));
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.name("Send notification HR Interview avoided");
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("UniqueId", "_834B21EF-9229-44F8-A5DB-D96EBB39A347");
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("elementname", "Send notification HR Interview avoided");
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("NodeType", "ScriptTask");
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("x", 1124);
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("width", 154);
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("y", 93);
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.metaData("height", 102);
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.action(kcontext -> {
            System.out.println("###################################");
            System.out.println("HR Interview have been avoided after reasonable time");
            System.out.println("###################################");
        });
        actionNode_834B21EF_9229_44F8_A5DB_D96EBB39A347.done();
        org.jbpm.ruleflow.core.factory.ActionNodeFactory<?> actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045 = factory.actionNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_5BDBE48C-CC83-46A9-9D56-F846F8FC1045"));
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.name("New Hiring");
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("UniqueId", "_5BDBE48C-CC83-46A9-9D56-F846F8FC1045");
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("elementname", "New Hiring");
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("NodeType", "ScriptTask");
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("x", 219);
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("width", 154);
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("y", 247);
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.metaData("height", 102);
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.action(kcontext -> {
            java.lang.Boolean hr_approval = (java.lang.Boolean) kcontext.getVariable("hr_approval");
            java.lang.Boolean it_approval = (java.lang.Boolean) kcontext.getVariable("it_approval");
            org.kie.kogito.hr.CandidateData candidateData = (org.kie.kogito.hr.CandidateData) kcontext.getVariable("candidateData");
            System.out.println("New Hiring has been created for candidate: " + candidateData.getFullName());
            kcontext.setVariable("hr_approval", false);
            kcontext.setVariable("it_approval", false);
        });
        actionNode_5BDBE48C_CC83_46A9_9D56_F846F8FC1045.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E = factory.humanTaskNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E"));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.name("IT Interview");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.workParameter("GroupId", "IT");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.workParameter("NodeName", "IT Interview");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.workParameter("Skippable", "false");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.workParameter("TaskName", "ITInterview");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_TaskNameInputX", "TaskName", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("085cebd8-3a48-472c-8533-9f7968052845", "EXPRESSION (ITInterview)", "java.lang.Object", "ITInterview"), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_TaskNameInputX", "TaskName", "Object", null))), null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("candidateData", "candidateData", "org.kie.kogito.hr.CandidateData", null)), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_candidateInputX", "candidate", "org.kie.kogito.hr.CandidateData", null), null, null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("offer", "offer", "org.kie.kogito.hr.Offer", null)), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_offerInputX", "offer", "org.kie.kogito.hr.Offer", null), null, null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("it_approval", "it_approval", "java.lang.Boolean", null)), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_approveInputX", "approve", "Boolean", null), null, null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_SkippableInputX", "Skippable", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("abdd1a95-bbef-4946-86ee-8b83a2ab1607", "EXPRESSION (false)", "java.lang.Object", "false"), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_SkippableInputX", "Skippable", "Object", null))), null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_GroupIdInputX", "GroupId", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("fea5970b-cd88-4140-b5bf-e6d40f5a7bcd", "EXPRESSION (IT)", "java.lang.Object", "IT"), new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_GroupIdInputX", "GroupId", "Object", null))), null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E_approveOutputX", "approve", "Boolean", null)), new org.jbpm.workflow.core.impl.DataDefinition("it_approval", "it_approval", "java.lang.Boolean", null), null, null));
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.done();
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("UniqueId", "_8962C15F-55EC-46F7-B926-5D5A1FD8D35E");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("elementname", "IT Interview");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("Lane", "Information Technology");
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("x", 839);
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("width", 154);
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("y", 403);
        humanTaskNode_8962C15F_55EC_46F7_B926_5D5A1FD8D35E.metaData("height", 102);
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6 = factory.humanTaskNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B8C4F63C-81AD-4291-9C1B-84967277EEF6"));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.name("HR Interview");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.workParameter("GroupId", "HR");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.workParameter("NodeName", "HR Interview");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.workParameter("Skippable", "false");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.workParameter("TaskName", "HRInterview");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_TaskNameInputX", "TaskName", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("834fdf86-73af-4f4d-9dbe-1a5f40267e74", "EXPRESSION (HRInterview)", "java.lang.Object", "HRInterview"), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_TaskNameInputX", "TaskName", "Object", null))), null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("candidateData", "candidateData", "org.kie.kogito.hr.CandidateData", null)), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_candidateInputX", "candidate", "org.kie.kogito.hr.CandidateData", null), null, null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("offer", "offer", "org.kie.kogito.hr.Offer", null)), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_offerInputX", "offer", "org.kie.kogito.hr.Offer", null), null, null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("hr_approval", "hr_approval", "java.lang.Boolean", null)), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_approveInputX", "approve", "Boolean", null), null, null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_SkippableInputX", "Skippable", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("f222eb61-24b6-4701-8911-34f17b0bd255", "EXPRESSION (false)", "java.lang.Object", "false"), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_SkippableInputX", "Skippable", "Object", null))), null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_GroupIdInputX", "GroupId", "Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("6cca057f-ffde-4798-b95f-b7394ee4c0ed", "EXPRESSION (HR)", "java.lang.Object", "HR"), new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_GroupIdInputX", "GroupId", "Object", null))), null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_approveOutputX", "approve", "Boolean", null)), new org.jbpm.workflow.core.impl.DataDefinition("hr_approval", "hr_approval", "java.lang.Boolean", null), null, null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_B8C4F63C-81AD-4291-9C1B-84967277EEF6_offerOutputX", "offer", "org.kie.kogito.hr.Offer", null)), new org.jbpm.workflow.core.impl.DataDefinition("offer", "offer", "org.kie.kogito.hr.Offer", null), null, null));
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.done();
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("UniqueId", "_B8C4F63C-81AD-4291-9C1B-84967277EEF6");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("elementname", "HR Interview");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("Lane", "Human Resources");
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("x", 839);
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("width", 154);
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("y", 93);
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.metaData("height", 102);
        humanTaskNode_B8C4F63C_81AD_4291_9C1B_84967277EEF6.onActionScript("onEntry", "java", "System.out.println(\"===>> Task wait 60 mins\");", (org.kie.kogito.internal.process.runtime.KogitoProcessContext kcontext) -> {
            System.out.println("===>> Task wait 60 mins");
        });
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56 = factory.startNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_1639F738-45F3-4CD6-A80E-CCEBAA605D56"));
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.name("Start");
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.interrupting(true);
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("UniqueId", "_1639F738-45F3-4CD6-A80E-CCEBAA605D56");
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("EventType", "none");
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("x", 126);
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("width", 56);
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("y", 270);
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.metaData("height", 56);
        startNode_1639F738_45F3_4CD6_A80E_CCEBAA605D56.done();
        org.jbpm.ruleflow.core.factory.BoundaryEventNodeFactory<?> boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B = factory.boundaryEventNode(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_116F3C54-A10E-4952-9E08-1CACE74CED0B"));
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("UniqueId", "_116F3C54-A10E-4952-9E08-1CACE74CED0B");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("EventType", "timer");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("Lane", "Human Resources");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("x", 965);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("width", 56);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("y", 115);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("AttachedTo", "_B8C4F63C-81AD-4291-9C1B-84967277EEF6");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("TimeDuration", "PT3600S");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("CancelActivity", true);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.metaData("height", 56);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.name("BoundaryEvent");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.eventType("Timer-_B8C4F63C-81AD-4291-9C1B-84967277EEF6-PT3600S-_116F3C54-A10E-4952-9E08-1CACE74CED0B");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.attachedTo("_B8C4F63C-81AD-4291-9C1B-84967277EEF6");
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.scope(null);
        boundaryEventNode_116F3C54_A10E_4952_9E08_1CACE74CED0B.done();
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B9662806-84F3-4444-AD62-4790CDA7C85D"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_940F2A0C-8CC6-4ECA-B504-49363AF68B67"), "_B11455DE-F77A-4251-A85B-4C66636E3CD9");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_57DBE42C-6850-4A08-AC73-2BB0FFDFCC8C"), "_9C33F5EA-89C7-4ED1-B3C2-CF18DE439AF5");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_940F2A0C-8CC6-4ECA-B504-49363AF68B67"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_6CE52250-BCEE-485C-8AE4-F6CACF2EF5A1"), "_7DDA574A-C220-4FEF-9784-22EF8052EDEC");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_A51A809E-03C0-4EA7-91F1-7B3C5E29AD04"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_F4D56F6C-4CFE-4D5C-BF5E-67261F68EF1A"), "_28B49D89-0D19-4B62-9BF8-EDCFA0942335");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_4B7FF147-F096-435B-8CAF-48318EF786B0"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_457DC13A-0AEC-4390-A860-5CCFFB67B366"), "_527D3164-4989-4D2C-B80B-9BA9D4C8FB89");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_4B7FF147-F096-435B-8CAF-48318EF786B0"), "_94172225-E124-4F14-98DA-C3D62C11254A");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_A51A809E-03C0-4EA7-91F1-7B3C5E29AD04"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), "_C6E61C53-FD35-4347-B69E-30AA93AE4404");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B9662806-84F3-4444-AD62-4790CDA7C85D"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), "_B7FC63DD-C08F-4CB3-A51A-79C1B8B18E6E");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_834B21EF-9229-44F8-A5DB-D96EBB39A347"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_30C8C01B-4149-4AE5-8D86-D27D57BCA8E7"), "_5334FFDC-1FCB-47E6-8085-36DC9A3D17B9");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_5BDBE48C-CC83-46A9-9D56-F846F8FC1045"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_A51A809E-03C0-4EA7-91F1-7B3C5E29AD04"), "_5162ABF0-DD2E-4BDC-9A46-DDCFCB010287");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B9662806-84F3-4444-AD62-4790CDA7C85D"), "_C62F7EFB-A009-450A-81C7-57D36F0DF766");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_116F3C54-A10E-4952-9E08-1CACE74CED0B"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_834B21EF-9229-44F8-A5DB-D96EBB39A347"), "_7B41F971-C74D-4036-8A5E-EFF81C37986A");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_1639F738-45F3-4CD6-A80E-CCEBAA605D56"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_5BDBE48C-CC83-46A9-9D56-F846F8FC1045"), "_8863B46B-9B0F-40B9-AAB1-A7503CF9AA0A");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B8C4F63C-81AD-4291-9C1B-84967277EEF6"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_8962C15F-55EC-46F7-B926-5D5A1FD8D35E"), "_A76C6603-0406-423C-940B-3403948DCA1F");
        factory.connection(org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_57DBE42C-6850-4A08-AC73-2BB0FFDFCC8C"), org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory.fromExternalFormat("_B8C4F63C-81AD-4291-9C1B-84967277EEF6"), "_ACEE7578-B7D2-4EDF-B104-9ECF3DD8A383");
        factory.validate();
        return factory.getProcess();
    }
}
