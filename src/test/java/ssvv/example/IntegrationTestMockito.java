package ssvv.example;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.Assert.assertEquals;

public class IntegrationTestMockito {
    private StudentValidator studentValidator;
    private TemaValidator temaValidator;
    private NotaValidator notaValidator;

    @Mock
    private StudentXMLRepository studentRepository;

    @Mock
    private TemaXMLRepository homeworkRepository;

    @Mock
    private NotaXMLRepository gradesRepository;
    private Service service;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);

        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        service = new Service(studentRepository, homeworkRepository, gradesRepository);
    }

    @Test
    public void addStudentMockito() {
        Student student = new Student("Student1", "Student1", 931);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        int result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);
    }

    @Test
    public void addAssignmentMockito() {
        Student student = new Student("Student1", "Student1", 931);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Tema homework = new Tema("Assignment1", "Assignment1", 5, 1);
        Mockito.when(homeworkRepository.save(homework)).thenReturn(homework);

        int result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);

        result = service.saveTema("Assignment1", "Assignment1", 5, 1);
        assertEquals(0, result);
    }

    @Test
    public void addGradeMockito() {
        Student student = new Student("Student1", "Student1", 931);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Tema homework = new Tema("Assignment1", "Assignment1", 5, 1);
        Mockito.when(homeworkRepository.save(homework)).thenReturn(homework);

        Nota grade = new Nota(new Pair("Student1", "Assignment1"), 10, 2, "very nice");
        Mockito.when(gradesRepository.save(grade)).thenReturn(grade);

        int result = service.saveStudent("Student1", "Student1", 931);
        assertEquals(0, result);

        service.saveTema("Assignment1", "Assignment1", 5, 1);
        assertEquals(0, result);

        service.saveNota("Student1", "Assignment1", 10, 2, "very nice");
        assertEquals(0, result);
    }
}
