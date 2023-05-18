package br.com.ale.restassuredDemo.Hooks;

import br.com.ale.restassuredDemo.DAO.InsertDAO;
import io.cucumber.java.After;
import io.cucumber.java.*;
import io.cucumber.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class Hook {

	@BeforeClass
	public static void setUp(){

		InsertDAO insertDAO = new InsertDAO();
		insertDAO.popularBancoDeDadoTesteAPI();

		//inserir um update para adicionar real_name no administrador do mantisBT
	}

	@Before
	public void setUpTest() throws Exception {

	}

	@After
	public void closeTest(){
	}

}
