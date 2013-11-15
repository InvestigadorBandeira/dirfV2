package br.com.dirf.model;

public class Beneficiario implements Comparable<Beneficiario> {

    private String cnpj;
    private String nome;

    public String getCnpj() {
	return cnpj;
    }

    public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    @Override
    public int compareTo(Beneficiario o) {
	return cnpj.compareTo(o.cnpj);
    }

    @Override
    public String toString() {
	return cnpj + " - " + nome;
    }

}
