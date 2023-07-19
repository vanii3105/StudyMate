package com.example.StudyMate;

public class putPDF {

    String dept;
    String sem;
    String subject;
    String topic;
    String url;
    String name;

    public putPDF() {
    }

    public putPDF(String dept, String sem, String subject, String topic, String url, String name) {
        this.dept = dept;
        this.sem = sem;
        this.subject = subject;
        this.topic = topic;
        this.url = url;
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
