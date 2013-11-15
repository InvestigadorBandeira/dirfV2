package br.com.dirf.exception;

public class DirfException extends Exception {

    public DirfException() {
	super();
    }

    public DirfException(String mensagem, Throwable causa) {
	super(mensagem, causa);
    }

    public DirfException(String mensagem) {
	super(mensagem);
    }

    public DirfException(Throwable causa) {
	super(causa);
    }

}
