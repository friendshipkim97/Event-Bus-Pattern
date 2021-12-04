package Components.Constant;

public class Constants {

    public enum EClientInputMain{

        eEventBus("EventBus"),
        eClientInputMainID("** ClientInputMain(ID:"),
        eClientInputMainRegister(") is successfully registered. \n"),
        eQuitTheSystem("Quit the system!!!"),
        ePleaseChooseTheRightMenu("Please choose the right menu."),
        eEnterStudentId("\nEnter student ID and press return (Ex. 20131234)>> "),
        eEnterFamilyName("\nEnter family name and press return (Ex. Hong)>> "),
        eEnterFirstName("\nEnter first name and press return (Ex. Gildong)>> "),
        eEnterDepartment("\nEnter department and press return (Ex. CS)>> "),
        eEnterCompletedCourses("\nEnter a list of IDs (put a space between two different IDs) of the completed courses and press return >> "),
        eEnterCompletedCoursesEX("(Ex. 17651 17652 17653 17654)"),
        eMessage("\n ** Message: "),
        eEnter("\n"),
        eEnterCourseId("\nEnter course ID and press return (Ex. 12345)>> "),
        eEnterFamilyNameOfInstructor("\nEnter the family name of the instructor and press return (Ex. Hong)>> "),
        eEnterCourseNames("\nEnter the name of the course ( substitute a space with ab underbar(_) ) and press return (Ex. C++_Programming)>> "),
        eEnterCompletedCoursesId("\nEnter a list of IDs (put a space between two different IDs) of prerequisite courses and press return >> "),
        eEnterCompletedCoursesIdEX("(Ex. 12345 17651)"),
        eSetStudentId("\nEnter student ID and press return (Ex. 20131234)>> "),
        eSetCourseId("\nEnter course ID and press return (Ex. 12345)>> "),
        eSetStudentIdAndCourseId("\nEnter course ID, student ID and press return (Ex. 12345 20131234)>> "),
        eMenuOne("[1]. List Students"),
        eMenuTwo("[2]. List Courses"),
        eMenuThree("[3]. Register a new Student"),
        eMenuFour("[4]. Register a new Course"),
        eMenuFive("[5]. Delete a Student"),
        eMenuSix("[6]. Delete a Course"),
        eMenuSeven("[7]. Application For Course"),
        eMenuEight("[0]. Quit the system"),
        eMenuChooseNumber("\n Choose No.: "),
        eSendEventMessage("\n** Sending an event(ID:"),
        eSendEventMessageEnter(")\n"),
        eMatchContainNumber(".*[0-9].*"),
        eMatchOnlyNumber("[0-9]+"),
        eMatchManyCourseNumbers("^[0-9\\s]*$"),
        eMatchCourseIdAndStudentId("^[0-9\\s]*$"),
        eStudentNameMatchMessage("학생이름이 비었거나 숫자를 포함할 수 없습니다."),
        eStudentNumberMatchMessage("학생번호가 비었거나 학생번호에 숫자만 포함할 수 있습니다."),
        eMajorMatchMessage("전공이 비었거나 전공에 숫자를 포함할 수 없습니다."),
        eManyCourseNumbersMatchMessage("강좌번호에 숫자만 허용됩니다."),
        eCourseIdAndStudentIdMatchMessage("강좌번호와 학생번호에 숫자만 허용됩니다."),
        eCourseNumberMatchMessage("강좌번호가 비었거나 숫자만 포함되어야 합니다."),
        eProfessorLastNameMatchMessage("교수 성이 비었거나 숫자가 포함되서는 안됩니다."),
        eCourseNameMatchMessage("강좌 이름에 숫자가 포함되서는 안됩니다."),
        eAdvancedCourseNumberMatchMessage("선이수 강좌번호에 숫자만 포함되어야 합니다."),
        eSpace(" "),
        eEmpty(""),
        eFalse(false),
        eTrue(true);

        private Boolean check;
        private String content;
        private int number;
        EClientInputMain(String content) { this.content = content;}
        EClientInputMain(Boolean check) { this.check = check; }
        public String getContent() { return content; }
        public Boolean getCheck() { return check; }
    }

    public enum EClientOutputMain{
        eEventBus("EventBus"),
        eClientOutputMainID("** ClientOutputMain (ID:"),
        eClientOutputMainRegister(") is successfully registered. \n"),
        eThreadTime(1000),
        eZero(0),
        eFalse(false);

        private Boolean check;
        private String content;
        private int number;
        EClientOutputMain(String content) { this.content = content;}
        EClientOutputMain(int number) { this.number = number; }
        EClientOutputMain(Boolean check) { this.check = check; }
        public String getContent() { return content; }
        public int getNumber() { return number; }
        public Boolean getCheck() { return check; }
    }

    public enum ECourse{
        eZero(0),
        eSpace(" ");

        private String content;
        private int number;
        ECourse(String content) { this.content = content;}
        ECourse(int number) { this.number = number; }
        public int getNumber() { return number; }
        public String getContent() { return content; }
    }

    public enum ECourseComponent{
        eEmpty(""),
        eZero(0);

        private String content;
        private int number;
        ECourseComponent(String content) { this.content = content;}
        ECourseComponent(int number) { this.number = number; }
        public String getContent() { return content; }
        public int getNumber() { return number; }
    }

