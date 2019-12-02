package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import submissao.Submissao;
import submissao.SubmissaoApresentacao;
import submissao.SubmissaoCientifica;
import submissao.categorias.*;
import submissao.embeddables.Autor;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void buildSessionFactory() {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Submissao.class)
                    .addAnnotatedClass(SubmissaoApresentacao.class)
                    .addAnnotatedClass(SubmissaoCientifica.class)
                    .addAnnotatedClass(Autor.class)
                    .addAnnotatedClass(Palestra.class)
                    .addAnnotatedClass(Artigo.class)
                    .addAnnotatedClass(Resumo.class)
                    .addAnnotatedClass(Minicurso.class)
                    .addAnnotatedClass(Monografia.class)
                    .addAnnotatedClass(RelatorioTecnico.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}