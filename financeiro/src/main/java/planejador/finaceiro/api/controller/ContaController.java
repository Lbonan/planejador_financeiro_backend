package planejador.finaceiro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import planejador.finaceiro.service.ContaService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contas")
public class ContaController {
    private final ContaService contaService;
}
