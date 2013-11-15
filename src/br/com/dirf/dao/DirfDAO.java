package br.com.dirf.dao;

import br.com.dirf.dao.xml.Dados;
import br.com.dirf.dao.xml.XMLDAO;

public class DirfDAO {
    XMLDAO dao;

    public DirfDAO() {
	dao = new XMLDAO();
    }

    public Dados getDados() {
	return dao.instance();
    }

    public void salvar() {
	// versão antiga
	// dao.salvar();

	// versão 2 com XStream
	dao.salvarXStream();
    }

}
