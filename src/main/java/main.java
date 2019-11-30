
import utils.HibernateUtil;
import utils.InterfaceUtil;
import javafx.application.Application;
import org.hibernate.Session;
import submissao.Situacao;
import submissao.categorias.Palestra;
import submissao.embeddables.Autor;

import java.util.Arrays;
import java.util.List;

public class main {

    public static void main(String[] args) {
        HibernateUtil.buildSessionFactory();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Autor a = new Autor();
        a.setNome("Pedro");
        a.setUniversidade("Unipampa");

        Autor b = new Autor();
        b.setNome("Henrique");
        b.setUniversidade("UFRGS");

        Palestra p = new Palestra();
        //Submissao.class
        p.setTitulo("Titulo da palestraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, yay");
        p.setSituacao(Situacao.SOB_AVALIACAO);
        p.setAutores(Arrays.asList(b));
        p.setMaxAut(2);
        //SubmissaoApresentacao.class
        p.setResumo("Resumo da palestraaaaa");
        p.set_abstract("Abstract of the talk");
        p.setDuracao(120);
        //Palestra.class
        p.setCurriculo("Curriculo da palestraaiaiiaiia");

        session.save(a);
        session.save(b);
        session.save(p);

        session.getTransaction().commit();
        session.close();

        Application.launch(InterfaceUtil.class);
    }
}
