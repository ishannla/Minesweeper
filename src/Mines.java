import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Mines extends JFrame {
	
    private final int WIDTH = 239;
    private final int HEIGHT = 290;
    public static int numberOfMines = 0;

    private JLabel statusbar;
    
    public static void main(String[] args) {
    	//Extra Credit: Adding a GUI window to allow user to select number of mines
        final JFrame mineChooser = new JFrame("Minesweeper Preferences");
        JPanel contentPane = new JPanel();
        
        contentPane.setOpaque(true);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        JLabel label = new JLabel("Select the number of mines that you want:", JLabel.CENTER);
        label.setSize(350, 80);
        label.setLocation(0, 0);

        JButton ten = new JButton("10 mines - Easy");
        ten.setSize(170, 35);
        ten.setLocation(90, 70);
        
        JButton twenty = new JButton("20 mines - Easy");
        twenty.setSize(170, 35);
        twenty.setLocation(90, 120);
        
        JButton thirty = new JButton("30 mines - Medium");
        thirty.setSize(170, 35);
        thirty.setLocation(90, 170); 
        
        JButton forty = new JButton("40 mines - Medium");
        forty.setSize(170, 35);
        forty.setLocation(90, 220); 
        
        JButton fifty = new JButton("50 mines - Hard");
        fifty.setSize(170, 35);
        fifty.setLocation(90, 270); 
        
        JButton sixty = new JButton("60 mines - Hard");
        sixty.setSize(170, 35);
        sixty.setLocation(90, 320); 

        contentPane.add(label);
        contentPane.add(ten);
        contentPane.add(twenty);
        contentPane.add(thirty); 
        contentPane.add(forty); 
        contentPane.add(fifty);
        contentPane.add(sixty); 
        
        ten.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 10;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
        
        twenty.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 20;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
        
        thirty.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 30;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
        
        forty.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 40;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
        
        fifty.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 50;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
     
        sixty.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		numberOfMines = 60;
            		mineChooser.setVisible(false);
            		new Mines();
            }
        });
        
        mineChooser.setContentPane(contentPane);
        mineChooser.setSize(350, 400);
        mineChooser.setLocationRelativeTo(null);
        mineChooser.setLocationByPlatform(true);
        mineChooser.setVisible(true);
    }

    public Mines() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar));

        setResizable(false);
        setVisible(true);
    }

   
}
