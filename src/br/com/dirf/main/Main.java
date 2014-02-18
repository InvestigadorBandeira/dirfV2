package br.com.dirf.main;

import java.util.List;

import br.com.dirf.dao.DirfDAO;
import br.com.dirf.model.Lancamento;
import br.com.dirf.model.Pagamento;

public class Main {
    public static void main(String[] args) {
	DirfDAO dao = new DirfDAO();
	// new BeneficiarioController(dao);
	// new lancamentoController(dao);

	// for (Beneficiario b : dao.getDados().getBeneficiarios())
	// System.out.println(b);

	// for (Lancamento l : dao.getDados().getLancamentos())
	// JOptionPane.showMessageDialog(null, l, "DIRF 2012",
	// JOptionPane.PLAIN_MESSAGE);

	// System.out.println(dao.getDados().getLancamentos().get(24));

	// gera1708(dao.getDados().getLancamentos());
	gera5952(dao.getDados().getLancamentos());
	// geraAnual(dao.getDados().getLancamentos());

    }

    private static void geraAnual(List<Lancamento> lancamentos) {
	StringBuilder plan = new StringBuilder();

	for (Lancamento l : lancamentos) {
	    plan.append(l.getBeneficiario().getCnpj() + ";");
	    plan.append(l.getBeneficiario().getNome() + "\n");
	}

	System.out.print(plan);
    }

    private static void gera1708(List<Lancamento> lancamentos) {
	StringBuilder ir1708 = new StringBuilder("IDREC|1708|\n");

	int cont = 0;

	for (Lancamento lancamento : lancamentos) {

	    boolean tem1708 = false;

	    // verifica se existe lançamento em algum mês
	    for (Pagamento pgto : lancamento.getPagamentos()) {

		String bruto = pgto.getBruto().trim();

		if (bruto.isEmpty())
		    continue;

		if ("0".equals(bruto))
		    continue;

		if ("00".equals(bruto))
		    continue;

		if ("00".equals(bruto))
		    continue;

		tem1708 = true;
	    }

	    if (!tem1708)
		continue;

	    // cnpj e razão social
	    ir1708.append("BPJDEC|");
	    ir1708.append(lancamento.getBeneficiario().getCnpj() + "|");
	    String nome = lancamento.getBeneficiario().getNome().toUpperCase();

	    if (nome.length() <= 40)
		ir1708.append(nome + "|\n");
	    else
		ir1708.append(nome.substring(0, 39) + "|\n");

	    // bruto e ir
	    StringBuilder bruto = new StringBuilder("RTRT|");
	    StringBuilder imposto = new StringBuilder("RTIRF|");

	    for (Pagamento pagamento : lancamento.getPagamentos()) {
		bruto.append(pagamento.getBruto().trim() + "|");
		imposto.append(pagamento.getIrrf().trim() + "|");
	    }

	    bruto.append("|\n");
	    imposto.append("|\n");

	    ir1708.append(bruto);
	    ir1708.append(imposto);

	    cont++;

	    // if (cont > 10)
	    // break;
	}

	System.out.println(ir1708.toString());

    }

    private static void gera5952(List<Lancamento> lancamentos) {
	StringBuilder pcs5952 = new StringBuilder("IDREC|5952|\n");

	int cont = 0;

	for (Lancamento lancamento : lancamentos) {

	    boolean tem5952 = false;

	    // verifica se existe retenções 5952
	    for (Pagamento pgto : lancamento.getPagamentos()) {
		// System.out.println("Pis: " + pgto.getPis() + " - Mês: "
		// + pgto.getMes() + "  - "
		// + lancamento.getBeneficiario().getNome());

		String pis = pgto.getPis().trim();

		if (pis.isEmpty())
		    continue;

		if ("0".equals(pis))
		    continue;

		if ("00".equals(pis))
		    continue;

		if ("000".equals(pis))
		    continue;

		tem5952 = true;
	    }

	    if (!tem5952)
		continue;

	    // cnpj e razão social
	    pcs5952.append("BPJDEC|");
	    pcs5952.append(lancamento.getBeneficiario().getCnpj() + "|");
	    String nome = lancamento.getBeneficiario().getNome().toUpperCase();

	    if (nome.length() <= 40)
		pcs5952.append(nome + "|\n");
	    else
		pcs5952.append(nome.substring(0, 39) + "|\n");

	    // bruto e 5952
	    StringBuilder bruto = new StringBuilder("RTRT|");
	    StringBuilder imposto = new StringBuilder("RTIRF|");

	    for (Pagamento pagamento : lancamento.getPagamentos()) {
		bruto.append(pagamento.getBruto().trim() + "|");

		if (pagamento.getPis().isEmpty())
		    imposto.append("|");
		else {
		    int soma = 0;
		    soma += Integer.parseInt(pagamento.getPis()
			    .replace(" ", ""));
		    soma += Integer.parseInt(pagamento.getCofins().replace(" ",
			    ""));
		    soma += Integer.parseInt(pagamento.getCsll().replace(" ",
			    ""));
		    imposto.append(soma + "|");
		}
	    }

	    bruto.append("|\n");
	    imposto.append("|\n");

	    pcs5952.append(bruto);
	    pcs5952.append(imposto);

	    cont++;

	    // if (cont > 10)
	    // break;
	}

	System.out.println(pcs5952.toString());

    }
}
