var studentData = execution.getVariable("rawStudentXML");
var avgScore = S(studentData).childElement("avgScore").textContent();
avgScore;
