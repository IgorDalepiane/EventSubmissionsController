import javafx.application.Application;
import utils.HibernateUtil;
import utils.InterfaceUtil;

class main {

    public static void main(String[] args) {
        HibernateUtil.buildSessionFactory();
        Application.launch(InterfaceUtil.class);
    }
}
