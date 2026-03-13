package rootManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Manager {
	private static Manager instance; // on crée une instance pour le manager
    private static Stage stage; 
    
	private static final Page PAGE = new Page(); //initialisation de la class Page
	private static final PageType DEFAULT = PageType.HOME;
	private static final int HEIGHT_PREF = 500;
	private static final int WIDTH_PREF = 800;
	
	private PageType current; // type de la page en coyrs

	public static void init(Stage primaryStage) { // on initalise le root
        if (instance == null) {
            instance = new Manager(primaryStage);
        }
    }
	
	private Manager(Stage pr)
	{
		stage = pr;
		setPage(DEFAULT);
	}
	
	public static void setPage(PageType PT) // changement de page
	{	
		try {
            String PATH = PAGE.getPath(PT); // recherche de la page
            if (PATH == null) {
                throw new IllegalArgumentException("Page inconnue: " + PT); // la page voulu n'existe pas
            }
            
            Parent root = FXMLLoader.load(Manager.class.getResource(PATH)); // on itilisalise le rot
            
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
	
	public static Manager getInstance() {
        return instance;
    }
}
