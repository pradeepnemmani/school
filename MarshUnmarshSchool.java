package school;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.List;

/**
 * Created by jan on 19/4/14.
 */
public class MarshUnmarshSchool {

    public static final String schoolSchema = "/home/jan/IdeaProjects/xml/src/school.xsd";
    private static Schema schema = null;

    public static School createData(ObjectFactory of) {
        School s = of.createSchool();
        s.setSchoolName("Hydrogen");

        BoardMember bm = MarshUnmarshSchool.createBoardMember(of, "amar", 10, createCalender(2121, 02, 02));
        BoardMember bm1 = MarshUnmarshSchool.createBoardMember(of, "Jan", 22, createCalender(125, 02, 12));
        BoardMembers bms = new BoardMembers();
        bms.getBoardMember().add(bm);
        bms.getBoardMember().add(bm1);
        s.setBoardMembers(bms);

        Student stu = MarshUnmarshSchool.createStudent(of, "amaarnath", 12, 5);
        Student stu1 = MarshUnmarshSchool.createStudent(of, "abc", 125, 45);

        Members mems = new Members();
        mems.getMember().add(stu);
        mems.getMember().add(stu1);
        s.setMembers(mems);

        Address add = new Address();
        add.setLandmark("madhapur");
        add.setArea("mdpur");
        add.setStreet("ayyapa Society");
        add.setCity("hyderabad");
        add.setState("Andhra Pradesh");
        add.setCountry("India");
        add.setZipcode("50132");

        s.setAddress(add);
        return s;
    }

    public static BoardMember createBoardMember(ObjectFactory of, String bName, int bId, XMLGregorianCalendar bDate) {
        BoardMember bm = of.createBoardMember();
        bm.setName(bName);
        bm.setId(bId);
        bm.setFrom(bDate);
        return bm;
    }

    public static Student createStudent(ObjectFactory of, String sName, int sId, int sClass) {
        Student stu = of.createStudent();
        stu.setName(sName);
        stu.setId(sId);
        stu.setClazz(sClass);
        return stu;

    }

    public static XMLGregorianCalendar createCalender(int year, int month, int day) {
        XMLGregorianCalendar xgc = null;

        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
            xgc = df.newXMLGregorianCalendarDate(year, month, day, 0);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xgc;
    }

    public static Schema createSchema(String fileName) throws Exception {
        if (schema != null) {
            SchemaFactory sf = SchemaFactory.newInstance(MarshUnmarshSchool.schoolSchema);
            schema = sf.newSchema(new File(fileName));
        }
        return schema;

    }

    public static void serilaizable(JAXBContext ctxt, Object obj, String output) throws Exception {
        if (ctxt != null) {
            Marshaller m = ctxt.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Schema schema = MarshUnmarshSchool.createSchema(MarshUnmarshSchool.schoolSchema);
            m.setSchema(schema);
            m.marshal(obj, new File(output));

        }

    }

    public static School deserialize(JAXBContext ctxt, String xmlFile) throws Exception {
        if (ctxt != null) {
            try {
                Unmarshaller um = ctxt.createUnmarshaller();
                Schema schema = MarshUnmarshSchool.createSchema(MarshUnmarshSchool.schoolSchema);
                um.setSchema(schema);
                Object unobj = um.unmarshal(new File(xmlFile));
                if (unobj instanceof School) {
                    return (School) unobj;
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static void printUnmarshallData(School s) {
        System.out.println("printing unmarshalling data");
        System.out.println("School Name is : " + s.getSchoolName());
        BoardMembers bms = s.getBoardMembers();
        Members members = s.getMembers();
        Address add = s.getAddress();
        if (bms != null && members != null) {
            List<BoardMember> boardMemberList = bms.getBoardMember();
            List<Student> studentList = members.getMember();
            if (boardMemberList != null && studentList != null && !studentList.isEmpty() && !boardMemberList.isEmpty()) {
                for (BoardMember bm : boardMemberList) {
                    System.out.println("-------------------------");
                    System.out.println("Member Name :" + bm.getName());
                    System.out.println("Member Id :" + bm.getId());
                    System.out.println("Member From :" + bm.getFrom());
                }

                for (Member member : studentList) {
                    System.out.println("----------Student Details---------------");
                    System.out.println("Student Name :" + member.getName());
                    System.out.println("Student Id :" + member.getId());
                    System.out.println("Student Class :" + member.getClass());
                }

                System.out.println("-------------Address-----------");
                System.out.println("Area :" + add.getArea());
                System.out.println("Street :" + add.getStreet());
                System.out.println("Landmark :" + add.getLandmark());
                System.out.println("City :" + add.getCity());
                System.out.println("State :" + add.getState());
                System.out.println("Country :" + add.getCountry());
                System.out.println("zip code :" + add.getZipcode());
            }

        }
    }

    public static void main(String[] args) {

        try {
            ObjectFactory of = new ObjectFactory();
            School s = MarshUnmarshSchool.createData(of);
            JAXBContext ctxt = JAXBContext.newInstance("school");
            MarshUnmarshSchool.serilaizable(ctxt, s, "/tmp/out.xml");
            System.out.println("Unmarshalling");
            School ss = MarshUnmarshSchool.deserialize(ctxt, "/tmp/out.xml");
            MarshUnmarshSchool.printUnmarshallData(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
