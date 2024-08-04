package com.example.newc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import com.example.homepage2.HomePage2;
import com.example.database.DatabaseConnection;
import com.example.homepage3.HomePage3;
import com.example.main.Main2;
//import com.example.main.User;



/*class Shape extends JComponent {
    public void paint(Graphics g){
        Graphics2D g1 = (Graphics2D) g;
        g1.drawRect(100,150,60,200);
    }
}*/

public class NewClass2 extends JFrame {

    JLabel label;
    JPanel panel;
    JPanel panel1;
    JPanel signuppanel;
    JLabel head,usr,pass,head1,pass1,usr1,signup,head2,usr2,pass2,pass3;
    JTextField usrbox,usrbox1,usrbox2,usrbox3;
    JButton but1,but2,change,change1,but3,but4;
    JPasswordField passbox,passbox1,passbox2,passbox3;
    public NewClass2(){
        setTitle("Login Page");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        label = new JLabel();
        label.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Lithika\\finalproject\\login.png").getImage().getScaledInstance(1550,900, Image.SCALE_SMOOTH)));
        setLayout(null);
        setContentPane(label);
        setVisible(true);

        panel = new JPanel();
        panel.setBounds(565, 175, 430, 490);
        panel.setBackground(new Color(0, 4, 9, 200));
        panel.setLayout(null);
        panel.setVisible(true);

        add(panel);

        head = new JLabel("LIBRARIAN");
        panel.add(head);
        head.setBounds(160,10,140,100);
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Ariel",Font.BOLD,22));

        usr = new JLabel("Username:");
        usr.setBounds(17,20,200,300);
        usr.setForeground(Color.WHITE);
        usr.setFont(new Font("Verdana",Font.PLAIN,18));
        panel.add(usr);

        usrbox = new JTextField();
        usrbox.setBounds(150,163,250,20);
        usrbox.setBackground(Color.white);
        panel.add(usrbox);

        pass = new JLabel("Password:");
        panel.add(pass);
        pass.setBounds(17,105,200,300);
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("Verdana",Font.PLAIN,18));

        passbox = new JPasswordField();
        panel.add(passbox);
        passbox.setBounds(150,247,250,20);
        passbox.setBackground(Color.WHITE);

        but1 = new JButton("Login");
        panel.add(but1);
        but1.setBounds(175,340,100,40);
        but1.setBackground(new Color(115, 222, 230));

        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usrbox.getText();
                String password = new String(passbox.getPassword());
                try {
                    if (librarianLogIn(username, password)) {
                        // Credentials are correct, open Main2 frame
                        Main2 main2 = new Main2();
                        main2.setVisible(true);
                        dispose(); // Close the current frame
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });







        change = new JButton("If USER click here");
        panel.add(change);
        change.setBounds(127,430,200,50);
        change.setBackground(new Color(0, 4, 9, 100));
        change.setBorder(null);
        change.setForeground(Color.white);
        change.addActionListener(changePage);
        //change.setFont(new Font("Ariel",Font.PLAIN,20));

        panel1 = new JPanel();
        panel1.setBounds(565, 175, 430, 550);
        panel1.setBackground(new Color(0, 4, 9, 200));
        panel1.setLayout(null);
        panel1.setVisible(false);

        add(panel1);

        head1 = new JLabel("EXISTING USER");
        panel1.add(head1);
        head1.setBounds(130,10,175,100);
        head1.setForeground(Color.white);
        head1.setFont(new Font("Ariel",Font.BOLD,22));

        usr1 = new JLabel("Username:");
        usr1.setBounds(17,20,200,300);
        usr1.setForeground(Color.WHITE);
        usr1.setFont(new Font("Verdana",Font.PLAIN,18));
        panel1.add(usr1);

        usrbox1 = new JTextField();
        usrbox1.setBounds(150,163,250,20);
        usrbox1.setBackground(Color.WHITE);
        panel1.add(usrbox1);

        pass1 = new JLabel("Password:");
        panel1.add(pass1);
        pass1.setBounds(17,105,200,300);
        pass1.setForeground(Color.WHITE);
        pass1.setFont(new Font("Verdana",Font.PLAIN,18));

        passbox1 = new JPasswordField();
        panel1.add(passbox1);
        passbox1.setBounds(150,247,250,20);
        passbox1.setBackground(Color.WHITE);

        but2 = new JButton("Login");
        panel1.add(but2);
        but2.setBounds(175,340,100,40);
        but2.setBackground(Color.cyan);



        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the entered username and password
                String username = usrbox1.getText();
                String password = new String(passbox1.getPassword());

