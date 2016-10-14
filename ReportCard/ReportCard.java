package com.example.android.miwok;

/**
 * Created by Aadam on 7/30/2016.
 */
public class ReportCard {
    private final static String SCHOOL_NAME = "University of Hairpur";
    private final static String TEACHER_NAME = "Aadam";
    private final static int NUMBER_OF_SUBJECTS = 4;

    private double mEnglishMarks;
    private double mUrduMarks;
    private double mComputerScienceMarks;
    private double mPakStudiesMarks;

    public ReportCard() {
        mEnglishMarks = 0;
        mUrduMarks = 0;
        mComputerScienceMarks = 0;
        mPakStudiesMarks = 0;
    }

    public ReportCard(double englishMarks, double urduMarks, double computerScienceMarks, double pakStudiesMarks) {
        mEnglishMarks = englishMarks;
        mUrduMarks = urduMarks;
        mComputerScienceMarks = computerScienceMarks;
        mPakStudiesMarks = pakStudiesMarks;
    }

    public double getGrade() {
        return (mEnglishMarks + mUrduMarks + mComputerScienceMarks + mPakStudiesMarks)/NUMBER_OF_SUBJECTS;
    }

    public double getEnglishMarks() {
        return mEnglishMarks;
    }

    public void setEnglishMarks(double englishMarks) {
        mEnglishMarks = englishMarks;
    }

    public double getUrduMarks() {
        return mUrduMarks;
    }

    public void setUrduMarks(double urduMarks) {
        mUrduMarks = urduMarks;
    }

    public double getComputerScienceMarks() {
        return mComputerScienceMarks;
    }

    public void setComputerScienceMarks(double computerScienceMarks) {
        mComputerScienceMarks = computerScienceMarks;
    }

    public double getPakStudiesMarks() {
        return mPakStudiesMarks;
    }

    public void setPakStudiesMarks(double pakStudiesMarks) {
        mPakStudiesMarks = pakStudiesMarks;
    }

    @Override
    public String toString() {
        return "ReportCard:" +
                "\nSchool Name = '" + SCHOOL_NAME + '\'' +
                "\nTeacher Name = '" + TEACHER_NAME + '\'' +
                "\nEnglish Marks = " + mEnglishMarks +
                "\nUrdu Marks = " + mUrduMarks +
                "\nComputer Science Marks = " + mComputerScienceMarks +
                "\nPak Studies Marks = " + mPakStudiesMarks +
                "\nGrade = " + getGrade() ;
    }
}
