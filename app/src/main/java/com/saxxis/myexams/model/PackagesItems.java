package com.saxxis.myexams.model;

/**
 * Created by android2 on 6/8/2017.
 */

public class PackagesItems {

    private String id;
    private String quiz_category;
    private String quiz_subject;
    private String quiz_sublevel;
    private String quiz_subchapter;
    private String quiz_pack;
    private String quiz_offer_type;
    private String quiz_num;
    private String questions_num;
    private String quiz_el;
    private String quiz_tet;
    private String quiz_ques;
    private String quiz_offer_price;
    private String quiz_offer_description;
    private String quiz_offer_readmore;
    private String quiz_offer_title;
    private String quiz_offer_image;
    private String published;
    private String Subjects;


    public PackagesItems(String id,String quiz_category,String quiz_subject,
                         String quiz_sublevel,String quiz_subchapter,String quiz_pack,
                         String quiz_offer_type,String quiz_num,String questions_num,
                         String quiz_el,String quiz_tet,String quiz_ques,String quiz_offer_price,
                         String quiz_offer_description,String quiz_offer_readmore,String quiz_offer_title,
                         String quiz_offer_image,String published,String Subjects){
        this.id=id;
        this.quiz_category=quiz_category;
        this.quiz_subject=quiz_subject;
        this.quiz_sublevel=quiz_sublevel;
        this.quiz_subchapter=quiz_subchapter;
        this.quiz_pack=quiz_pack;
        this.quiz_offer_type=quiz_offer_type;
        this.quiz_num=quiz_num;
        this.questions_num=questions_num;
        this.quiz_el=quiz_el;
        this.quiz_tet=quiz_tet;
        this.quiz_ques=quiz_ques;
        this.quiz_offer_price=quiz_offer_price;
        this.quiz_offer_description=quiz_offer_description;
        this.quiz_offer_readmore=quiz_offer_readmore;
        this.quiz_offer_title=quiz_offer_title;
        this.quiz_offer_image=quiz_offer_image;
        this.published=published;
        this.Subjects=Subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuiz_category() {
        return quiz_category;
    }

    public void setQuiz_category(String quiz_category) {
        this.quiz_category = quiz_category;
    }

    public String getQuiz_subject() {
        return quiz_subject;
    }

    public void setQuiz_subject(String quiz_subject) {
        this.quiz_subject = quiz_subject;
    }

    public String getQuiz_sublevel() {
        return quiz_sublevel;
    }

    public void setQuiz_sublevel(String quiz_sublevel) {
        this.quiz_sublevel = quiz_sublevel;
    }

    public String getQuiz_subchapter() {
        return quiz_subchapter;
    }

    public void setQuiz_subchapter(String quiz_subchapter) {
        this.quiz_subchapter = quiz_subchapter;
    }

    public String getQuiz_pack() {
        return quiz_pack;
    }

    public void setQuiz_pack(String quiz_pack) {
        this.quiz_pack = quiz_pack;
    }

    public String getQuiz_offer_type() {
        return quiz_offer_type;
    }

    public void setQuiz_offer_type(String quiz_offer_type) {
        this.quiz_offer_type = quiz_offer_type;
    }

    public String getQuiz_num() {
        return quiz_num;
    }

    public void setQuiz_num(String quiz_num) {
        this.quiz_num = quiz_num;
    }

    public String getQuestions_num() {
        return questions_num;
    }

    public void setQuestions_num(String questions_num) {
        this.questions_num = questions_num;
    }

    public String getQuiz_el() {
        return quiz_el;
    }

    public void setQuiz_el(String quiz_el) {
        this.quiz_el = quiz_el;
    }

    public String getQuiz_tet() {
        return quiz_tet;
    }

    public void setQuiz_tet(String quiz_tet) {
        this.quiz_tet = quiz_tet;
    }

    public String getQuiz_ques() {
        return quiz_ques;
    }

    public void setQuiz_ques(String quiz_ques) {
        this.quiz_ques = quiz_ques;
    }

    public String getQuiz_offer_price() {
        return quiz_offer_price;
    }

    public void setQuiz_offer_price(String quiz_offer_price) {
        this.quiz_offer_price = quiz_offer_price;
    }

    public String getQuiz_offer_description() {
        return quiz_offer_description;
    }

    public void setQuiz_offer_description(String quiz_offer_description) {
        this.quiz_offer_description = quiz_offer_description;
    }

    public String getQuiz_offer_readmore() {
        return quiz_offer_readmore;
    }

    public void setQuiz_offer_readmore(String quiz_offer_readmore) {
        this.quiz_offer_readmore = quiz_offer_readmore;
    }

    public String getQuiz_offer_title() {
        return quiz_offer_title;
    }

    public void setQuiz_offer_title(String quiz_offer_title) {
        this.quiz_offer_title = quiz_offer_title;
    }

    public String getQuiz_offer_image() {
        return quiz_offer_image;
    }

    public void setQuiz_offer_image(String quiz_offer_image) {
        this.quiz_offer_image = quiz_offer_image;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }


    public String getSubjects() {
        return Subjects;
    }

    public void setSubjects(String subjects) {
        this.Subjects = subjects;
    }
}
