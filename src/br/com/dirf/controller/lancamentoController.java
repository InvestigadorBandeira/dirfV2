package br.com.dirf.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.dirf.dao.DirfDAO;
import br.com.dirf.exception.DirfException;
import br.com.dirf.model.Beneficiario;
import br.com.dirf.model.Lancamento;
import br.com.dirf.model.Pagamento;

public class lancamentoController {
    private final DirfDAO dao;
    private final List<Lancamento> lancamentos;
    private int linha;

    public lancamentoController(DirfDAO dao) {
	this.dao = dao;
	lancamentos = new ArrayList<Lancamento>();
	criaLancamentos();
    }

    private void criaLancamentos() {
	List<Beneficiario> beneficiarios = dao.getDados().getBeneficiarios();

	if (beneficiarios.isEmpty()) {
	    informacao("É preciso criar beneficiários primeiro.");
	    return;
	}

	for (Beneficiario b : beneficiarios) {
	    Lancamento lancamento = new Lancamento();
	    List<Pagamento> pagamentos = new ArrayList<Pagamento>();
	    lancamento.setBeneficiario(b);

	    // gera 12 pagamentos
	    for (int i = 0; i < 12; i++) {
		Pagamento pagamento = new Pagamento();
		pagamento.setMes(i + 1);
		pagamentos.add(pagamento);
	    }

	    lancamento.setPagamentos(pagamentos);

	    lancamentos.add(lancamento);
	}

	try {
	    carregarArquivo();
	} catch (Exception e) {
	    informacao(e.getMessage());
	    lancamentos.clear();
	}

	if (!lancamentos.isEmpty()) {
	    // limpa lançamentos
	    dao.getDados().getLancamentos().clear();

	    // adiciona lançamentos
	    dao.getDados().setLancamentos(lancamentos);

	    dao.salvar();
	}
    }

    private void carregarArquivo() throws Exception {
	JFileChooser chooser = new JFileChooser(".");
	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	chooser.setDialogTitle("Abrir arquivo de Lançamentos (CSV)");

	chooser.addChoosableFileFilter(new FileNameExtensionFilter(
		"Arquivo CSV", "csv"));

	int meses = 12;

	do {
	    int opcao = chooser.showOpenDialog(null);

	    if (opcao == JFileChooser.APPROVE_OPTION) {
		processaArquivo(chooser.getSelectedFile());
		informacao(chooser.getSelectedFile().getAbsolutePath());
		--meses;
	    }
	} while (meses != 0);

    }

    private void processaArquivo(File arquivo) throws Exception {
	linha = 0;
	BufferedReader arq;

	arq = new BufferedReader(new FileReader(arquivo));
	while (arq.ready()) {
	    linha++;
	    processaLinha(arq.readLine());
	}
	arq.close();

	// salvar();
    }

    private void processaLinha(String linha) throws Exception {
	String[] dados = linha.split(";");

	if (dados.length != 7) {
	    lancamentos.clear();
	    throw new DirfException("Erro processando a linha: " + this.linha
		    + "\n\n" + linha + "\n\n");
	}

	// 0 nome | 1 bruto | 2 ir | 3 pis | 4 cofins | 5 csll | 6 mes

	String valorBruto = dados[1].replace(",", "").trim();
	if (valorBruto.isEmpty() || "0".equals(valorBruto)
		|| "00".equals(valorBruto) || "000".equals(valorBruto))
	    return;

	String nome = dados[0].trim();
	int mes = Integer.parseInt(dados[6]);
	// System.out.println(mes); // excluir

	for (Lancamento lancamento : lancamentos)
	    if (nome.equals(lancamento.getBeneficiario().getNome())) {
		for (Pagamento p : lancamento.getPagamentos())
		    if (mes == p.getMes()) {
			p.setBruto(dados[1].replace(",", "").trim());
			p.setIrrf(dados[2].replace(",", "").trim());
			p.setPis(dados[3].replace(",", "").trim());
			p.setCofins(dados[4].replace(",", "").trim());
			p.setCsll(dados[5].replace(",", "").trim());
			// System.out.println(p); // excluir
			break;
		    }
		// System.out.println(lancamento); // excluir
		break;
	    }
    }

    private void informacao(String informacao) {
	JOptionPane.showMessageDialog(null, informacao, "Criar Lançamentos",
		JOptionPane.INFORMATION_MESSAGE);
    }

}
