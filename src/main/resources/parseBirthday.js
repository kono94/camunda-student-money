var studentData = execution.getVariable("rawStudentXML");
var birthday = S(studentData).childElement("birthday").textContent();
birthday;
