var studentData = execution.getVariable("rawStudentXML");
var iban = S(studentData).childElement("iban").textContent();
iban;
