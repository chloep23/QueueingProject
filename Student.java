//Student.java
//Date: 20220415
//Author: Chloe Park and Marton Sharpe
//Purpose: Queueing Project Code for Student Class

public class Student { //blueprint for Student objects (name, GPA)
    public String fullName = "";
    public double grade = 0.0;

    public Student (String fullName, double grade){
        this.fullName = fullName; //format: (last name, first name)
        this.grade = grade; //GPA
    }
}
