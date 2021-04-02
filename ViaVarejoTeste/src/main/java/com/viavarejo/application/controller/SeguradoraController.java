package com.viavarejo.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viavarejo.application.converter.ApoliceConverter;
import com.viavarejo.application.converter.ClienteConverter;
import com.viavarejo.application.dto.ApoliceDTO;
import com.viavarejo.application.dto.ClienteDTO;
import com.viavarejo.application.exception.CustomRestExceptionHandler;
import com.viavarejo.application.model.Apolice;
import com.viavarejo.application.model.Cliente;
import com.viavarejo.application.service.ApoliceService;
import com.viavarejo.application.service.ClienteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class SeguradoraController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SeguradoraController.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ApoliceService apoliceService;

	@GetMapping("/clientes")
	@ApiOperation(value = "Consulta todos os clientes da seguradora", notes = "Este serviço é responsável por consultar todos os clientes da seguradora")
	public ResponseEntity<List<ClienteDTO>> getAllClientes() {
		try {

			List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();

			clientes = ClienteConverter.getInstance().converterList(clienteService.findAll());

			if (clientes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return ResponseEntity.ok(clientes);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/clientes/{id}")
	@ApiOperation(value = "Consulta um cliente específico através do ID", notes = "Este serviço é responsável por consultar um cliente através do ID")
	public ResponseEntity<Object> getClienteById(@PathVariable("id") String id) throws Exception{
		Optional<Cliente> clienteData = clienteService.findById(id);

		try {
			if (clienteData.isPresent()) {
				ClienteDTO clienteDTO = ClienteConverter.getInstance().converter(clienteData.get());
				return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
			} else {
				throw new Exception("Cliente não encontrado");
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/clientes")
	@ApiOperation(value = "Cria um cliente da seguradora", notes = "Este serviço é responsável por criar um cliente da seguradora")
	public ResponseEntity<Object> createCliente(@RequestBody Cliente cliente) throws Exception{
		try {
			
			if (validaCliente(cliente)) {
				Cliente clienteSalvo = clienteService.salvar(cliente);
				return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
			} else {
				throw new Exception("Todos os campos do cliente devem ser preenchidos: [nome, cpf, cidade, uf]");
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@PutMapping("/clientes/{id}")
	@ApiOperation(value = "Atualiza um cliente da seguradora", notes = "Este serviço é responsável por atualizar um cliente da seguradora")
	public ResponseEntity<Object> updateCliente(@PathVariable("id") String id, @RequestBody Cliente cliente) throws Exception{
		Optional<Cliente> clienteData = clienteService.findById(id);
		try {
			if (clienteData.isPresent()) {
				Cliente clienteNovo = clienteData.get();
				clienteNovo.setNome(cliente.getNome());
				clienteNovo.setCpf(cliente.getCpf());
				clienteNovo.setCidade(cliente.getCidade());
				clienteNovo.setUf(cliente.getUf());
				
				if (validaCliente(clienteNovo)) {
					return new ResponseEntity<>(clienteService.salvar(clienteNovo), HttpStatus.OK);
				} else {
					throw new Exception("Todos os campos do cliente devem ser preenchidos: [nome, cpf, cidade, uf]");
				}
				
			} else {
				Exception e = new Exception("ID de cliente não encontrado");
				return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

	@DeleteMapping("/clientes/{id}")
	@ApiOperation(value = "Deleta um cliente da seguradora", notes = "Este serviço é responsável por deletar um cliente da seguradora")
	public ResponseEntity<Object> deleteCliente(@PathVariable("id") String id) throws Exception{
		try {
			
			if (clienteService.findById(id).isPresent()) {
				clienteService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				Exception e = new Exception("ID de cliente não encontrado");
				return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/clientes")
	@ApiOperation(value = "Deleta todos os clientes da seguradora", notes = "Este serviço é responsável por deletar todos os clientes da seguradora")
	public ResponseEntity<HttpStatus> deleteAllClientes() {
		try {
			clienteService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/apolices")
	@ApiOperation(value = "Consulta todas as apólices da seguradora", notes = "Este serviço é responsável por consultar todas as apólices da seguradora")
	public ResponseEntity<List<ApoliceDTO>> getAllApolices() {
		try {
			List<ApoliceDTO> apolicesDTO = new ArrayList<ApoliceDTO>();

			// apolices = ApoliceConverter.getInstance().converterList(
			List<Apolice> apolices = apoliceService.findAll();

			for (Apolice apolice : apolices) {
				Optional<Cliente> cliente = clienteService.findById(apolice.getClienteID());
				ApoliceDTO apoliceDTO = ApoliceConverter.getInstance().converter(apolice);
				apoliceDTO.setCliente(ClienteConverter.getInstance().converter(cliente.get()));
				apolicesDTO.add(apoliceDTO);
			}

			if (apolicesDTO.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(apolicesDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/apolices/{id}")
	@ApiOperation(value = "Consulta uma apólice da seguradora", notes = "Este serviço é responsável por consultar uma apólice da seguradora")
	@ApiResponses(value = { @ApiResponse(code = 406, message = "Obrigatório informar o número da apólice") })
	public ResponseEntity<Object> getApoliceById(@PathVariable("id") String id) throws Exception {
		Optional<Apolice> apoliceData = apoliceService.findById(id);

		try {
			if (apoliceData.isPresent()) {
				Apolice apolice = apoliceData.get();
				Optional<Cliente> cliente = clienteService.findById(apolice.getClienteID());
				ApoliceDTO apoliceDTO = ApoliceConverter.getInstance().converter(apolice);
				apoliceDTO.setCliente(ClienteConverter.getInstance().converter(cliente.get()));

				return new ResponseEntity<>(apoliceDTO, HttpStatus.OK);
			} else {
				throw new Exception("Número da apólice não encontrado");
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/apolices")
	@ApiOperation(value = "Cria uma apólice da seguradora", notes = "Este serviço é responsável por criar uma apólice da seguradora")
	public ResponseEntity<Object> createApolice(@RequestBody Apolice apolice) throws Exception {
		try {
			if (validaApolice(apolice)) {
				Apolice apoliceSalva = apoliceService.salvar(apolice);
				return new ResponseEntity<>(apoliceSalva, HttpStatus.CREATED);
			} else {
				throw new Exception("Todos os campos da apólice devem ser preenchidos: [dataInicioVigencia, dataFimVigencia, valor, placaVeiculo, clienteID]");
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@PutMapping("/apolices/{id}")
	@ApiOperation(value = "Atualiza uma apólice da seguradora", notes = "Este serviço é responsável por atualizar uma apólice da seguradora")
	public ResponseEntity<Object> updateApolice(@PathVariable("id") String id, @RequestBody Apolice apolice) throws Exception{
		LOGGER.info("[INICIO do UPDATE - APOLICE]");
		Optional<Apolice> apoliceData = apoliceService.findById(id);

		try {
			if (apoliceData.isPresent()) {
				Apolice apoliceNova = apoliceData.get();
				apoliceNova.setPlacaVeiculo(apolice.getPlacaVeiculo());
				apoliceNova.setValor(apolice.getValor());
				apoliceNova.setDataInicioVigencia(apolice.getDataInicioVigencia());
				apoliceNova.setDataFimVigencia(apolice.getDataFimVigencia());
				apoliceNova.setClienteID(apolice.getClienteID());
				
				if (validaApolice(apoliceNova)) {
					LOGGER.info("[INICIANDO PERSISTENCIA - APOLICE]");
					return new ResponseEntity<>(apoliceService.salvar(apoliceNova), HttpStatus.OK);
				} else {
					throw new Exception("Todos os campos da apólice devem ser preenchidos: [dataInicioVigencia, dataFimVigencia, valor, placaVeiculo, clienteID]");
				}
			} else {
				Exception e = new Exception("Número de apólice não encontrado");
				return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/apolices/{id}")
	@ApiOperation(value = "Deleta uma apólice da seguradora", notes = "Este serviço é responsável por deletar uma apólice da seguradora")
	public ResponseEntity<Object> deleteApolice(@PathVariable("id") String id) throws Exception{
		try {
			if (apoliceService.findById(id).isPresent()) {
				apoliceService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				Exception e = new Exception("Número de apólice não encontrado");
				return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new CustomRestExceptionHandler().handleAll(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/apolices")
	@ApiOperation(value = "Deleta todas as apólices da seguradora", notes = "Este serviço é responsável por deletar todas as apólices da seguradora")
	public ResponseEntity<HttpStatus> deleteAllApolices() {
		try {
			apoliceService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean validaApolice(Apolice apolice) {
		boolean validou = true;

		if (apolice.getDataFimVigencia() == null || apolice.getDataInicioVigencia() == null
				|| apolice.getClienteID() == null || apolice.getPlacaVeiculo() == null || 
					apolice.getValor() == null) {
			validou = false;
		}

		return validou;
	}
	
	public boolean validaCliente(Cliente cliente) {
		boolean validou = true;
		
		if (cliente.getCidade() == null || cliente.getCpf() == null	|| cliente.getNome() == null || 
				cliente.getUf() == null) {
			validou = false;
		}

		return validou;
	}

}
