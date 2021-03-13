package net.lwenstrom.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Do not implement Serializable interface, because it will be of type "x-java-serialized-object", which
 * cannot be interpreted by the (javascript) front-end. By default camunda will serialize java objects as
 * "application/json" which then can be fetched and used by the front-end.
 *
 * Add @XMLElement and the corresponding SOAP namespace to each field...
 */
@XmlRootElement(name="student", namespace="lwenstrom.net/ws/students")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {

    @XmlElement(namespace="lwenstrom.net/ws/students")
    private String name;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private String surname;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private long studentID;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private double avgScore;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private XMLGregorianCalendar birthday;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private String iban;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private String street;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private int plz;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    private String city;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public XMLGregorianCalendar getBirthday() {
        return birthday;
    }

    public void setBirthday(XMLGregorianCalendar birthday) {
        this.birthday = birthday;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
