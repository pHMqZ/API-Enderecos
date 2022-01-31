package com.pms.dio.gof.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.dio.gof.model.Cliente;
import com.pms.dio.gof.model.Endereco;
import com.pms.dio.gof.repository.ClienteRepository;
import com.pms.dio.gof.repository.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;

	public Iterable<Cliente> buscarTodos() {

		return clienteRepository.findAll();
	}

	public Cliente buscarPorId(Long id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

}
