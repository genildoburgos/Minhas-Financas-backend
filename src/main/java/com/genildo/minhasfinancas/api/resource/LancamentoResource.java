package com.genildo.minhasfinancas.api.resource;

import com.genildo.minhasfinancas.api.dto.LancamentoDTO;
import com.genildo.minhasfinancas.exception.RegraNegocioException;
import com.genildo.minhasfinancas.model.entity.Lancamento;
import com.genildo.minhasfinancas.model.entity.Usuario;
import com.genildo.minhasfinancas.model.enums.StatusLancamento;
import com.genildo.minhasfinancas.model.enums.TipoLancamento;
import com.genildo.minhasfinancas.service.LancamentoService;
import com.genildo.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {
    private final LancamentoService service;
    private final UsuarioService usuarioService;


//    public LancamentoResource(LancamentoService service, UsuarioService usuarioService) {
//        this.service = service;
//        this.usuarioService = usuarioService;
//    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("hello world");
    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam("usuario") Long idUsuario
    ){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(idUsuario);
        if(usuarioOptional.isPresent()) {
            lancamentoFiltro.setUsuario(usuarioOptional.get());
        } else {
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o ID informado");
        }

        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }


    @PostMapping
    public ResponseEntity salvar (@RequestBody LancamentoDTO dto){
        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("{id}")
    public ResponseEntity deletar (@PathVariable("id") Long id){


        return service.obterPorId(id).map( entidade ->{
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(()->
                new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto  ){
        return service.obterPorId(id).map( entity -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            }catch (RegraNegocioException  e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

            }).orElseGet(()-> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));

    }


    private Lancamento converter(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getIdUsuario());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getIdUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id encontrado."));

        lancamento.setUsuario(usuario);

        if(dto.getTipo() != null){
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if(dto.getStatus() != null){
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }


}
