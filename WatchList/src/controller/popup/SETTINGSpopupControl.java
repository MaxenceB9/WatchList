package controller.popup;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import rootManager.Manager;
import rootManager.ModalType;

public class SETTINGSpopupControl {

	
	@FXML
	private FlowPane content;
	
	@FXML
	private Button ApiButton, General;
	
	public SETTINGSpopupControl() {
		
	}
	
	
	
	@FXML
	public void GeneralContent()
	{
		
	}
	
	
	@FXML
	public void ApiContent()
	{
		try {
			Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
