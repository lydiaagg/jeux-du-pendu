import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fenetre extends JFrame{
    //Creation du menu
	private HashMap<String,Gamer> baseDeDonnee=new HashMap<String,Gamer>();
	private JMenuBar Menu =new JMenuBar();
	private JMenu fichier =new JMenu("fichier");
	private JMenu Apropos =new JMenu("Apropos");
	private JMenuItem Commencer=new JMenuItem("Commencer Le Jeu");
	private JMenuItem Rejouer =new JMenuItem("Rejouer");
	private JMenuItem Fermer =new JMenuItem("Fermer");
	private JMenuItem change =new JMenuItem("Changer de joueur");
	private JPanel pan=new JPanel(),pan2=new JPanel(),pan3=new JPanel(),conteneur=new JPanel();
	private int scoreU=0,Nbre=0,nbr=0;
	private String Gamer=null;
	private JLabel Score=new JLabel("Score : "+scoreU),userG=new JLabel("Gamer : "+Gamer),MotTrouver=new JLabel("Nombre de mot trouvee: "+Nbre);
	private JButton[] bot=new JButton[26];
	private String mot = null,SecretMot="",mots="";
	private JLabel MotSecret =new JLabel(mot);
	private boolean pendre =false;
	//Cree Ma fenetre
   public Fenetre(){
	    this.setSize(700, 700);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.white);
	    this.setTitle("Le Pendu");
	    
	 //Entrer le menu dans la fenetre
	    this.fichier.add(Commencer);
	    this.fichier.add(Rejouer);
	    this.fichier.add(Fermer);
	    this.fichier.add(change);
	    this.Menu.add(fichier);
	    this.Menu.add(Apropos);
	  //Ajouter des fonctions au item
	    pan.add(new JLabel(new ImageIcon("D:\\ProjetJava\\Jeux de Pendu\\Pondu\\pendu.png")));
	    //Fermer le jeu
	    Fermer.addActionListener(new ActionListener(){
	    	
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
	    });
	    //inscrire un joueur
	    Commencer.addActionListener(new ActionListener()
	    {  public void actionPerformed(ActionEvent arg0)
	    {  
	    	
	        Gamer =JOptionPane.showInputDialog(null,"Nom Utilisateur");
	        Gamer g=new Gamer(Gamer);
	        baseDeDonnee.put(Gamer, g);
	       
	        play(g);
	        repaintPanel();
	    }
	    });
	    //pour rejouer
	    Rejouer.addActionListener(new ActionListener()
	    {  public void actionPerformed(ActionEvent arg0)
	    {  
	        
	        if(baseDeDonnee.get(Gamer)==null)
	        {
	        	Gamer =JOptionPane.showInputDialog(null,"Nom Utilisateur");
	        }
	        else{
	        	baseDeDonnee.get(Gamer).setScore(0);
	             baseDeDonnee.get(Gamer).setNbreMactchG(0);
	        	play(baseDeDonnee.get(Gamer));}
	        repaintPanel();
	    }
	    });
	    //pour changer de joueuer
	    change.addActionListener(new ActionListener()
	    {  public void actionPerformed(ActionEvent arg0)
	    {  
	    	Gamer =JOptionPane.showInputDialog(null,"Nom Utilisateur");
	        if(baseDeDonnee.get(Gamer)==null)
	        {
	        	Gamer =JOptionPane.showInputDialog(null,"Reessyer");
	        }
	        else{play(baseDeDonnee.get(Gamer));}
	        repaintPanel();
	    }
	    });    
	    //cree le conteneur
				conteneur.setLayout(new BoxLayout(conteneur,BoxLayout.PAGE_AXIS));
				conteneur.add(pan);
				conteneur.add(pan2);
				conteneur.add(pan3);
	    this.getContentPane().add(conteneur);
	    this.setJMenuBar(Menu);
	    this.setVisible(true);
   }
   //class permet de verifier le click sur le boutton
   class bouttonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			 
