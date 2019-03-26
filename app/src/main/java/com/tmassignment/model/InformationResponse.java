package com.tmassignment.model;

import java.util.ArrayList;

/**
 * Created by Sumedha Vats on 26-03-2019.
 */
public class InformationResponse {
    ArrayList<CompatibilityQuestions> compatibility_questions;


    public ArrayList<CompatibilityQuestions> getCompatibility_questions() {
        return compatibility_questions;
    }

    public void setCompatibility_questions(ArrayList<CompatibilityQuestions> compatibility_questions) {
        this.compatibility_questions = compatibility_questions;
    }


    public class CompatibilityQuestions {
        int id;
        String question;
        String tick;
        String cross;
        Style style;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getTick() {
            return tick;
        }

        public void setTick(String tick) {
            this.tick = tick;
        }

        public String getCross() {
            return cross;
        }

        public void setCross(String cross) {
            this.cross = cross;
        }

        public Style getStyle() {
            return style;
        }

        public void setStyle(Style style) {
            this.style = style;
        }
    }

    public class Style {
        String medium;
        String large;
        String thumb;
        String original;

        public Style(String medium, String large, String thumb, String original) {
            this.medium = medium;
            this.large = large;
            this.thumb = thumb;
            this.original = original;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }


}


