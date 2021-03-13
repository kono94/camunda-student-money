var studentData = execution.getVariable("rawStudentXML");
var plz = S(studentData).childElement("plz").textContent();
plz;
