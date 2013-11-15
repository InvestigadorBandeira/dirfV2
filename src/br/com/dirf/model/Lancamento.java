package br.com.dirf.model;

import java.util.ArrayList;
import java.util.List;

public class Lancamento {
    private Beneficiario beneficiario;
    private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

    public Beneficiario getBeneficiario() {
	return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
	this.beneficiario = beneficiario;
    }

    public List<Pagamento> getPagamentos() {
	return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
	this.pagamentos = pagamentos;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(beneficiario).append("\n");

	for (Pagamento p : pagamentos)
	    builder.append(p).append("\n");
	return builder.toString();
    }

}
