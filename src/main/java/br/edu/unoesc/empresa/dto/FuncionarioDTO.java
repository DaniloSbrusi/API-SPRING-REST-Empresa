package br.edu.unoesc.empresa.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import br.edu.unoesc.empresa.model.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class FuncionarioDTO implements Serializable {
	String nome;
	Integer num_dep;
	BigDecimal salario;
	Date nascimento;
	
	public FuncionarioDTO(Funcionario funcionario) {
		this.nome = funcionario.getNome();
		this.nascimento = funcionario.getNascimento();
		this.salario = funcionario.getSalario();
		this.num_dep = funcionario.getNum_dep();
	}
}