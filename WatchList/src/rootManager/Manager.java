package rootManager;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Manager {
	private static Manager instance; // on crée une instance pour le manager
    private static Stage stage; 
    private static Stage modalStage;

	private static final Page PAGE = new Page(); //initialisation de la class Page
	private static final Modal MODAL = new Modal();
	private static final Theme THEME = new Theme();
	
	private static Parent root;
	
	private static final PageType DEFAULT = PageType.HOME;
	private static final ThemeList DEFAULT_THEME = ThemeList.LIGHT;
	
	private static final int HEIGHT_PREF = 500;
	private static final int WIDTH_PREF = 800;
	
	private PageType current; // type de la page en cours
	private ThemeList currentT;
	
	public static void init(Stage primaryStage) { // on initalise le root
        if (instance == null) {
            instance = new Manager(primaryStage);
        }
    }
	
	private Manager(Stage pr)
	{
		stage = pr;
		setPage(DEFAULT);
		if(root != null)
		{
			setTheme(DEFAULT_THEME);
		}
	}
	
	public void setPage(PageType PT) // changement de page
	{	
		try {
            String PATH = PAGE.getPath(PT); // recherche de la page
            if (PATH == null) {
                throw new IllegalArgumentException("Page inconnue: " + PT); // la page voulu n'existe pas
            }
            
            root = FXMLLoader.load(Manager.class.getResource(PATH)); // on itilisalise le rot
            
            stage.setResizable(false);
        	
            if (stage.getScene() == null) { // si null cela veut dire que la scene n'existe pas

            	Scene scene = new Scene(root, WIDTH_PREF, HEIGHT_PREF);

                stage.setScene(scene);
            } else { // on change de scene
                stage.getScene().setRoot(root);
            }

            if (instance != null) {
                instance.current = PT;
            }
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void openModal(ModalType MT, String title) throws IOException
	{
		try {
			String PATH = MODAL.getPath(MT);
	        if (PATH == null) {
	            throw new IllegalArgumentException("modal inconnue: " + MT);
	        }
	        root = FXMLLoader.load(Manager.class.getResource(PATH));
	        modalStage = new Stage();
	        modalStage.setTitle(title);
	        
	        modalStage.initOwner(stage);
	        modalStage.initModality(Modality.WINDOW_MODAL);
	        
	        modalStage.setScene(new Scene(root));
	        modalStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeModal()
	{
		modalStage.close();
	}
	
	
	public void setTheme(ThemeList th)
	{
		this.currentT = th;
		
		root.setStyle(THEME.getTheme(th));
	}
	
	
	public static Manager getInstance() {
        return instance;
    }
}
