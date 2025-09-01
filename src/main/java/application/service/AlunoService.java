package application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import application.repository.AlunoRepository;
import application.model.Aluno;
import application.record.AlunoDTO;
import application.record.AlunoInsertDTO;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepo;

    public Iterable<AlunoDTO> getAll() {
        return alunoRepo.findAll().stream().map(AlunoDTO::new).toList();
    }

    public AlunoDTO insert(AlunoInsertDTO dados) {
        return new AlunoDTO(alunoRepo.save(new Aluno(dados)));
    }

    public AlunoDTO getOne(long id) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if (resultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        
        return new AlunoDTO(resultado.get());
    }

    public AlunoDTO update(long id, AlunoInsertDTO dadosAlunos) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if (resultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }

        resultado.get().setNome(dadosAlunos.nome());
        resultado.get().setIdade(dadosAlunos.idade());

        return new AlunoDTO(alunoRepo.save(resultado.get()));
    }

    public void delete(long id) {
        if (!alunoRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        alunoRepo.deleteById(id);
    }
}