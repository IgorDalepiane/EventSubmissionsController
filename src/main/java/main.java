import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import submissao.SubmissaoCientifica;
import submissao.embeddables.Autor;

import java.util.Date;
import java.util.List;

public class main {
    public static void main(String[] args) {
        SessionFactory sessionFactory=null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Autor( "OI"));
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Autor" ).list();
        for ( Autor event : (List<Autor>) result ) {
            System.out.println( "Autor (" + event.getNome() + ") : " + event.getId() );
        }
        session.getTransaction().commit();
        session.close();


    }
}
