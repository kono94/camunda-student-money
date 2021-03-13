var studentData = execution.getVariable("rawStudentXML");
var street = S(studentData).childElement("street").textContent();
street;
