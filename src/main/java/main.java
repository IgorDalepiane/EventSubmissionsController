
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

        for (int i = 0; i < 2; i++) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Autor a = new Autor();
            a.setNome("nomeA" + i);
            a.setUniversidade("uniA" + i);

            Autor b = new Autor();
            b.setNome("nomeB" + i);
            b.setUniversidade("uniB" + i);

            session.save(a);
            session.save(b);

            session.getTransaction().commit();
            session.close();

            for (int j = 0; j < 2; j++) {
                Session sessionj = HibernateUtil.getSessionFactory().getCurrentSession();
                sessionj.beginTransaction();

                Palestra p = new Palestra();
                //Submissao.class
                p.setTitulo("titulo" + i + "_" + j);
                p.setSituacao(Situacao.SOB_AVALIACAO);
                p.setAutores(Arrays.asList(a, b));
                p.setMaxAut(2);
                //SubmissaoApresentacao.class
                p.setResumo("Resumo da palestraaaaa" + i + "_" + j);
                p.set_abstract("Abstract of the talk" + i + "_" + j);
                p.setDuracao(120);
                //Palestra.class
                p.setCurriculo("Curriculo da palestraaiaiiaiia" + i + "_" + j);
                sessionj.save(p);

                sessionj.getTransaction().commit();
                sessionj.close();
            }


        }
        Application.launch(InterfaceUtil.class);
    }
}
