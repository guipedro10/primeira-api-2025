package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Aluno;
import java.util.Optional;
import application.repository.AlunoRepository;


@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepo;

    @GetMapping
    public Iterable<Aluno> getAll() {
        return alunoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Aluno getOne(@PathVariable long id) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if (resultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        
        return resultado.get();
    }

    @PostMapping
    public Aluno insert(@RequestBody Aluno novoAluno) {
        return alunoRepo.save(novoAluno);
    }

    @PutMapping("/{id}")
    public Aluno update(@PathVariable long id, @RequestBody Aluno novosDados) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if (resultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }

        resultado.get().setNome(novosDados.getNome());
        resultado.get().setIdade(novosDados.getIdade());

        return alunoRepo.save(resultado.get());
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        if (!alunoRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        
        alunoRepo.deleteById(id);
    }
}