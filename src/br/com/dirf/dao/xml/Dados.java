package br.com.dirf.dao.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.dirf.model.Beneficiario;
import br.com.dirf.model.Lancamento;

public class Dados {

    private List<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();
    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

    public List<Beneficiario> getBeneficiarios() {
	Collections.sort(beneficiarios);
	return beneficiarios;
    }

    public void setBeneficiarios(List<Beneficiario> beneficiarios) {
	this.beneficiarios = beneficiarios;
    }

    public List<Lancamento> getLancamentos() {
	return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
	this.lancamentos = lancamentos;
    }

}
