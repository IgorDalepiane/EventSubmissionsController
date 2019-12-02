
import submissao.Submissao;
import submissao.SubmissaoApresentacao;
import submissao.SubmissaoCientifica;
import submissao.categorias.*;
import utils.HibernateUtil;
import utils.InterfaceUtil;
import javafx.application.Application;
import org.hibernate.Session;
import submissao.Situacao;
import submissao.embeddables.Autor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

    public static void main(String[] args) {
        HibernateUtil.buildSessionFactory();

        Session sessionj = HibernateUtil.getSessionFactory().getCurrentSession();
        sessionj.beginTransaction();

        Submissao obj;

        obj = new Artigo();
        obj.setTitulo("artigo");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoCientifica) obj).setInstituicao(Arrays.asList("UFRGS", "Unichamps"));
        ((SubmissaoCientifica) obj).setPalavraChave(Arrays.asList("software", "web"));
        ((Artigo) obj).setResumo("Resumo do artigo");
        ((Artigo) obj).set_abstract("Abstract of the article");
        sessionj.save(obj);

        obj = new Minicurso();
        obj.setTitulo("artigo");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoApresentacao) obj).setResumo("Resumo da palestraaaaa");
        ((SubmissaoApresentacao) obj).set_abstract("Abstract of the talk");
        ((SubmissaoApresentacao) obj).setDuracao(120);
        ((Minicurso) obj).setRecursos("RECURSOS");
        ((Minicurso) obj).setMetodologia("METODOLOGIA");
        sessionj.save(obj);

        obj = new Palestra();
        obj.setTitulo("paleasretsd");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoApresentacao) obj).setResumo("Resumo da palestraaaaa");
        ((SubmissaoApresentacao) obj).set_abstract("Abstract of the talk");
        ((SubmissaoApresentacao) obj).setDuracao(120);
        ((Palestra) obj).setCurriculo("awdadadawd");
        sessionj.save(obj);

        obj = new Monografia();
        obj.setTitulo("mosdnfods");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoCientifica) obj).setInstituicao(Arrays.asList("UFRGS", "Unichamps"));
        ((SubmissaoCientifica) obj).setPalavraChave(Arrays.asList("software", "web"));
        ((Monografia) obj).setTipo(Tipo.TCC1);
        ((Monografia) obj).setOrientador("AWDAWD");
        ((Monografia) obj).setnPaginas(5);
        ((Monografia) obj).setCurso("ADAWDWADWADQQQQQ");
        ((Monografia) obj).setAno(12312);
        ((Monografia) obj).setResumo("AWDWAWAEEEDFDF");
        ((Monografia) obj).set_abstract("Abstract of the article");
        sessionj.save(obj);

        obj = new RelatorioTecnico();
        obj.setTitulo("rearawdwa");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoCientifica) obj).setInstituicao(Arrays.asList("UFRGS", "Unichamps"));
        ((SubmissaoCientifica) obj).setPalavraChave(Arrays.asList("software", "web"));
        ((RelatorioTecnico) obj).setAno(324);
        ((RelatorioTecnico) obj).setnPaginas(2324);
        ((RelatorioTecnico) obj).setResumo("awdqqqqqq");
        ((RelatorioTecnico) obj).set_abstract("Abstract of the article");
        sessionj.save(obj);

        obj = new Resumo();
        obj.setTitulo("resumo");
        obj.setSituacao(Situacao.APROVADO);
        obj.setAutores(Collections.singletonList("autorre"));
        ((SubmissaoCientifica) obj).setInstituicao(Arrays.asList("UFRGS", "Unichamps"));
        ((SubmissaoCientifica) obj).setPalavraChave(Arrays.asList("software", "web"));
        sessionj.save(obj);

        sessionj.getTransaction().commit();
        sessionj.close();

        Application.launch(InterfaceUtil.class);
    }
}
