import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUI extends JFrame{
    private JPanel mainPanel;
    private JButton btnCancel;
    private JTextField textUser;
    private JPasswordField textPwd;
    private JButton btnOk;
    private JLabel labelIcon;
    private JLabel labelLogin;
    private JLabel labelPassword;

    public GUI(){
        this.setTitle("Connection à la base");
        this.setContentPane(mainPanel);
        this.pack();
        this.getRootPane().setDefaultButton(btnOk);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Modele mod = new Modele();
                String log = textUser.getText();
                String pass = new String(textPwd.getPassword());
                String req = "SELECT COUNT(*) FROM utilisateur WHERE login='"+log+"' AND password='"+pass+"'";
                System.out.println(req);

                try{
                    if(mod.verifLogin(req) == 1){
                        GUI_Contact fen = new GUI_Contact();
                        fen.setResizable(false);
                        fen.setVisible(true);
                        fen.setLocationRelativeTo(null);
                        setVisible(false);

                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Erreur de login !");
                        textUser.setText("");
                        textPwd.setText("");
                        textUser.requestFocus();
                    }
                }
                catch (SQLException se){
                    System.out.println("Erreur" + se);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textUser.setText("");
                textPwd.setText("");
                textUser.requestFocus();
            }
        });
    }


}
