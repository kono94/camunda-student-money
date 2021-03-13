var studentData = execution.getVariable("rawStudentXML");
var surname = S(studentData).childElement("surname").textContent();
surname;
