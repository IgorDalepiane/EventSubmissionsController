package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import submissao.Submissao;
import submissao.SubmissaoApresentacao;
import submissao.SubmissaoCientifica;
import submissao.categorias.Palestra;
import submissao.embeddables.Autor;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Submissao.class)
                    .addAnnotatedClass(SubmissaoApresentacao.class)
                    .addAnnotatedClass(SubmissaoCientifica.class)
                    .addAnnotatedClass(Autor.class)
                    .addAnnotatedClass(Palestra.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}