package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

public class ActivationLinkTableEntry {
    private String activationLink;
    private Student student;
    private boolean activated;

    public ActivationLinkTableEntry(String activationLink, Student student) {
        this.activationLink = activationLink;
        this.student = student;
        this.activated = false;
    }

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
