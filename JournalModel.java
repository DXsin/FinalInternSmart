package com.example.terence.internsmartassistant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Terence on 9/23/2017.
 */

public class JournalModel {
     String message;
     String in;
     String out;
    String program;
    String university;
    String company;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public JournalModel() {
    }

//    public JournalModel(String message, String in, String out) {
//        this.message = message;
//        this.in = in;
//        this.out = out;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JournalModel that = (JournalModel) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (in != null ? !in.equals(that.in) : that.in != null) return false;
        if (out != null ? !out.equals(that.out) : that.out != null) return false;
        if (program != null ? !program.equals(that.program) : that.program != null) return false;
        if (university != null ? !university.equals(that.university) : that.university != null)
            return false;
        return company != null ? company.equals(that.company) : that.company == null;

    }


}