    public enum ECourseMain{

        eEventBus("EventBus"),
        eCourseMainID("** ClientInputMain(ID:"),
        eCourseMainRegister(") is successfully registered. \n"),
        eCourseFileName("Courses.txt"),
        eEventGet("Get"),
        eEventDelete("Delete"),
        eEventApplicationForCourse("applicationForCourse"),
        eLogEventMessage1("\n** "),
        eLogEventMessage2(" the event(ID:"),
        eLogEventMessage3(") message: "),
        eDeleteCourseSuccessMessage("This course is successfully deleted."),
        eDeleteCourseFailMessage("There is no course"),
        eRegisterCourseSuccessMessage("This course is successfully added."),
        eRegisterCourseFailMessage("This Course is already registered."),
        eValidationCourseNumberMessage("This course number is an unregistered course number."),
        eMessageSplitFormat("\\s"),
        eSpace(" "),
        eEnter("\n"),
        eEmpty(""),
        eZero(0),
        eThreadTime(1000),
        eFalse(false),
        eTrue(true);

        private String content;
        private Boolean check;
        private int number;
        ECourseMain(String content) { this.content = content;}
        ECourseMain(Boolean check) { this.check = check; }
        ECourseMain(int number) { this.number = number; }
        public String getContent() { return content; }
        public Boolean getCheck() { return check; }
        public int getNumber() { return number; }
    }

    public enum EStudent{
        eZero(0),
        eSpace(" ");

        private String content;
        private int number;
        EStudent(String content) { this.content = content;}
        EStudent(int number) { this.number = number; }
        public int getNumber() { return number; }
        public String getContent() { return content; }
    }

    public enum EStudentComponent{

        eEmpty(""),
        eZero(0),
        eMinusOne(-1);

        private String content;
        private int number;
        EStudentComponent(String content) { this.content = content;}
        EStudentComponent(int number) { this.number = number; }
        public String getContent() { return content; }
        public int getNumber() { return number; }

    }

    public enum EStudentMain{

        eEventBus("EventBus"),
        eStudentMainID("** StudentMain(ID:"),
        eStudentMainRegister(") is successfully registered. \n"),
        eStudentFileName("Students.txt"),
        eEventGet("Get"),
        eEventDelete("Delete"),
        eEventValidation("validation"),
        eLogEventMessage1("\n** "),
        eLogEventMessage2(" the event(ID:"),
        eLogEventMessage3(") message: "),
        eDeleteStudentSuccessMessage("This student is successfully deleted."),
        eDeleteStudentFailMessage("There is no student"),
        eRegisterStudentSuccessMessage("This student is successfully added."),
        eRegisterStudentFailMessage("This student is already registered."),
        eApplicationForCourseSuccessMessage("You have completed the course application."),
        eStudentNumberNotRegisteredMessage("This student number is an unregistered student number."),
        eAlreadyTakingCourseMessage("The student is already taking the course."),
        eNotCompletedAdvancedCourses("The student did not complete the advanced courses."),
        eEmptyRequestStudentExceptionMessage("THE STUDENT INPUT IS A EMPTY VALUE."),
        eMessageSplitFormat("\\s+"),
        eSpace(" "),
        eEnter("\n"),
        eEmpty(""),
        eZero(0),
        eOne(1),
        eTwo(2),
        eThreadTime(1000),
        eFalse(false),
        eTrue(true);

        private String content;
        private Boolean check;
        private int number;
        EStudentMain(String content) { this.content = content;}
        EStudentMain(Boolean check) { this.check = check; }
        EStudentMain(int number) { this.number = number; }
        public String getContent() { return content; }
        public Boolean getCheck() { return check; }
        public int getNumber() { return number; }
    }

    public enum EEvent{

        eSerialVersionUID(1L);

        private Long number;
        EEvent(Long number) { this.number = number;}
        public Long getNumber() { return number; }
    }

    public enum EEventQueue{

        eSerialVersionUID(1L),
        eInitialCapacity(15),
        eCapacityIncrement(1),
        eZero(0);

        private int number;
        private Long id;
        EEventQueue(Long id) { this.id = id;}
        EEventQueue(int number) { this.number = number; }
        public int getNumber() { return number; }
        public Long getId() { return id; }
    }

    public enum ERMIEventBusImpl{

        eSerialVersionUID(1L),
        eInitialCapacity(15),
        eCapacityIncrement(1),
        eZero(0),
        eEventBus("EventBus"),
        eEventBusRunningNow("Event Bus is running now..."),
        eEventBusRunningError("Event bus startup error: "),
        eRegisterMessage1("Component (ID:"),
        eRegisterMessage2(") is registered..."),
        eUnRegisterMessage1("Component (ID:"),
        eUnRegisterMessage2(") is unregistered..."),
        eEventQueue("eventQueue"),
        eSendEventMessage1("Event Information(ID: "),
        eSendEventMessage2(", Message: "),
        eSendEventMessage3(")");

        private String content;
        private int number;
        private Long id;
        ERMIEventBusImpl(String content) { this.content = content;}
        ERMIEventBusImpl(Long id) { this.id = id;}
        ERMIEventBusImpl(int number) { this.number = number; }
        public String getContent() { return content; }
        public int getNumber() { return number; }
        public Long getId() { return id; }
    }


}
