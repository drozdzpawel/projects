package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class snake implements ActionListener, KeyListener
{

public static snake snake;

public JFrame jframe;

public RenderPanel renderPanel;

public Timer czas = new Timer(100, this);

public ArrayList<Point> kawweza = new ArrayList<Point>();

public static final int gora = 0, dol = 1, lewo = 2, prawo = 3, skala = 10;

public int tik = 0, ruch = dol, wynik, ogon = 10;

public Point glowa, lacznik;

public Random genLosu;

public boolean straznik = false, przerwa;

public Dimension wymiar;

public snake()
{
wymiar = Toolkit.getDefaultToolkit().getScreenSize();
jframe = new JFrame("Snake");
jframe.setVisible(true);
jframe.setSize(805, 700);
jframe.setResizable(false);
jframe.setLocation(wymiar.width / 2 - jframe.getWidth() / 2, wymiar.height / 2 - jframe.getHeight() / 2);
jframe.add(renderPanel = new RenderPanel());
jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jframe.addKeyListener(this);
start();
}

public void start()
{
straznik = false;
przerwa = false;
wynik = 0;
ogon = 14;
tik = 0;
ruch = dol;
glowa = new Point(0, -1);
genLosu = new Random();
kawweza.clear();
lacznik = new Point(genLosu.nextInt(79), genLosu.nextInt(66));
czas.start();
}

@Override
public void actionPerformed(ActionEvent arg0)
{
renderPanel.repaint();
tik++;

if (tik % 2 == 0 && glowa != null && !straznik && !przerwa)
{

kawweza.add(new Point(glowa.x, glowa.y));

if (ruch == gora)
{
if (glowa.y - 1 >= 0 && strazOgon(glowa.x, glowa.y - 1))
{
glowa = new Point(glowa.x, glowa.y - 1);
}
else
{
straznik = true;

}
}

if (ruch == dol)
{
if (glowa.y + 1 < 67 && strazOgon(glowa.x, glowa.y + 1))
{
glowa = new Point(glowa.x, glowa.y + 1);
}
else
{
straznik = true;
}
}

if (ruch == lewo)
{
if (glowa.x - 1 >= 0 && strazOgon(glowa.x - 1, glowa.y))
{
glowa = new Point(glowa.x - 1, glowa.y);
}
else
{
straznik = true;
}
}

if (ruch == prawo)
{
if (glowa.x + 1 < 80 && strazOgon(glowa.x + 1, glowa.y))
{
glowa = new Point(glowa.x + 1, glowa.y);
}
else
{
straznik = true;
}
}

if (kawweza.size() > ogon)
{
kawweza.remove(0);

}

if (lacznik != null)
{
if (glowa.equals(lacznik))
{
wynik += 10;
ogon++;
lacznik.setLocation(genLosu.nextInt(79), genLosu.nextInt(66));
}
}
}
}

public boolean strazOgon(int x, int y)
{
for (Point punkt : kawweza)
{
if (punkt.equals(new Point(x, y)))
{
return false;
}
}
return true;
}

public static void main(String[] args)
{
snake = new snake();
}

@Override
public void keyPressed(KeyEvent e)
{
int i = e.getKeyCode();

if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && ruch != prawo)
{
ruch = lewo;
}

if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && ruch != lewo)
{
ruch = prawo;
}

if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && ruch != dol)
{
ruch = gora;
}

if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && ruch != gora)
{
ruch = dol;
}

if (i == KeyEvent.VK_SPACE)
{
if (straznik)
{
start();
}
else
{
przerwa = !przerwa;
}
}
}

@Override
public void keyReleased(KeyEvent e)
{
}

@Override
public void keyTyped(KeyEvent e)
{
}

}