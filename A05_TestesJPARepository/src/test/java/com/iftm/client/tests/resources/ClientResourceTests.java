package com.iftm.client.tests.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourceTests {
	private int qtdClientes = 12;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	// Para o teste real da aplicação iremos comentar ou retirar.
	//@MockBean
	//private ClientService service;

	@Test
	public void testarListarTodosClientesRetornaOKeClientes() throws Exception {
		//configuração do meu mock
		/*
		List<ClientDTO> listaClientes = new ArrayList<ClientDTO>();
		listaClientes.add(new ClientDTO(
				new Client(7l, "Jose Saramago", "10239254871", 5000.0, Instant.parse("1996-12-23T07:00:00Z"), 0)));
		listaClientes.add(new ClientDTO(new Client(4l, "Carolina Maria de Jesus", "10419244771", 7500.0,
				Instant.parse("1996-12-23T07:00:00Z"), 0)));
		listaClientes.add(new ClientDTO(
				new Client(8l, "Toni Morrison", "10219344681", 10000.0, Instant.parse("1940-02-23T07:00:00Z"), 0)));
		Page<ClientDTO> page = new PageImpl<ClientDTO>(listaClientes);
		when(service.findAllPaged(any())).thenReturn(page);
		qtdClientes = 3;
		*/
		
//		//iremos realizar o teste
//		mockMvc.perform(get("/clients")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.content").exists())
//				.andExpect(jsonPath("$.content").isArray())
//				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 7L).exists())
//				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 4L).exists())
//				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 8L).exists())
//				.andExpect(jsonPath("$.totalElements").value(qtdClientes));
	}
	
	@Test
	public void testarBuscaPorIDExistenteRetornaJsonCorreto() throws Exception {
		long idExistente = 3L;
		ResultActions resultado = mockMvc.perform(get("/clients/{id}",idExistente)
				.accept(MediaType.APPLICATION_JSON));
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").exists());
		resultado.andExpect(jsonPath("$.id").value(idExistente));
		resultado.andExpect(jsonPath("$.name").exists());		
		resultado.andExpect(jsonPath("$.name").value("Clarice Lispector"));
	}
	
	@Test
	public void testarBuscaPorIdNaoExistenteRetornaNotFound() throws Exception {
		long idNaoExistente = 300L;
		ResultActions resultado = mockMvc.perform(get("/clients/{id}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));
		resultado.andExpect(status().isNotFound());
		resultado.andExpect(jsonPath("$.error").exists());
		resultado.andExpect(jsonPath("$.error").value("Resource not found"));
		resultado.andExpect(jsonPath("$.message").exists());
		resultado.andExpect(jsonPath("$.message").value("Entity not found"));
	}

	/////////////////// TESTES NA CAMADA WEB
	@Test
	public void insertReturnCriadoComSucesso() throws Exception {

		ClientDTO clientDTO = new ClientDTO(new Client(7l, "Joseilson Marques", "996877451", 5000.0,
				Instant.parse("2019-10-01T08:25:24.00Z"), 1));

	String json = objectMapper.writeValueAsString(clientDTO);

		ResultActions result = mockMvc.perform(post("/clients")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.cpf").value("996877451"));
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.name").value("Joseilson Marques"));
	}

	@Test
	public void deleteRetornaNoContentQuandoIdExistir() throws Exception {
		Long idExistente = 6L;
		ResultActions resultado = mockMvc.perform(delete("/clients/{id}", idExistente)
				.accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().is(204));
	}

	@Test
	public void deleteRetornaNotFoundQuandoIdNaoExistir() throws Exception {
		Long idNaoExistente = 300L;
		ResultActions resultado = mockMvc.perform(delete("/clients/{id}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isNotFound());
		resultado.andExpect(jsonPath("$.error").exists());
		resultado.andExpect(jsonPath("$.error").value("Resource not found"));
		resultado.andExpect(jsonPath("$.message").value("Id not found 300"));
		resultado.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	public void findByIncomeRetornaOk() throws Exception {

		double income = 1500.0;
		qtdClientes = 3;

		ResultActions resultado = mockMvc.perform(get("/clients/income/")
				.param("income", String.valueOf(income))
				.accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").exists())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 1L).exists())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 9L).exists())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 10L).exists())
				.andExpect(jsonPath("$.content[?(@.income =='%s')]", income).exists())
				.andExpect(jsonPath("$.content[?(@.income =='%s')]", income).exists())
				.andExpect(jsonPath("$.content[?(@.income =='%s')]", income).exists())
				.andExpect(jsonPath("$.numberOfElements").value(qtdClientes));
	}

	@Test
	public void updateRetornaSucesso() throws Exception {

		ClientDTO clientDTO = new ClientDTO(new Client(7l, "Joseilson Marques", "996877451", 6500.0,
				Instant.parse("1992-10-14T08:25:24.00Z"), 1));

		String json = objectMapper.writeValueAsString(clientDTO);

		ResultActions result = mockMvc.perform(put("/clients/{id}", 7l)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.cpf").value("996877451"));
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.name").value("Joseilson Marques"));
		result.andExpect(jsonPath("$.income").exists());
		result.andExpect(jsonPath("$.income").value(6500.0));
	}

}