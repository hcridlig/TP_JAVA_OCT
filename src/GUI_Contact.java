import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;

public class GUI_Contact extends JFrame{
    private JPanel mainPanel;
    private JButton btnReset;
    private JButton btnAnnuler;
    private JButton btnValider;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel middlePanel;
    private JTextField textName;
    private JLabel labelName;
    private JTextField textFirstName;
    private JLabel labelFirstName;
    private JLabel labelDate;
    private JLabel labelLieuNaiss;
    private JLabel labelCountry;
    private JFormattedTextField textDate;
    private JTextField textLieuNaiss;
    private JTextField textCountry;
    private JFormattedTextField textPhone;
    private JTextField textMail;
    private JRadioButton radioMan;
    private JRadioButton radioWoman;
    private JCheckBox checkSport;
    private JCheckBox checkMusic;
    private JCheckBox checkTrip;
    private JCheckBox checkRead;
    private JTextField textStreet;
    private JTextField textCity;
    private JFormattedTextField textCp;
    private JLabel labelStreet;
    private JLabel labelCity;
    private JLabel labelCp;
    private JComboBox comboFiliere;
    private JComboBox comboLevel;
    private JComboBox comboBac;
    private JTextArea textAreaAffichage;
    private JLabel labelFiliere;
    private JLabel labelLevel;
    private JLabel labelBac;
    private JLabel labelMotivation;

    public GUI_Contact() throws SQLException {
        this.setContentPane(mainPanel);
       // this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        this.getRootPane().setDefaultButton(btnValider);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioMan);
        radioGroup.add(radioWoman);

        Modele mod = new Modele();
        mod.remplirCombo3(comboFiliere, "SELECT nom FROM filiere");
        mod.remplirCombo3(comboBac, "SELECT libelle FROM bac");

        try {
            MaskFormatter mask = new MaskFormatter("####-##-##");
            mask.install(textDate);

            mask = new MaskFormatter("#####");
            mask.install(textCp);

            mask = new MaskFormatter("+## # ## ## ## ##");
            mask.install(textPhone);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textName.setText("");
                textFirstName.setText("");
                textDate.setText("");
                textLieuNaiss.setText("");
                textCountry.setText("");
                textStreet.setText("");
                textCp.setText("");
                textCity.setText("");
                textPhone.setText("");
                textMail.setText("");

                radioGroup.clearSelection();

                checkSport.setSelected(false);
                checkMusic.setSelected(false);
                checkTrip.setSelected(false);
                checkRead.setSelected(false);

                textAreaAffichage.setText("");


            }
        });
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioMan.setActionCommand("Homme");
                radioWoman.setActionCommand("Femme");
                String radioString = radioGroup.getSelection().getActionCommand();

                textAreaAffichage.append(textName.getText() + "\n" + textFirstName.getText() + "\n" + textDate.getText() + "\n" + textLieuNaiss.getText() + "\n" + textCountry.getText() + "\n" + textStreet.getText() + "\n" + textCp.getText() + "\n" + textCity.getText() + "\n" + textPhone.getText() + "\n" + textMail.getText() + "\n" + radioString + "\n" );


                String[] options = {"Oui", "Non"};
                int n = JOptionPane.showConfirmDialog(null,"Êtes-vous sûr d'entrer ces informations", "Confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if(n==0){

                    Connection connexion = null;
                    Statement stmt = null;

                    String id1 = "SELECT * FROM bac";
                    String id2 = "SELECT * FROM filiere";

                    String url = "jdbc:mysql://localhost:3306/bddgraph";
                    String user = "root";
                    String pass = "";


                    try {
                        connexion = mod.seConnecter(url, user, pass);
                        int indexFiliere = comboFiliere.getSelectedIndex() + 1;
                        int indexBac = comboBac.getSelectedIndex() + 1;
                        String req = "INSERT INTO `etudiant` (`idEtu`, `nom`, `prenom`, `dateNaiss`, `lieuNaiss`, `sexe`, `nationalite`, `rue`, `cp`, `ville`, `telephone`, `mail`, `niveau`, `idFil`, `idBac`) VALUES (NULL, '" + textName.getText() +"', '" + textFirstName.getText() + "', '" + textDate.getText() + "', '" + textLieuNaiss.getText() + "', '" + radioString + "', '" + textCountry.getText() + "', '" + textStreet.getText() + "', '" + textCp.getText() + "', '" + textStreet.getText() + "', '" + textPhone.getText() + "', '" + textMail.getText() + "', '" + comboLevel.getSelectedItem() + "', '" + indexFiliere + "', '" + indexBac + "')";
                        System.out.println(req);
                        stmt = (Statement) connexion.createStatement();
                        int result = stmt.executeUpdate(req);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
