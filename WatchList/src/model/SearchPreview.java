package model;

import api.Media;
import api.MediaList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import rootManager.Manager;
import rootManager.PageType;

public class SearchPreview extends HBox {
	private String Title;
	private String Desc;
	private String Score;
	private String PosterUri;
	private String year;
	
	private int witdh;
	private int height;
	public SearchPreview(Media m, int witdh,int height) {
		super();
		Title = m.getTitle();
		Desc = m.getDesc();
		Score = m.getScore();
		PosterUri = m.getPosterUri();
		this.year = m.getYear();
		this.witdh = witdh;
		this.height = height;
		
		VBox TextContent = new VBox();
		
		ImageView image = new ImageView();
		try {
			image.setImage(new Image(this.PosterUri, true));
			image.setFitWidth(this.witdh / 2);
			image.setFitHeight(height);
			image.setPreserveRatio(true);
		}catch (Exception e) {
			System.out.println("Erreur lors du chargement de l'image: " + e);
		}
				
		Label text = new Label(this.Title + " (" + this.year + ")");
		text.setFont(Font.font("System", FontWeight.BOLD, 18));
		
		Label txtDesc = new Label(this.Desc);
		txtDesc.setFont(Font.font("System", FontWeight.NORMAL, 14));

		
		Button addButton = new Button((!MediaList.getInstance().getMyMedia().contains(m)) ? "Add" : "Already add");
		addButton.setPrefWidth(witdh / 2);
		addButton.setPrefHeight(20);
		addButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(!MediaList.getInstance().getMyMedia().contains(m))
				{
					MediaList.getInstance().addMyMedia(m);	
					addButton.setText("Added");
				}
			}
		});
		
		
		this.setPrefWidth(this.witdh);
		this.setPrefHeight(this.height);
		this.setMaxHeight(this.height);
		this.setMaxWidth(this.witdh);

		Region spacer = new Region();
		TextContent.setVgrow(spacer, Priority.ALWAYS);
		
		TextContent.getChildren().addAll(text, txtDesc, spacer, addButton);
		
		
		this.getChildren().add(image);
		this.setMargin(image, new Insets(0, 10, 0, 0));
		
		this.getChildren().add(TextContent);
		
	}
	

}