//on verifie si le caractere exist
	
	if(mot.contains(((JButton)e.getSource()).getText()))
	{
		while(mot.contains(((JButton)e.getSource()).getText()))
		{int i= mot.indexOf(((JButton)e.getSource()).getText());
	 SecretMot= changeChar(SecretMot, i, mot.charAt(i));
	 mot=changeChar(mot, i, '*');}
		((JButton)e.getSource()).setEnabled(false);
	 //on verifie si le mot a ete trouver
	   if(SecretMot.equalsIgnoreCase(mots))
			   {
		   JOptionPane jop;
		int option = JOptionPane.showConfirmDialog(null,
					"Voulez-vous Continuer ?",
					"BRAVO",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		if(option != JOptionPane.NO_OPTION &&
				option != JOptionPane.CANCEL_OPTION &&
				option != JOptionPane.CLOSED_OPTION){
			int x=baseDeDonnee.get(Gamer).getScore();
			baseDeDonnee.get(Gamer).setScore(x+(70-nbr*10));
			int y=baseDeDonnee.get(Gamer).getNbreMactchG();
			baseDeDonnee.get(Gamer).setNbreMactchG(y+1);
			play(baseDeDonnee.get(Gamer));
		}
		else
		{
			System.exit(0);
		}
			   }
	
	}
	else
	{
		pendre=true;
		
	}
	//repaindre le panel
	 repaintPanel();
		}
   }
   //fonction qui repaint un panel
   public void repaintPanel()
   {
	   //revalider le panel apres chaque nouvelle parti
      
       pan.removeAll();
       pan2.removeAll();
       pan.revalidate();
       pan2.revalidate();
       MotSecret=new JLabel("");
       MotSecret=new JLabel(SecretMot);
			//repaindre le premier panel
			pan.setLayout(new BoxLayout(pan,BoxLayout.PAGE_AXIS));
			 userG.setFont(new Font("Tahoma", Font.BOLD, 20));
		     Score.setFont(new Font("Tahoma", Font.BOLD, 20));
		     MotSecret.setFont(new Font("Tahoma", Font.BOLD, 70));
		     MotSecret.setForeground(Color.blue);
		     MotTrouver.setFont(new Font("Tahoma", Font.BOLD, 20));
			pan.add(userG);
			pan.add(Score);
			pan.add(MotTrouver);
			pan.add(MotSecret);
			//repaindre le deuxeme panel
			pan2.setLayout(new GridLayout(5, 4));
			
			//ranger les boutton
			for(int j=0;j<26;j++)
			{char n='a';
			  n=(char) (n+j);
			 bot[j]=new JButton(n+"");
			 bot[j].setBackground(Color.ORANGE);
			 bot[j].setFont(new Font("Tahoma", Font.BOLD, 20));
			 bot[j].addActionListener(new bouttonListener());
			 pan2.add(bot[j]);
			}
			
	 if(pendre==true)
	 {pan3.removeAll();
	 pan3.revalidate();
		nbr++;
		
		 if(nbr<7)
		 {   pan3.add(new JLabel(new ImageIcon("D:\\ProjetJava\\Jeux de Pendu\\Pondu\\"+nbr+".png")));
		 }
		else{
			int x=baseDeDonnee.get(Gamer).getScore();
			baseDeDonnee.get(Gamer).setScore(x+70-nbr*10);
			pan.removeAll();
			pan3.removeAll();
			pan2.removeAll();
			pan.revalidate();
			JLabel  label=new JLabel("Scores");
			label.setFont(new Font("Tahoma", Font.BOLD, 50));
			label.setForeground(Color.black);
			pan.add(label);
			for(Gamer g:baseDeDonnee.values())
			{   
				pan.add( new JLabel(g.getName()+" : "+g.getScore()+"      Match Gagner :      "+g.getNbreMactchG()));
			}
			
		} 
		 
		 pendre=false;
		}
		 
		 
	 }
	   
   
   //play
   public void play(Gamer g)
   {   
	   nbr=-1;
       SecretMot="";
       userG=new JLabel("Gamer : "+g.getName());
       Score=new JLabel("Score : "+g.getScore());
       MotTrouver=new JLabel("Nombre de mot trouvee: "+g.getNbreMactchG());
       //lance la recherche du nombre aleatoire
       int nbre = (int)(Math.random()*336529);
       //chercher un mot dans un dictionnaire
       
           
           int i = 0;
           BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("D:\\ProjetJava\\Jeux de Pendu\\Pondu\\dictionnaire.txt"));
				
           	try {
           		while(i<nbre)
                  {
					mot= reader.readLine();
					i++;
                   }
           		mots=mots.replace(mots, mot);
           		System.out.println("le nombre : "+nbre+"\n le mot :"+mot);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
           	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//afficher le panel
			//transformer le mot en caractere
   		for(int k=0;k<mot.length();k++)
   		{
   			SecretMot=SecretMot+"*";
   		}
   		
   }
   //changer un car par un autre
   public String changeChar(String chaine, int idx, char monCharRempl) {
	   char[] tab = chaine.toCharArray();
	   tab[idx] = monCharRempl;
	   return String.valueOf(tab);
	 }
}
