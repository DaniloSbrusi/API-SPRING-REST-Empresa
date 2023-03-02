package br.edu.unoesc.empresa.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unoesc.empresa.dto.FuncionarioDTO;
import br.edu.unoesc.empresa.model.Funcionario;
import br.edu.unoesc.empresa.repository.FuncionarioRepository;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository repositorio;
	
	@Override
	public void popularTabelaInicial() {
		repositorio.saveAll(List.of(
					new Funcionario(1L, "Ana", Integer.valueOf(2), new BigDecimal(3000), Date.valueOf("2000-01-01")),
					new Funcionario(2L, "Bernardo", Integer.valueOf(0), new BigDecimal(7000), Date.valueOf("1980-11-05")),
					new Funcionario(3L, "Carlos", Integer.valueOf(1), new BigDecimal(1500), Date.valueOf("2002-07-21")),
					new Funcionario(4L, "Daniela", Integer.valueOf(0), new BigDecimal(5400), Date.valueOf("2004-03-18"))
			)
		);
	}

	@Override
	public Funcionario incluir(Funcionario funcionario) {
		funcionario.setId(null);
		return repositorio.save(funcionario);
	}

	@Override
	public Funcionario alterar(Long id, Funcionario funcionario) {
		var f = repositorio.findById(id).orElseThrow(() -> new ObjectNotFoundException("Funcionário não encontrado!", null));
		f.setNome(funcionario.getNome());
		f.setNascimento(funcionario.getNascimento());
		f.setNum_dep(funcionario.getNum_dep());
		f.setSalario(funcionario.getSalario());
		return repositorio.save(f);
	}

	@Override
	public void excluir(Long id) {
		if (repositorio.existsById(id)) {
			repositorio.deleteById(id);
		} else {
			throw new ObjectNotFoundException("Funcionário não encontrado", null);
		}
	}

	@Override
	public List<Funcionario> listar() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Iterable<Funcionario> itens = repositorio.findAll();
		itens.forEach(funcionarios::add);
		return funcionarios;
	}

	@Override
	public Page<FuncionarioDTO> listarPaginado(Pageable pagina) {
		Page<Funcionario> lista = repositorio.findAll(pagina);
		Page<FuncionarioDTO> listaDTO = lista.map(livro -> new FuncionarioDTO(livro));
		return listaDTO;
	}

	@Override
	public Funcionario buscar(Long id) {
		Optional<Funcionario> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Funcionário não encontrado!", null));			      
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		return repositorio.findById(id).orElse(new Funcionario());					      
	}

	@Override
	public Optional<Funcionario> porId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public List<Funcionario> buscarPorNome(String nome) {
		return repositorio.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public List<Funcionario> buscarPorFaixaSalarial(BigDecimal salarioMinimo, BigDecimal salarioMaximo) {
		return repositorio.buscarPorFaixaSalario(salarioMinimo, salarioMaximo);
	}

	@Override
	public List<Funcionario> buscarPossuiDependentes(Integer numDependentes) {
		return repositorio.possuiDependentes();
	}

}
