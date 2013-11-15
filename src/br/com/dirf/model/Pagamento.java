package br.com.dirf.model;

public class Pagamento {

    private int mes = 0;
    private String bruto = "";
    private String irrf = "";
    private String pis = "";
    private String cofins = "";
    private String csll = "";

    public int getMes() {
	return mes;
    }

    public void setMes(int mes) {
	this.mes = mes;
    }

    public String getBruto() {
	return bruto;
    }

    public void setBruto(String bruto) {
	this.bruto = bruto;
    }

    public String getIrrf() {
	return irrf;
    }

    public void setIrrf(String irrf) {
	this.irrf = irrf;
    }

    public String getPis() {
	return pis;
    }

    public void setPis(String pis) {
	this.pis = pis;
    }

    public String getCofins() {
	return cofins;
    }

    public void setCofins(String cofins) {
	this.cofins = cofins;
    }

    public String getCsll() {
	return csll;
    }

    public void setCsll(String csll) {
	this.csll = csll;
    }

    @Override
    public String toString() {
	return String.format("%02d - %10s - %10s - %10s - %10s - %10s", mes,
		getBruto().trim(), getIrrf().trim(), getPis().trim(),
		getCofins().trim(), getCsll().trim());
    }

}
