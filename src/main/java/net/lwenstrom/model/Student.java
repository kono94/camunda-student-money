package net.lwenstrom.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Do not implement Serializable interface, because it will be of type "x-java-serialized-object", which
 * cannot be interpreted by the (javascript) front-end. By default camunda will serialize java objects as
 * "application/json" which then can be fetched and used by the front-end.
 *
 * Add @XMLElement and the corresponding SOAP namespace to each field...
 *
 * ATTENTION!!!
 * The SPIN library will serialize java objects either as XML or JSON non deterministic (PURE RANDOM,
 * which plugin will get loaded first from the classpath). If the front-end embedded form fetches this
 * object (from process variable) it will 50% of the time be in a front-end readable format (JSON) but in 50%
 * of cases it will get serialized as XML which cannot be interpreted by the front-end. Because of this
 * jackson's @JsonSerialize annotation is used here in hope (it does not work) that it will intercept SPIN from
 * interfering with the serialization. Normally the default SerializableDataFromat can be configured in the
 * servlet container but as we are using the official docker container this is not an option...
 * See:
 * https://docs.camunda.org/manual/latest/reference/spin/extending-spin/#configuring-data-formats
 * https://forum.camunda.org/t/serialization-problem-switch-to-xml/16795/2
 */
@XmlRootElement(name="student", namespace="lwenstrom.net/ws/students")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize
public class Student {

    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected String name;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected String surname;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected long studentID;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected double avgScore;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected XMLGregorianCalendar birthday;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected String iban;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected String street;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected int plz;
    @XmlElement(namespace="lwenstrom.net/ws/students")
    protected String city;


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
