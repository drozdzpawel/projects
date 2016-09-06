package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")

public class RenderPanel extends JPanel
{

public static final Color GREEN = new Color(1666073);

@Override
protected void paintComponent(Graphics g)
{
super.paintComponent(g);

snake waz = snake.snake;

g.setColor(Color.blue);

g.fillRect(0, 0, 800, 700);

g.setColor(Color.ORANGE);

for (Point point : waz.kawweza)
{
g.fillRect(point.x * snake.skala, point.y * snake.skala, snake.skala, snake.skala);
}

g.fillRect(waz.glowa.x * snake.skala, waz.glowa.y * snake.skala, snake.skala, snake.skala);

g.setColor(Color.RED);

g.fillRect(waz.lacznik.x * snake.skala, waz.lacznik.y * snake.skala, snake.skala, snake.skala);

String string = "Wynik: " + waz.wynik + ", D³ugoœæ: " + waz.ogon ;

g.setColor(Color.pink);

g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 650);

string = "NIESTETY PRZEGRA£EŒ";

if (waz.straznik)
{
g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) waz.wymiar.getHeight() / 4);
}

string = "Przerwa!";

if (waz.przerwa && !waz.straznik)
{
g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) waz.wymiar.getHeight() / 4);
}
}
}