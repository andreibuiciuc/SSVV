package ssvv.example;

import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private final StudentValidator studentValidator;
    private final TemaValidator temaValidator;
    private final NotaValidator notaValidator;
    private final StudentXMLRepository studentRepository;
    private final TemaXMLRepository homeworkRepository;
    private final NotaXMLRepository gradesRepository;
    private Service service;

    public IntegrationTest() {
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        studentRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        homeworkRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        gradesRepository = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentRepository, homeworkRepository, gradesRepository);
    }

    @Test
    public void addStudent() {
        int result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);
    }

    @Test
    public void addAssignmentIntegration() {
        int result;
        result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);

        result = service.saveTema("Assignment1", "Assignment1", 5, 1);
        assertEquals(0, result);
    }

    @Test
    public void addGradeIntegration() {
        int result;
        result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);

        service.saveTema("Assignment1", "Assignment1", 5, 1);
        assertEquals(0, result);

        service.saveNota("Student1", "Assignment1", 10, 2, "very nice");
        assertEquals(0, result);
    }
}
