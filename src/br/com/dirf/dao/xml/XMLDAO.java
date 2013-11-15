package br.com.dirf.dao.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import br.com.dirf.model.Beneficiario;
import br.com.dirf.model.Lancamento;
import br.com.dirf.model.Pagamento;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class XMLDAO {
    private final String DATABASE = "database.xml";
    private Dados instance = null;
    private XStream xstream;

    public XMLDAO() {
	xstream = new XStream();
	alias(xstream);

	// versão antiga
	// ler();

	// versão 2 com XStream
	lerXStream();
    }

    public Dados instance() {
	if (instance == null)
	    lerXStream();
	return instance;
    }

    private void ler() {
	try {
	    XMLDecoder d = new XMLDecoder(new BufferedInputStream(
		    new FileInputStream(DATABASE)));
	    instance = (Dados) d.readObject();
	    d.close();
	} catch (FileNotFoundException e) {
	    instance = new Dados();
	}

	System.out.println("DATABASE lido.");

    }

    private void lerXStream() {

	xstream = new XStream(new Dom4JDriver());
	alias(xstream);

	try {
	    BufferedReader input = new BufferedReader(new FileReader(DATABASE));
	    // FileInputStream input = new FileInputStream(DATABASE);
	    instance = (Dados) xstream.fromXML(input);
	    input.close();
	} catch (IOException e) {
	    System.out.println("[lerXStream()] " + e.getMessage());
	    instance = new Dados();
	}

	System.out.println("DATABASE lido.");
    }

    public void salvarXStream() {

	xstream = new XStream();
	alias(xstream);

	try {
	    FileOutputStream output = new FileOutputStream(DATABASE);
	    output.write(xstream.toXML(instance).getBytes());
	    // xstream.toXML(instance, output);
	    output.close();
	    instance = null;
	    lerXStream();
	} catch (IOException e) {
	    throw new RuntimeException(
		    "Arquivo de banco de dados não pôde ser aberto/criado.");
	}

	System.out.println("DATABASE salvo.");
    }

    public void salvar() {
	try {
	    XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
		    new FileOutputStream(DATABASE)));
	    e.writeObject(instance);
	    e.close();
	    // backup();
	    instance = null;
	    ler();
	} catch (FileNotFoundException e) {
	    throw new RuntimeException(
		    "Arquivo de banco de dados não pôde ser aberto/criado.");
	}

	System.out.println("DATABASE salvo.");
    }

    private void alias(XStream xstream) {
	xstream.alias("lista", List.class);
	xstream.alias("beneficiario", Beneficiario.class);
	xstream.alias("lancamento", Lancamento.class);
	xstream.alias("pagamento", Pagamento.class);
	xstream.alias("dados", Dados.class);
    }

    private void backup() {
	try {
	    XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
		    new FileOutputStream("backup"
			    + Calendar.getInstance().getFirstDayOfWeek()
			    + ".xml")));
	    e.writeObject(instance);
	    e.close();
	} catch (FileNotFoundException e) {
	    throw new RuntimeException(
		    "Arquivo de backup não pode ser criado.\n" + e.getMessage());
	}

    }
}
