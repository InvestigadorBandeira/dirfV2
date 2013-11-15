package br.com.dirf.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.dirf.dao.DirfDAO;
import br.com.dirf.exception.DirfException;
import br.com.dirf.model.Beneficiario;
import br.com.dirf.view.BeneficiarioView;

public class BeneficiarioController {

    private final BeneficiarioView view;
    private int linha;
    private final List<Beneficiario> beneficiarios;
    private final DirfDAO dao;

    public BeneficiarioController(DirfDAO dao) {
	this.dao = dao;
	beneficiarios = new ArrayList<Beneficiario>();
	view = new BeneficiarioView(this);
	view.setVisible(true);
    }

    public void processaArquivo(File arquivo) throws Exception {
	int opcao = JOptionPane.showConfirmDialog(view,
		"Processar Benefíciários irá excluir todos os dados anteriores."
			+ "\nDeseja continuar?", "Continuar?",
		JOptionPane.YES_NO_OPTION);

	if (opcao == JOptionPane.YES_OPTION) {
	    processar(arquivo);
	} else
	    informacao("Processamento cancelado.");
    }

    private void processar(File arquivo) throws Exception {
	linha = 0;
	BufferedReader arq;

	arq = new BufferedReader(new FileReader(arquivo));
	while (arq.ready()) {
	    linha++;
	    processaLinha(arq.readLine());
	}
	arq.close();

	salvar();
    }

    private void processaLinha(String linha) throws Exception {
	String[] dados = linha.split(";");

	if (dados.length != 2) {
	    beneficiarios.clear();
	    throw new DirfException("Erro processando a linha: " + this.linha
		    + "\n\n" + linha + "\n\n");
	}

	Beneficiario beneficiario = new Beneficiario();
	beneficiario.setCnpj(dados[0].trim());
	beneficiario.setNome(dados[1]);

	beneficiarios.add(beneficiario);
    }

    private void salvar() {
	// limpa todos os dados
	dao.getDados().getLancamentos().clear();
	dao.getDados().getBeneficiarios().clear();

	// salva beneficiários
	dao.getDados().setBeneficiarios(beneficiarios);

	dao.salvar();

	exibirResultado();

	beneficiarios.clear();
    }

    private void exibirResultado() {
	StringBuilder builder = new StringBuilder(
		"Resultado do processamento:\n");

	for (Beneficiario b : beneficiarios)
	    builder.append(b.getCnpj() + " - " + b.getNome() + "\n");

	builder.append("\n" + beneficiarios.size()
		+ " beneficiários cadastrados.");

	informacao(builder.toString());
    }

    private void informacao(String informacao) {
	view.setInformacoes(informacao);
    }
}
