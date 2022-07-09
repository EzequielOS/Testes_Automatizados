package com.iftm.client.tests.services;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.services.ClientService;
import com.iftm.client.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {
	@InjectMocks
	private ClientService servico;

	@Mock
	private ClientRepository rep;

    @Test
    public void testarApagarRetornaNadaQuandoIDExiste(){
        //entrada
        Long idExistente = 2l;
		//configurar Mock
        Mockito.doNothing().when(rep).deleteById(idExistente);
        ///executar yteste
        Assertions.assertDoesNotThrow(() -> {servico.delete(idExistente);});
        // verfificar a execuçao da classe mock e de seus metodos
        Mockito.verify(rep, Mockito.times(1)).deleteById(idExistente);
	}

	@Test
	public void testarFindPageAll(){
        PageRequest pageRequestTest = PageRequest.of(0,1);
        List<Client> listaClientes = new ArrayList<Client>();
        Client test1 = new Client(9L, "Joao Aparecido", "444489887954", 1200.0, Instant.parse("1998-05-10T08:00:00Z"),4);
        listaClientes.add(test1);

        Page<Client> pagTest = new PageImpl<>(listaClientes, pageRequestTest, listaClientes.size());
        Mockito.when(rep.findAll(pageRequestTest)).thenReturn(pagTest);
        Page<ClientDTO> resultadoTest = servico.findAllPaged(pageRequestTest);

        Assertions.assertFalse(resultadoTest.isEmpty());
        Assertions.assertEquals(listaClientes.size(), resultadoTest.getTotalElements());

        for(int i=0; i < listaClientes.size(); i++){
            Assertions.assertEquals(listaClientes.get(i), resultadoTest.toList().get(i).toEntity());
        }
        Mockito.verify(rep, Mockito.times(1)).findAll(pageRequestTest);
	}

    @Test
    public void testarFindByIncome() {
        PageRequest pageRequestTest = PageRequest.of(0,1);
        Double valorEntrada = 1200.0;
        List<Client> listaClientes = new ArrayList<Client>();
        Client test1 = new Client(9L, "Joao Aparecido", "444489887954", 1200.0, Instant.parse("1998-05-10T08:00:00Z"),4);
        listaClientes.add(test1);
        Page<Client> pagTest = new PageImpl<>(listaClientes, pageRequestTest, listaClientes.size());

        Mockito.when(rep.findByIncome(valorEntrada, pageRequestTest)).thenReturn(pagTest);
        Page<ClientDTO> resultadoTest = servico.findByIncome(pageRequestTest, valorEntrada);

        Assertions.assertFalse(resultadoTest.isEmpty());
        Assertions.assertEquals(listaClientes.size(), resultadoTest.getTotalElements());

        for(int i=0; i< listaClientes.size(); i++){
            Assertions.assertEquals(listaClientes.get(i), resultadoTest.toList().get(i).toEntity());
        }
        Mockito.verify(rep, Mockito.times(1)).findByIncome(valorEntrada, pageRequestTest);

    }

    @Test
    public void testFindByIdExiste(){
        PageRequest pageRequestTest = PageRequest.of(0,1);
        Long idExiste = 9L;

        Optional<Client> newClientTest = Optional.of(new Client());

        Mockito.when(rep.findById(idExiste)).thenReturn(newClientTest);
        ClientDTO resultadoTest = servico.findById(idExiste);

        Assertions.assertNotNull(resultadoTest);
        Assertions.assertEquals(newClientTest.get(), resultadoTest.toEntity());

        Mockito.verify(rep, Mockito.times(1)).findById(idExiste);

    }

    @Test
    public void testFindByIdNaoExiste() {
        Long idNaoExiste = 299L;

        Mockito.doThrow(ResourceNotFoundException.class).when(rep).findById(idNaoExiste);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> servico.findById(idNaoExiste));
        Mockito.verify(rep, Mockito.times(1)).findById(idNaoExiste);
    }

    @Test
    public void testUpdateRetornaClientDtoQuandoExistirId() {
        Long id = 1l;
        Optional<Client> client = Optional.of(new Client());
        Client clientTest = new Client(9L, "Joao Aparecido", "444489887954", 1200.0, Instant.parse("1998-05-10T08:00:00Z"),4);c
        Mockito.when(rep.getOne(id)).thenReturn(clientTest);

        Client clientTest2 = new Client(9L, "Juarez Alves", "444489887954", 1200.0, Instant.parse("1998-05-10T08:00:00Z"),4);
        Mockito.when(rep.save(clientTest2)).thenReturn(clientTest2);

        ClientDTO test = servico.update(id, new ClientDTO(clientTest2));


        Assertions.assertEquals(clientTest2, test.toEntity());
        Assertions.assertNotNull(test);
        Mockito.verify(rep, Mockito.times(1)).getOne(id);
        Mockito.verify(rep, Mockito.times(1)).save(clientTest2);
    }

    @Test
    public void testUpdateRetornaExceptionNaoExistirId() {
        Long idNaoExiste = 1000l;
        Mockito.doThrow(ResourceNotFoundException.class).when(rep).findById(idNaoExiste);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> servico.findById(idNaoExiste));
        Mockito.verify(rep, Mockito.times(1)).findById(idNaoExiste);
    }
}
