//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class BookDescription{
    private JTextArea descriptionArea;

    public BookDescription() {
    }

    public void createGUI() {
        JFrame frame = new JFrame("Book Page Example");
        frame.setDefaultCloseOperation(3);
        frame.setSize(800, 600);
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.decode("#F0F3ED"));
        leftPanel.setLayout(new BoxLayout(leftPanel, 1));
        ImageIcon imageIcon = new ImageIcon("D:\\MAHAA USER DATA\\OneDrive\\Desktop\\book1.jpg");
        int newWidth = 200;
        int newHeight = 300;
        Image originalImage = imageIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, 4);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(120, 260, 0, 0));
        leftPanel.add(imageLabel);
        JButton fictionButton = new JButton("Fiction");
        fictionButton.setBorderPainted(true);
        fictionButton.setFocusPainted(false);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        fictionButton.setBorder(lineBorder);
        fictionButton.setBackground(Color.decode("#F0F3ED"));
        fictionButton.setForeground(Color.BLUE);
        Border lineBorder4 = BorderFactory.createLineBorder(Color.BLUE, 1);
        fictionButton.setBorder(lineBorder4);
        fictionButton.setFont(new Font("Calibri", 1, 20));
        fictionButton.setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 0));
        leftPanel.add(fictionButton);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout((LayoutManager)null);
        rightPanel.setBackground(Color.decode("#F0F3ED"));
        JLabel titleLabel = new JLabel("Unspeakable Beauty");
        titleLabel.setFont(new Font("Calibri", 1, 40));
        titleLabel.setBounds(30, 100, 500, 100);
        rightPanel.add(titleLabel);
        JLabel authorLabel = new JLabel("Georgia Carys Williams");
        authorLabel.setFont(new Font("Calibri", 1, 20));
        authorLabel.setBounds(30, 135, 500, 100);
        rightPanel.add(authorLabel);
        JButton readButton = new JButton("Read");
        readButton.setFont(new Font("Calibri", 1, 20));
        readButton.setBorderPainted(false);
        readButton.setFocusPainted(false);
        readButton.setBackground(Color.blue);
        readButton.setForeground(Color.WHITE);
        readButton.setBounds(30, 220, 150, 50);
        rightPanel.add(readButton);
        final JPopupMenu optionMenu = new JPopupMenu();
        optionMenu.setPreferredSize(new Dimension(200, 150));
        optionMenu.setForeground(Color.BLACK);
        optionMenu.setBackground(Color.LIGHT_GRAY);
        optionMenu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JMenuItem addToList = new JMenuItem("Add to Bookshelf");
        JMenuItem alreadyRead = new JMenuItem("Already Read");
        JMenuItem impressions = new JMenuItem("Impressions");
        optionMenu.add(addToList);
        optionMenu.add(alreadyRead);
        optionMenu.add(impressions);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        optionMenu.setBorder(border);
        final JButton optionButton = new JButton("...");
        optionButton.setBorderPainted(true);
        optionButton.setFocusPainted(false);
        Border lineBorder3 = BorderFactory.createLineBorder(Color.BLUE, 1);
        optionButton.setBorder(lineBorder3);
        optionButton.setBackground(Color.decode("#F0F3ED"));
        optionButton.setForeground(Color.BLUE);
        optionButton.setBounds(200, 220, 50, 50);
        rightPanel.add(optionButton);
        optionButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                optionMenu.show(optionButton, 0, optionButton.getHeight());
            }
        });
        JButton shareButton = new JButton("...");
        shareButton.setBorderPainted(true);
        shareButton.setFocusPainted(false);
        Border lineBorder2 = BorderFactory.createLineBorder(Color.BLUE, 1);
        shareButton.setBorder(lineBorder2);
        shareButton.setBackground(Color.decode("#F0F3ED"));
        shareButton.setForeground(Color.BLUE);
        shareButton.setBounds(270, 220, 50, 50);
        rightPanel.add(shareButton);
        final JButton aboutButton = new JButton("About");
        aboutButton.setBorderPainted(false);
        aboutButton.setFocusPainted(false);
        aboutButton.setFont(new Font("Calibri", 0, 20));
        aboutButton.setBackground(Color.decode("#F0F3ED"));
        aboutButton.setForeground(Color.BLACK);
        aboutButton.setBounds(30, 330, 100, 50);
        rightPanel.add(aboutButton);
        aboutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                aboutButton.setForeground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                aboutButton.setForeground(Color.BLACK);
            }
        });
        final JButton impressionButton = new JButton("Readers");
        impressionButton.setBorderPainted(false);
        impressionButton.setFocusPainted(false);
        impressionButton.setFont(new Font("Calibri", 0, 20));
        impressionButton.setBackground(Color.decode("#F0F3ED"));
        impressionButton.setForeground(Color.BLACK);
        impressionButton.setBounds(130, 330, 150, 50);
        rightPanel.add(impressionButton);
        impressionButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                impressionButton.setForeground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                impressionButton.setForeground(Color.BLACK);
            }
        });
        final JButton reviewButton = new JButton("Reviews");
        reviewButton.setBorderPainted(false);
        reviewButton.setFocusPainted(false);
        reviewButton.setFont(new Font("Calibri", 0, 20));
        reviewButton.setBackground(Color.decode("#F0F3ED"));
        reviewButton.setForeground(Color.BLACK);
        reviewButton.setBounds(280, 330, 150, 50);
        rightPanel.add(reviewButton);
        reviewButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                reviewButton.setForeground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                reviewButton.setForeground(Color.BLACK);
            }
        });
        this.descriptionArea = new JTextArea("Growing up in a lonely house on the edge of a wild common, Violet Hart is a quiet and sheltered only child who has always dreamt of becoming something extraordinary: a ballet icon as famous as Margot Fonteyn.\n\nGuarding her dream closely after suffering catastrophic loss, Violet falls further into quietness, learning to speak only with her feet as she pursues a path to a career in dance. On the cusp of adulthood, she finally starts to find her voice");
        this.descriptionArea.setFont(new Font("Calibri", 0, 16));
        this.descriptionArea.setLineWrap(true);
        this.descriptionArea.setWrapStyleWord(true);
        this.descriptionArea.setEditable(false);
        this.descriptionArea.setBackground(Color.decode("#F0F3ED"));
        this.descriptionArea.setBounds(30, 400, 520, 200);
        rightPanel.add(this.descriptionArea);
        String aboutContent = "Growing up in a lonely house on the edge of a wild common, Violet Hart is a quiet and sheltered only child who has always dreamt of becoming something extraordinary: a ballet icon as famous as Margot Fonteyn.\n\nGuarding her dream closely after suffering catastrophic loss, Violet falls further into quietness, learning to speak only with her feet as she pursues a path to a career in dance. On the cusp of adulthood, she finally starts to find her voice.";
        String impressionsContent = "Impressions content here.";
        String reviewsContent = "Reviews content here.";
        aboutButton.addActionListener((e) -> {
            this.descriptionArea.setText(aboutContent);
            this.descriptionArea.setFont(new Font("Calibri", 0, 16));
        });
        impressionButton.addActionListener((e) -> {
            this.descriptionArea.setText(impressionsContent);
            this.descriptionArea.setFont(new Font("Calibri", 0, 16));
        });
        reviewButton.addActionListener((e) -> {
            this.descriptionArea.setText(reviewsContent);
            this.descriptionArea.setFont(new Font("Calibri", 0, 16));
        });
        JLabel printedPagesLabel = new JLabel("358 PRINTED PAGES");
        printedPagesLabel.setFont(new Font("Calibri", 0, 11));
        printedPagesLabel.setBounds(30, 610, 150, 20);
        rightPanel.add(printedPagesLabel);
        JLabel copyrightLabel = new JLabel("COPYRIGHT OWNER: BOOKWIRE");
        copyrightLabel.setFont(new Font("Calibri", 0, 11));
        copyrightLabel.setBounds(30, 635, 200, 20);
        rightPanel.add(copyrightLabel);
        JLabel originalPublicationYearLabel = new JLabel("ORIGINAL PUBLICATION: 2024");
        originalPublicationYearLabel.setFont(new Font("Calibri", 0, 11));
        originalPublicationYearLabel.setBounds(30, 660, 200, 20);
        rightPanel.add(originalPublicationYearLabel);
        JLabel publicationYearLabel = new JLabel("PUBLICATION YEAR: 2024");
        publicationYearLabel.setFont(new Font("Calibri", 0, 11));
        publicationYearLabel.setBounds(30, 685, 200, 20);
        rightPanel.add(publicationYearLabel);
        JLabel publisherLabel = new JLabel("PUBLISHER: PARTHIAN BOOKS");
        publisherLabel.setFont(new Font("Calibri", 0, 11));
        publisherLabel.setBounds(30, 710, 200, 20);
        rightPanel.add(publisherLabel);
        JSplitPane splitPane = new JSplitPane(1, leftPanel, rightPanel);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(500);
        frame.add(splitPane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookDescription example = new BookDescription();
            example.createGUI();
        });
    }
}