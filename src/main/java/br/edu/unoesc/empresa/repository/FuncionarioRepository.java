package br.edu.unoesc.empresa.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.empresa.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	public List<Funcionario> findByNomeContainingIgnoreCase(String nome);
	
	@Query("Select f from Funcionario f where f.salario >= :min and f.salario <= :max order by salario")
	public List<Funcionario> buscarPorFaixaSalario(BigDecimal min, BigDecimal max);
	
	@Query("Select f from Funcionario f where f.num_dep >= 1")
	public List<Funcionario> possuiDependentes();
}
