package model;

import java.io.File;

import data.Media;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Preview extends VBox{
	private String Title;
	private String Desc;
	private String Score;
	private String PosterUri;
	private String year;
	
	private int witdh;
	private int height;
	public Preview(Media m, int width, int height) {
		super();
		Title = m.getTitle();
		Desc = m.getDesc();
		Score = m.getScore();
		PosterUri = m.getPosterUri();
		this.year = m.getYear();
		this.witdh = width;
		this.height = height;
		
		
		ImageView image = new ImageView();
		try {
			image.setImage(new Image(this.PosterUri.contains("http") ? this.PosterUri : new File(this.PosterUri).toURI().toString(), true));			
			image.setFitWidth(width);
			image.setFitHeight(height);
			image.setPreserveRatio(true);
		}catch (Exception e) {
			System.out.println("Erreur lors du chargement de l'image: " + e);
		}
		
		Label text = new Label(this.Title + " ( " + year + " )");
		text.setFont(Font.font("System", FontWeight.BOLD, 14));
		Label texyDesc = new Label(this.Desc);
		text.setFont(Font.font("System", FontWeight.NORMAL, 12));
		
		
		this.setPrefWidth(this.witdh);
		this.setPrefHeight(this.height);
		this.setMaxHeight(this.height);
		this.setMaxWidth(this.witdh);
		
		this.getChildren().add(image);
		this.getChildren().add(text);
		this.getChildren().add(texyDesc);

		
	}

}
