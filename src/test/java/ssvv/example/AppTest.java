package ssvv.example;

import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    private final StudentValidator studentValidator;
    private final TemaValidator temaValidator;
    private final NotaValidator notaValidator;
    private final StudentXMLRepository studentRepository;
    private final TemaXMLRepository homeworkRepository;
    private final NotaXMLRepository gradesRepository;
    private Service service;

    public AppTest() {
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        studentRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        homeworkRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        gradesRepository = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentRepository, homeworkRepository, gradesRepository);
    }

    @Test
    public void addStudent_01() {
        int result;

        // Invalid
        result = service.saveStudent("", "Name1", 931);
        assertEquals(result, 1);

        result = service.saveStudent(null, "Name1", 931);
        assertEquals(result, 1);

        // Valid
        result = service.saveStudent("not empty", "Name1", 931);
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_02() {
        int result;

        // Invalid
        result = service.saveStudent("Name1", "", 931);
        assertEquals(result, 1);

        result = service.saveStudent("Name2", null, 931);
        assertEquals(result, 1);

        // Valid
        result = service.saveStudent("Name3", "not empty", 931);
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_03() {
        int result;

        // Invalid
        result = service.saveStudent("Id1", "Name1", -100);
        assertEquals(result, 1);

        result = service.saveStudent("Id2", "Name2", 950);
        assertEquals(result, 1);

        result = service.saveStudent("Id3", "Name3", 110);
        assertEquals(result, 1);

        result = service.saveStudent("Id4", "Name4", 938);
        assertEquals(result, 1);

        // Valid
        result = service.saveStudent("Id5", "Name5", 111);
        assertEquals(result, 0);

        result = service.saveStudent("Id6", "Name6", 112);
        assertEquals(result, 0);

        result = service.saveStudent("Id7", "Name7", 936);
        assertEquals(result, 0);

        result = service.saveStudent("Id8", "Name8", 937);
        assertEquals(result, 0);
    }

    @Test
    public void addAssignmentWithEmptyId() {
        int result = service.saveTema("", "HH1", 8, 6);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithNullId() {
        int result = service.saveTema(null, "HH1", 8, 6);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithEmptyDescription() {
        int result = service.saveTema("test1", "", 8, 6);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithNullDescription() {
        int result = service.saveTema("test2", null, 8, 6);
        assertEquals(result,1);
    }

    @Test
    public void addAssignmentWithDeadlineSmallerThanStartline() {
        int result = service.saveTema("test3", "HW2", 4, 6);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithInvalidDeadline() {
        int result = service.saveTema("test4", "HW2", 0, 5);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithInvalidStartline() {
        int result = service.saveTema("test5", "HW2", 1, 15);
        assertEquals(result, 1);
    }

    @Test
    public void addAssignmentWithInvalidStartAndDeadline() {
        int result = service.saveTema("test5",  "HW1", 6, 3);
        assertEquals(result, 0);
    }

}
