package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogPessoal.model.UsuarioModel;
import org.generation.blogPessoal.service.UsuarioService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	public void deveCadastrarUmUsuario() {
		HttpEntity<UsuarioModel> requisicao = new HttpEntity<UsuarioModel>(new UsuarioModel(0L, "Zé Mario",
				"zemario@ovo.com", "ovofrito123", "https://i.imgSur.com/JR7kUFU.jpg\r\n"));

		ResponseEntity<UsuarioModel> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				requisicao, UsuarioModel.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());

	}

	@Test
	@Order(2)
	private void deveAtualizarUmUsuario() {
		Optional<UsuarioModel> usuarioCreate = usuarioService.cadastrarUsuario(new UsuarioModel(0L, "TinkWink",
				"tinkwink@telletubies.com.br", "bolsavermelha",
				"https://pbs.twimg.com/profile_images/3457438261/e839142b1e74a6c69ce06189edf5a4e7_400x400.jpeg\r\n"));

		UsuarioModel usuarioUpdate = new UsuarioModel(usuarioCreate.get().getId(), "TinkWink da Silva",
				"tinkwink@telletubies.com.br", "bolsavermelha",
				"https://pbs.twimg.com/profile_images/3457438261/e839142b1e74a6c69ce06189edf5a4e7_400x400.jpeg\r\n");

		HttpEntity<UsuarioModel> requisicaoAtualizacao = new HttpEntity<UsuarioModel>(usuarioUpdate);

		ResponseEntity<UsuarioModel> respostaAtualizacao = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicaoAtualizacao, UsuarioModel.class);

		assertEquals(HttpStatus.OK, respostaAtualizacao.getStatusCode());

	}

}