                // Perform user login
                try {
                    if (Main2.userLogin(username, password)) {
                        // If login successful, show a success message
                        storeLoggedInUserId(username);

                        JOptionPane.showMessageDialog(null, "Login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // Open new UI or perform other actions
                                new HomePage3();
                            }
                        });
                    } else {
                        // If login failed, show an error message
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error during login.");
                    ex.printStackTrace();
                }
            }




            private void handleLogin(int userId) {
                // Update the logged_in_user table with the current user ID
                String updateQuery = "UPDATE logged_in_user SET user_id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, userId);
                    updateStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }
            }



            private void storeLoggedInUserId(String username) {
                int user_id = getUserIdByUsername(username);
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "MERGE INTO logged_in_user l " +
                            "USING (SELECT ? AS user_id FROM dual) u " +
                            "ON (l.user_id = u.user_id) " +
                            "WHEN MATCHED THEN " +
                            "  UPDATE SET l.login_time = CURRENT_TIMESTAMP " +
                            "WHEN NOT MATCHED THEN " +
                            "  INSERT (user_id, login_time) VALUES (u.user_id, CURRENT_TIMESTAMP)";

                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, user_id);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println("Error storing logged-in user ID: " + e.getMessage());
                    e.printStackTrace();
                }
            }


            private int getUserIdByUsername(String username) {
                int user_id = -1;
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT user_id FROM users WHERE username = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, username);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                user_id = rs.getInt("user_id");
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving user ID: " + e.getMessage());
                    e.printStackTrace();
                }

                return user_id;
            }
        });










        but3 = new JButton("Sign up");
        panel1.add(but3);
        but3.setBounds(175,420,100,25);
        but3.setBackground(new Color(0, 4, 9, 100));
        but3.setForeground(Color.cyan);
        but3.addActionListener(signupp);

        change1 = new JButton("If LIBRARIAN click here");
        panel1.add(change1);
        change1.setBounds(120,460,200,50);
        change1.setBackground(new Color(0, 4, 9, 100));
        change1.setBorder(null);
        change1.setForeground(Color.WHITE);
        change1.addActionListener(reverse);

        signuppanel = new JPanel();
        signuppanel.setBounds(555, 175, 460, 550);
        signuppanel.setBackground(new Color(0, 4, 9, 200));
        signuppanel.setLayout(null);
        signuppanel.setVisible(false);

        add(signuppanel);

        /*ImageIcon icon = new ImageIcon("D:\\D\\Clg Fo\\java project\\mavenproject1\\target\\classes\\close Jframe.png");
        signuppanel.add(icon);*/

        JButton but5 = new JButton("<--");
        signuppanel.add(but5);
        but5.setBounds(10,50,28,20);
        but5.setBorder(null);
        but5.setBackground(new Color(0, 4, 9, 100));
        but5.setForeground(Color.WHITE);
        but5.addActionListener(backbutt);

        head2 = new JLabel("NEW USER SIGN UP");
        signuppanel.add(head2);
        head2.setBounds(130,10,200,100);
        head2.setForeground(Color.white);
        head2.setFont(new Font("Ariel",Font.BOLD,20));

        usr2 = new JLabel("New Username:");
        usr2.setBounds(17,20,200,300);
        usr2.setForeground(Color.WHITE);
        usr2.setFont(new Font("Verdana",Font.PLAIN,15));
        signuppanel.add(usr2);

        usrbox2 = new JTextField();
        usrbox2.setBounds(180,160,250,20);
        usrbox2.setBackground(Color.white);
        signuppanel.add(usrbox2);

        pass2 = new JLabel("Password:"); /*password box */
        signuppanel.add(pass2);
        pass2.setBounds(17,239,200,20);
        pass2.setForeground(Color.WHITE);
        pass2.setBackground(new Color(0, 4, 9, 200));
        pass2.setFont(new Font("Verdana",Font.PLAIN,15));

        passbox2 = new JPasswordField();
        signuppanel.add(passbox2);
        passbox2.setBounds(180,240,250,20);
        passbox2.setBackground(Color.WHITE);

        pass3 = new JLabel("E-Mail Id:");
        signuppanel.add(pass3);
        pass3.setBounds(17,179,200,300);
        pass3.setForeground(Color.WHITE);
        pass3.setFont(new Font("Verdana",Font.PLAIN,15));

        passbox3 = new JPasswordField();
        signuppanel.add(passbox3);
        passbox3.setBounds(180,320,250,20);
        passbox3.setBackground(Color.WHITE);

        JLabel pass4 = new JLabel("Age :"); /*Age */
        signuppanel.add(pass4);
        pass4.setBounds(17,255,200,300);
        pass4.setForeground(Color.WHITE);
        pass4.setFont(new Font("Verdana",Font.PLAIN,15));

        usrbox3 = new JTextField();
        signuppanel.add(usrbox3);
        usrbox3.setBounds(180,400,250,20);
        usrbox3.setBackground(Color.white);

        but4 = new JButton("Sign up");
        signuppanel.add(but4);
        but4.setBounds(175, 420, 100, 25);
        but4.setBackground(new Color(0, 4, 9, 100));
        but4.setForeground(Color.CYAN);
        but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the signup function in Main2.java
                try {
                    int age = Integer.parseInt(usrbox3.getText());
                    Main2.signUpUser(usrbox2.getText(), passbox3.getText(), new String(passbox2.getPassword()), age);
                    //JOptionPane.showMessageDialog(null, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new HomePage2();
                        }
                    });
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




    }


    private boolean librarianLogIn(String username, String password) throws SQLException {
        // Implement the actual login logic here
        return true; // Return true if login is successful, false otherwise
    }


    ActionListener changePage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setVisible(false);
            panel1.setVisible(true);
            signuppanel.setVisible(false);
        }
    };

    ActionListener backbutt = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel1.setVisible(true);
            panel.setVisible(false);
            signuppanel.setVisible(false);
        }
    };






    ActionListener reverse = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel1.setVisible(false);
            panel.setVisible(true);
            signuppanel.setVisible(false);
        }
    };

    ActionListener signupp = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            panel1.setVisible(false);
            panel.setVisible(false);
            signuppanel.setVisible(true);
        }



    };
    public static void main(String[] args){
        new NewClass2();
    }
}