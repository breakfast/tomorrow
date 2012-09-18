package com.breakfast.tomorrow.core;



import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.breakfast.tomorrow.core.academic.Aluno;
import com.breakfast.tomorrow.core.database.DataBase;

/**
 * The Main class for Tomorrow App Kernel. Just It.
 * @author Kleber Ilario
 *
 */
public class App 
{
    /**
     * Logger of the Application
     */
	static Logger LOG = Logger.getLogger(App.class);
	
	/**
	 * the method main of Tomorrow Application Kernel.
	 * @param args
	 */
	public static void main( String[] args ){
		initLogger();	
		initDataBase();
		testApp();
		stopDatabase();
    }
    
	/**
	 * Init Database App
	 */
    public static void initDataBase(){
    	DataBase.startUp();
    }
    
    public static void stopDatabase(){
    	DataBase.shutDown();
    }
    
    /**
     * Init Logger App
     */
    public static void initLogger(){
    	BasicConfigurator.configure();
    	LOG.setLevel(Level.INFO);
    }
    
    public static void testApp(){
    	LOG.info("Init App");
    	
    	/*
    	for(int x = 0; x <= 30; x++){
    		Aluno student = new Aluno();
    		student.setNome("Student " + x);
    		student.setObservation("Observation");
    		Aluno.persist(student);
    	}
    	*/
    	
    	/*
    	for(Student std : Student.getStudentByName("Student 4")){
    		LOG.info(std.getName());
    	}
    	
    	LOG.info("GET a STudent by ID");
    	Student std = Student.getStudentById(new Long("4"));
    	
    	
    	LOG.info("Name : " + std.getName());
    	LOG.info("ID : " + std.getId());
    	LOG.info("OBS : " + std.getObservation() );
    	
    	
    	LOG.info("Change Name a STudent");
    	std.setName("Student 4");
    	Student.persist(std);
    	*/
    	
    	
    	Aluno stdId = Aluno.getAlunosPorId(Long.valueOf("1"));
    	//stdId.setNome("KLEBER ILARIO");
    	//stdId.setObservation("Obs");
    	System.out.println(stdId.toString());
    	//Student.delete(stdId);
    	//Aluno.persist(stdId);
    	
    	stdId.setNome(null);
    	Aluno.persist(stdId);
    	
    	/*
    	Iterator<Aluno> it = Aluno.getAlunos();
    	while(it.hasNext()){
    		Aluno a = it.next();
    		System.out.println(a);
    	}
    	*/
    	
    	
    	LOG.info("Close");
    }
    
    
    
}
