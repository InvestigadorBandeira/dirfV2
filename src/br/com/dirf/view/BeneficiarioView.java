package br.com.dirf.view;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.dirf.controller.BeneficiarioController;

public class BeneficiarioView extends JDialog {
    private final JLabel label;
    private final JTextField txtArquivo;
    private final JButton btnCarregar;
    private final JButton btnProcessar;
    private final JTextArea txtInformacoes;

    private File arquivo;
    private final BeneficiarioController controller;
    private final JScrollPane scrollPane;

    public BeneficiarioView(BeneficiarioController controller) {
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setResizable(false);
	setModal(true);
	setTitle("Criar Benefici\u00E1rios");
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(null);

	label = new JLabel("Arquivo:");
	label.setFont(new Font("Tahoma", Font.BOLD, 12));
	label.setBounds(10, 23, 68, 14);
	getContentPane().add(label);

	txtArquivo = new JTextField();
	txtArquivo.setEditable(false);
	txtArquivo.setText(" Nenhum arquivo selecionado.");
	txtArquivo.setFont(new Font("Tahoma", Font.BOLD, 12));
	txtArquivo.setColumns(10);
	txtArquivo.setBackground(SystemColor.info);
	txtArquivo.setBounds(75, 18, 359, 25);
	getContentPane().add(txtArquivo);

	btnCarregar = new JButton("Carregar Arquivo");
	btnCarregar.addActionListener(new BtnCarregarAction());
	btnCarregar.setBounds(10, 54, 140, 30);
	getContentPane().add(btnCarregar);

	btnProcessar = new JButton("Processar Arquivo");
	btnProcessar.addActionListener(new BtnProcessarAction());
	btnProcessar.setBounds(10, 112, 140, 30);
	getContentPane().add(btnProcessar);

	scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 153, 424, 108);
	getContentPane().add(scrollPane);

	txtInformacoes = new JTextArea();
	scrollPane.setViewportView(txtInformacoes);
	txtInformacoes.setFont(new Font("Tahoma", Font.BOLD, 12));

	//
	setLocationRelativeTo(null);
	this.controller = controller;
    }

    public void setInformacoes(String informacao) {
	txtInformacoes.setText(informacao);
    }

    private class BtnCarregarAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    JFileChooser chooser = new JFileChooser(".");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setDialogTitle("Abrir arquivo de Beneficiários (CSV)");

	    chooser.addChoosableFileFilter(new FileNameExtensionFilter(
		    "Arquivo CSV", "csv"));

	    int opcao = chooser.showOpenDialog(null);

	    if (opcao == JFileChooser.APPROVE_OPTION) {
		txtArquivo.setText(chooser.getSelectedFile().getAbsolutePath());
		arquivo = chooser.getSelectedFile();
	    }
	}
    }

    private class BtnProcessarAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    if (arquivo == null || !arquivo.exists())
		JOptionPane.showMessageDialog(null,
			"Nenhum arquivo selecionado.", "Erro no processamento",
			JOptionPane.ERROR_MESSAGE);
	    else
		try {
		    controller.processaArquivo(arquivo);
		} catch (Exception ex) {
		    setInformacoes(ex.getMessage());
		}
	}

    }
}
