var studentData = execution.getVariable("rawStudentXML");
var city = S(studentData).childElement("city").textContent();
city;
