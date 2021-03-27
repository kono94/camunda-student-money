package net.lwenstrom;

public class ProcessConstants {

  public static final String PROCESS_DEFINITION_KEY = "student-money"; // BPMN Process ID

  public static final String PROCESS_REJECTION_MESSAGE = "rejectionMessage";
  public static final String PROCESS_DATA_VALIDATION_APPROVED = "dataValidationApproved";
  public static final String PROCESS_DUPLICATION_CHECK_APPROVED = "duplicationCheckApproved";

  // used in the process model
  public static final String VAR_STUDENT_ID = "studentID";
  public static final String VAR_STUDENT = "student";
  public static final String VAR_STUDENT_RAW_XML = "rawStudentXML";
  public static final String VAR_ACTIVATION_LINK = "activationLink";

  // printed request has been archived by the AStA office
  public static final String VAR_ARCHIVED = "archived";
}
