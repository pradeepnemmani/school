package school;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jan on 17/4/14.
 */
public class TestSchool {
    public static void main(String[] args) {
        ObjectFactory of = new ObjectFactory();
        School s = of.createSchool();

        BoardMember bm = new BoardMember();
        bm.setName("amar");
        bm.setId(1);


        BoardMember bm1 = new BoardMember();
        bm1.setName("jan");
        bm1.setId(2);


        Student student = new Student();
        student.setName("abc");
        student.setId(11);

        Student student1 = new Student();
        student1.setName("cde");
        student1.setId(22);
        student1.setClazz(21);

        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            GregorianCalendar gc = new GregorianCalendar();
            int year = gc.get(Calendar.YEAR);
            int month = gc.get(Calendar.MONTH) + 1;
            int day = gc.get(Calendar.DAY_OF_MONTH);
            XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendarDate(year, month, day, 0);
            bm.setFrom(xgc);
            bm1.setFrom(xgc);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        Address add = new Address();
        add.setArea("sfsdf");
        add.setCity("Hyd");
        add.setLandmark("sdfsdf");
        add.setState("AP");
        add.setStreet("sdsdf");
        add.setCountry("India");
        add.setZipcode("123");

        BoardMembers bms = new BoardMembers();

        bms.getBoardMember().add(bm);
        bms.getBoardMember().add(bm1);


        s.setSchoolName("agatsya");
        s.setBoardMembers(bms);
        s.setAddress(add);


        Members members = new Members();
        members.getMember().add(student);
        members.getMember().add(student1);


        s.setMembers(members);
        //   s.setBoardMembers(bms);


        try {
            JAXBContext ctxt = JAXBContext.newInstance("school");
            Marshaller m = ctxt.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(s, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
