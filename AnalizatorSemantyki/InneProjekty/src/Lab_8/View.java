package Lab_8;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import com.mashape.unirest.http.exceptions.UnirestException;

import Lab_4.Lab4;
import Lab_7.Lab_7;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View extends Application {

	int counter = 10;
	int a = 0;
	Label cleaner = new Label("");

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Semantic Web");
		primaryStage.setWidth(800);
		primaryStage.setHeight(700);
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setStyle("-fx-background-color: #DAE6F3");

		Text lab1 = new Text("Analizator semantyku ");
		lab1.setTextAlignment(TextAlignment.CENTER);
		lab1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(lab1, 0, 0);

		Label labelad = new Label("Podaj adres URL:");
		labelad.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(labelad, 0, 2);

		TextArea dlugoscadresu = new TextArea();
		dlugoscadresu.setMaxHeight(5);
		dlugoscadresu.setMinWidth(200);
		grid.add(dlugoscadresu, 0, 3, 4, 1);

		Label labeldl = new Label("Podaj dlugosc slow:");
		labeldl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(labeldl, 0, 4);

		TextArea dlugosc = new TextArea();
		dlugosc.setMaxHeight(5);
		dlugosc.setMaxWidth(50);
		grid.add(dlugosc, 0, 5);

		Label labelil = new Label("Podaj ilosc poszukiwanych slów:");
		labelil.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(labelil, 0, 7);

		TextArea ilosc = new TextArea();
		ilosc.setMaxHeight(5);
		ilosc.setMaxWidth(50);
		grid.add(ilosc, 0, 8);

		Button results = new Button("Pokaz wyniki");
		results.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(results, 0, 9);

		Label lpoz = new Label("Wyniki Pozytywne:");
		lpoz.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(lpoz, 0, 11);

		TextArea poz = new TextArea();
		poz.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		poz.setMinWidth(50);
		poz.setMinHeight(100);
		grid.add(poz, 0, 12);

		Label lneg = new Label("Wyniki Negatywne:");
		lneg.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(lneg, 1, 11);

		TextArea neg = new TextArea();
		neg.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		neg.setMinWidth(50);
		neg.setMinHeight(100);
		grid.add(neg, 1, 12);

		Button resa = new Button("Pokaz wyniki dla calosci");
		resa.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(resa, 0, 14);

		Label wyn = new Label("Wyniki dla calosci:");
		wyn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(wyn, 0, 15);

		TextArea wynikda = new TextArea();
		wynikda.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		wynikda.setMinWidth(30);
		wynikda.setMaxHeight(10);
		grid.add(wynikda, 0, 16);

		results.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				Lab_8.URL = dlugoscadresu.getText();
				int d = Integer.parseInt(dlugosc.getText());
				int i = Integer.parseInt(ilosc.getText());

				try {
					Lab_8.badacz(d, i);
				} catch (UnirestException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Label logresults = new Label("Juz prawie koniec...");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 1);

				int con = 0;
				String text = "";
				for (String str : Lab_8.PositivWord) {

					text = text + (con + 1) + " " + str + "   " + Lab_8.PositivVal[con] + "\n";
					con++;
				}
				poz.setText(text);
				text = "";
				con = 0;
				for (String str : Lab_8.NegativWord) {
					text = text + (con + 1) + " " + str + "   " + Lab_8.NegativVal[con] + "\n";
					con++;
				}

				neg.setText(text);

				Label logresultss = new Label("Pokazano wyniki");
				logresultss.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresultss, 8, 1);

			}

		});

		resa.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				String t = Lab_8.calwyn;
				wynikda.setText(t);
				Label logresultss = new Label("Pokazano wyniki dla calosci");
				logresultss.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresultss, 8, 1);

			}

		});

		Scene scene = new Scene(grid, 400, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
