package com.example.administrator.teacher.bean.bean.requset;

/**
 * @author yhy created at 2017/4/1 11:25
 */

public class StudentInfoBean {

    public StudentInfoBean(String UName, int USex, String UHeadImg, String UPwd, String UStudentAge, String UStudentSchool, String UStudentMajor, String UStudentGrade, String UStudentDegreen) {
        this.UName = UName;
        this.USex = USex;
        this.UHeadImg = UHeadImg;
        this.UPwd = UPwd;
        this.UStudentAge = UStudentAge;
        this.UStudentSchool = UStudentSchool;
        this.UStudentMajor = UStudentMajor;
        this.UStudentGrade = UStudentGrade;
        this.UStudentDegreen = UStudentDegreen;
    }

    /**
     * UName : 123
     * USex : 1
     * UHeadImg : 1234142
     * UPwd : 1234234
     * UStudentAge : 12342354
     * UStudentSchool : 3453
     * UStudentMajor : 142354345
     * UStudentGrade : 34654
     * UStudentDegreen : 235
     */

    private String UName;
    private int USex;
    private String UHeadImg;
    private String UPwd;
    private String UStudentAge;
    private String UStudentSchool;
    private String UStudentMajor;
    private String UStudentGrade;
    private String UStudentDegreen;

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public int getUSex() {
        return USex;
    }

    public void setUSex(int USex) {
        this.USex = USex;
    }

    public String getUHeadImg() {
        return UHeadImg;
    }

    public void setUHeadImg(String UHeadImg) {
        this.UHeadImg = UHeadImg;
    }

    public String getUPwd() {
        return UPwd;
    }

    public void setUPwd(String UPwd) {
        this.UPwd = UPwd;
    }

    public String getUStudentAge() {
        return UStudentAge;
    }

    public void setUStudentAge(String UStudentAge) {
        this.UStudentAge = UStudentAge;
    }

    public String getUStudentSchool() {
        return UStudentSchool;
    }

    public void setUStudentSchool(String UStudentSchool) {
        this.UStudentSchool = UStudentSchool;
    }

    public String getUStudentMajor() {
        return UStudentMajor;
    }

    public void setUStudentMajor(String UStudentMajor) {
        this.UStudentMajor = UStudentMajor;
    }

    public String getUStudentGrade() {
        return UStudentGrade;
    }

    public void setUStudentGrade(String UStudentGrade) {
        this.UStudentGrade = UStudentGrade;
    }

    public String getUStudentDegreen() {
        return UStudentDegreen;
    }

    public void setUStudentDegreen(String UStudentDegreen) {
        this.UStudentDegreen = UStudentDegreen;
    }
}
