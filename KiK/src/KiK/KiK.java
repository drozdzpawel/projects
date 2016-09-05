package KiK;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class KiK extends JFrame 
{
   private int x, y, czyj_ruch=1;
   int[] plansza={0,0,0,0,0,0,0,0,0};
   private ArrayList<Ellipse2D> pionek=new ArrayList<>();
   
   
   public KiK()
   {
       setSize(500,500);
       setBackground(Color.getHSBColor(205, 235, 167));
       
       Grafika graf= new Grafika();
       add(graf);
       
       Ruch mysz= new Ruch();
       addMouseListener(mysz);
       
       
   }

  
   public static void main(String[] args) 
   {
       EventQueue.invokeLater(new Runnable() {

           @Override
           public void run()
           {
                KiK kik= new KiK();
                kik.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                kik.setVisible(true);
           }
       });
      
       
   }
   
   class Grafika extends JPanel
   {
      
      
         
       public void paintComponent(Graphics g)
       {
           Graphics2D g2= (Graphics2D)g;
           g2.setColor(Color.red);
           g2.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
           g2.drawString("KÓ£KO i KRZY¯YK", 160, 40);
           g2.drawString("POWODZENIA ! ! !", 160, 65);
           g2.setColor(Color.magenta);
           g2.drawLine(100, 200, 400, 200);
           g2.drawLine(100, 300, 400, 300);
           g2.drawLine(200, 100, 200, 400);
           g2.drawLine(300, 100, 300, 400);
   
           int i=0;
           
           for(Ellipse2D el:pionek)
           {
               pokoloruj_pionek(el,i,g2);
               
               i++;
           }
           
       }
       
       public void pokoloruj_pionek(Ellipse2D el,int i, Graphics2D g2)
       {
           if(i==0 || i==2||i==4||i==6||i==8)
           {
               g2.setColor(Color.blue);
               
           }
           else
               g2.setColor(Color.orange);
               g2.fill(el);
       }
       
       
       
       
       
       
   }
   
   class Ruch extends MouseAdapter
   {

       @Override
       public void mouseClicked(MouseEvent e) 
       {
           x=e.getX();
           y=e.getY();
           
           
          if( sprawdzanieRuchu(x, y))
          {
              
              zmienKolor( x, y);
              repaint();
              sprawdzZwyciezce(x, y);
              
          }
          
           
           
           
       }
       
       public boolean sprawdzanieRuchu(int x, int y)
       {
           if((x>108 && x<408)&&(y>131 && y<431))
           {
               if(x<208&&y<231)
               {
                 if(plansza[0]!=0)
                 {
                     return false;
                 }
                 else
                 {
                      if(czyj_ruch==1)
                           {
                                plansza[0]=czyj_ruch;
                               
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[0]=czyj_ruch;
                                 
                                 czyj_ruch--;
                           }
                     return true;
                 }
               }
               else if(x>208&&x<308&&y<231)
               {
                  if(plansza[1]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[1]=czyj_ruch;
                                
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[1]=czyj_ruch;
                                
                                 czyj_ruch--;
                           }
                     return true;
                 } 
               }
               else if(x>308&&y<231)
               {
                   if(plansza[2]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[2]=czyj_ruch;
                                
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[2]=czyj_ruch;
                                 
                                 czyj_ruch--;
                           }
                     return true;
                 }
               }
               else if(x<208&&y<331&&y>231)
               {
                   if(plansza[3]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[3]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[3]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 }
                  
               }
               else if(x>208&&x<308&&y<331&&y>231)
               {
                if(plansza[4]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[4]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[4]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 }   
               }
               else if(x>308&&y<331&&y>231)
               {
                   if(plansza[5]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[5]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[5]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 }
               }
               else if(x<208&&y>331)
               {
                   if(plansza[6]!=0)
                 {
                     return false;
                 }
                 else
                 {if(czyj_ruch==1)
                           {
                                plansza[6]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[6]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 }
               }
               else if(x>208&&x<308&& y>331)
               {
                  if(plansza[7]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[7]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[7]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 } 
                   
               }
               else if(x>308&& y>331)
               {
                if(plansza[8]!=0)
                 {
                     return false;
                 }
                 else
                 {
                     if(czyj_ruch==1)
                           {
                                plansza[8]=czyj_ruch;
                                czyj_ruch++;
                           }
                    else
                           {
                                 plansza[8]=czyj_ruch;
                                 czyj_ruch--;
                           }
                     return true;
                 }   
               }
                   
                   
               return true;
           }
           else
           {
               JOptionPane.showMessageDialog(null, "ruch poza obszarem gry");
           
           return false;
           }        
       }
       
       public void sprawdzZwyciezce(int x, int y)
       {int k;
       String gr;
           if(czyj_ruch==2)
           {
               k=1; 
               gr="niebieski";
           }
           else
           {
               k=2;
               gr="pomarañczowy";
           }
           
           if((plansza[0]==k&&plansza[1]==k&&plansza[2]==k)||
              (plansza[3]==k&&plansza[4]==k&&plansza[5]==k)||
              (plansza[6]==k&&plansza[7]==k&&plansza[8]==k)||
              (plansza[0]==k&&plansza[3]==k&&plansza[6]==k)||
              (plansza[1]==k&&plansza[4]==k&&plansza[7]==k)||
              (plansza[2]==k&&plansza[5]==k&&plansza[8]==k)||    
              (plansza[0]==k&&plansza[4]==k&&plansza[8]==k)||
              (plansza[6]==k&&plansza[4]==k&&plansza[2]==k) )
                   
           {
               JOptionPane.showMessageDialog(null, "wygra³ gracz "+gr);
               System.exit(0);
           }
           int remis=0;
           for(int i:plansza)
           {
               if(i==0)
               {
                   break;
               }
               else
               {
                   remis++;
                   if(remis==9)
                   {
                      JOptionPane.showMessageDialog(null, "remis ");
               System.exit(0); 
                   }
                   
               }
                   
                   
           }
          
           
           
           
       }
       
       public void zmienKolor(int x, int y)
       {
           pionek.add( new Ellipse2D.Double(x-45, y-70, 70, 70));
       }
       
       
       
   }
   
}