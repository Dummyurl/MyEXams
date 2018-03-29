package com.saxxis.myexams.app;

/**
 * Created by android2 on 5/31/2017.
 */

public class AppConstants {

    public static final String SERVER_URL="http://myexamspace.com/";

    public static final String USER_LOGIN=SERVER_URL+"index.php?option=com_jbackend&view=request&action=post&module=user&resource=login&username=";

    public static final String USER_REGISTER=SERVER_URL+"index.php?option=com_jbackend&view=request&action=post&module=user&resource=register&name=";

    public static final String USER_SOCIAL_LOGIN = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=facebooklogin&response=";

    public static final String USER_G_PLUS_LOGIN = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=googlepluslogin&response=";

    public static final String EXAMS_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=examlist";//"index.php?option=com_jbackend&view=request&action=get&module=user&resource=catagory";

    public static final String EXAMS_SUBCAT_LIST=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=getsubjects&categoryid=";

    public static final String EXAMS_DETAILS_LIST=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=getsubjectsdetails&subjectid=";

    public static final String SUBJECTS_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=sublist";

    public static final String SUBJECTS_SUB_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=childsublist&subid=";

    public static final String CURRENT_AFFAIRS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=";

    public static final String HOME_SLIDER=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=sliders";

    public static final String ACADEMIC_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=academiclist";

    public static final String ACADEMIC_SUB_LIST_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=acadamicdetails&categoryid=";

    public static final String TRENDING_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=trending";

    public static final String NOTIFICATIONS_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=notifications";

    public static final String UPCOMING_NOTIFICATIONS_URL = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=upcomingnotifications";

    public static final String DETAILED_NOTIFICATIONS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=jobdetails&jobid=";

    public static final String PACKAGES_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=packages";

    public static final String COMBO_PACKAGES_URL=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=combopackages";

    public static final String PACKAGE_IMAGE_URL=SERVER_URL+"images/package/";

    public static final String PDF_URL = SERVER_URL+"images/files/";

    ///  main profile tabbed items urls
    public static final String MY_EXAM_RESULTS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=result&userid=";

    public static final String MY_EXAM_RESULT_VIEW = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=viewresult&TicketId=";
                                                        //index.php?option=com_jbackend&view=request&action=get&module=user&resource=viewresult&TicketId=
    public static final String MY_PURCHASE_PACKAGE_RESULTS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=myorders&ordernum=";

    public static final String MY_PACKAGES_DETAILS = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=mypackages&userid=";

    public static final String MY_PACKAGE_SINGLE_EXAMS_LIST =SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=viewdetailspackages&subchapterid=";

    public static final String MY_PACKAGE_COMBO_EXAM_LIST = SERVER_URL + "index.php?option=com_jbackend&view=request&action=get&module=user&resource=viewcombodetailslist&userid=";

    public static final String QUESTIONS_LIST = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=questionlist&quizid=";

    public static final String EducationNews =SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=news";

    public static final String CATEGORY_FILTER_PACKAGE=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=";

    public static final String SEARCH_FILTER_PACKAGE = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=filterpackage&catid=";

    public static final String FILTER_NOTIFICATIONS_CAT =SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=";

    public static final String SEARCH_FILTER_NOTIFICATIONS = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=filternotification&deptid=";

    public static final String SEARCH_COMBO_FILTER_PACKAGE = SERVER_URL + "index.php?option=com_jbackend&view=request&action=get&module=user&resource=filtercombopackages";

    public static final String RESULT_ANALYSIS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=analysis&TicketId=";

    public static final String EXPLANATION_EXAM_RESULT=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=explanation&TicketId=";

    public static final String CURRENT_AFFAIRS_QUIZZ=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=cquiz&language=";//2&months=2017-09";

    public static final String FILTER_CURRENT_AFFAIRSQUIZZ=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=examtype";

    /*
    * latest update category in current affairs
     */
    public static final String LATEST_UPDATES_CATEGORY_CURRENTAFFAIRS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=catlist&categoryid=";
/*
    * latest update category in latest updates
     */
    public static final String LATEST_UPDATES_CATEGORY_LATESTUPDATES=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=catlist&categoryid=";

    public static final String LATEST_UPDATES_DETAILS=SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=latestupdates&uid=";

    public static final String PROFILE_IMAGE=SERVER_URL+"index.php?option=com_jbackend&view=request&action=post&module=user&resource=addprofileimage&userid=";

    public static final String MY_FAVOURITEXS = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=myfeve&userid=";

    public static final String ADD_FAVOURITE = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=addingmyfevquestion&questionid=";

    public static final String REMOVE_FACOURITE = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=deletefev&QuestionVersionId=";

    public static final String ADD_REPORT = SERVER_URL+"index.php?option=com_jbackend&view=request&action=post&module=user&resource=addreport&questionid=";

    public static final String EDIT_MYPROFILE = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=updateprofile";

    public static final String TERMS_AND_CONDITIONS = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=contents";

    public static final String FORGOT_PASSWORD = SERVER_URL + "index.php?option=com_jbackend&view=request&action=get&module=user&resource=forgotpassword&email=";

    public static final String FORGOT_CHANGEPASSWORD = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=resetpassword&email=";

    public static final int PERMISSION_REQUEST_CODE = 0x009;

    public static final int CONTACTS_PERMISSION_REQUEST_CODE = 0x009;

    public static final int SELECT_PROFILE_PICTURE = 0x005;

    public static final int SELECT_PROFILE_CAMERA = 0x006;

    public static final int PLAN_REQUEST = 0x001;

    public static final String ADD_IMAGE = SERVER_URL + "index.php?option=com_jbackend&view=request&action=post&module=user&resource=addImage";

    /*
    * submit answers url*/
    public static final String SUBMITANSWER =  SERVER_URL + "index.php?option=com_jbackend&view=request&action=post&module=user&resource=answersubmit&staticId=";

    public static final String TOTAL_EXAM_SUBMIT = SERVER_URL+"index.php?option=com_jbackend&view=request&action=post&module=user&resource=examsubmit&TicketId=";

    public static final String CA_MUST_READ = SERVER_URL+"index.php?option=com_jbackend&view=request&module=user&action=get&resource=news&categoryid=";

    public static final String CA_MUST_READ_CATEGORY = SERVER_URL+"index.php?option=com_jbackend&view=request&module=user&action=get&resource=mustread&pid=";

    public static final String SLIDER_DETAILS = SERVER_URL+"index.php?option=com_jbackend&view=request&action=get&module=user&resource=sliderslink&sliderid=";

}
