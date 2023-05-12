package br.com.ale.restassuredDemo.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.*;

public class Hook {

	@Before
	public void setUp() throws Exception {
		//GerarMassaDeDadosDAO gerarMassaDeDadosDAO = new GerarMassaDeDadosDAO();
		//gerarMassaDeDadosDAO.create();
		//inserir um update para adicionar real_name no administrador do mantisBT
	}

	@After
	public void closeTest(){
	}

}
