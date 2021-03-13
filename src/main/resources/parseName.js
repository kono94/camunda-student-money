var studentData = execution.getVariable("rawStudentXML");
var name = S(studentData).childElement("name").textContent();
name;
