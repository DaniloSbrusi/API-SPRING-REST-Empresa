package br.edu.unoesc.empresa.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unoesc.empresa.model.Funcionario;
import br.edu.unoesc.empresa.service.FuncionarioService;

@RestController
@RequestMapping("/api/empresa")
public class FuncionarioController {

	@Autowired
	private FuncionarioService servico;
	
	@GetMapping("/funcionario")
	public Iterable<Funcionario> listar() {
		return servico.listar();
	}
	
//	@GetMapping("/funcionario/listar/pagina")
//	public ResponseEntity<Page<FuncionarioDTO>> listarPaginado(Pageable	pagina){
//		return ResponseEntity.ok(servico.listarPaginado(pagina));
//	}
	
	@GetMapping("/funcionario/id/{id}")
	public ResponseEntity<Funcionario> porId(@PathVariable Long id){
		return ResponseEntity.ok(servico.buscarPorId(id));
	}
	
	@GetMapping(value = "/funcionario/id/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public Funcionario porIdXML(@PathVariable Long id) {
		return servico.buscarPorId(id);
	}
	
	@GetMapping("/funcionario/nome")
	public List<Funcionario> porNome(@RequestParam("nome") String nome){
		return servico.buscarPorNome(nome);
	}
	
	@GetMapping("/funcionario/faixa-salarial")
	public List<Funcionario> porFaixaSalarial(@RequestParam(value = "min", defaultValue = "0") BigDecimal min, @RequestParam(value = "max", defaultValue = "0") BigDecimal max) {
		return servico.buscarPorFaixaSalarial(min, max);
	}
	
	@PostMapping("/funcionario")
	public ResponseEntity<Void> incluir(@RequestBody Funcionario funcionario){
		funcionario = servico.incluir(funcionario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        									 .path("/{id}")
        									 .buildAndExpand(funcionario.getId())
        									 .toUri();        
        return ResponseEntity.created(uri).build();
	}
	
    @PutMapping("funcionario/{id}")
	public ResponseEntity<Funcionario> atualizar(@PathVariable("id") Long id, @RequestBody Funcionario funcionario) {
		if (servico.porId(id).isEmpty())
    		return ResponseEntity.notFound().build();
        return ResponseEntity.ok(servico.alterar(id, funcionario));
	}
	
    @DeleteMapping("funcionario/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		try {
    		servico.excluir(id);   		
    	} catch (ObjectNotFoundException e) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.noContent().build();
	}
	
}
