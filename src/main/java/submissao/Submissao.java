package submissao;

import org.hibernate.annotations.GenericGenerator;
import submissao.embeddables.Autor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Submissao")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Submissao {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;
    private String tituloSubmissao;

    private Situacao situacaoSubmissao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Autor> autoresSubmissao=new ArrayList<>();;
    private int maxAut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Autor> getAutoresSubmissao() {
        return autoresSubmissao;
    }

    public void setAutoresSubmissao(List<Autor> autoresSubmissao) {
        this.autoresSubmissao = autoresSubmissao;
    }

    public String getTituloSubmissao() {
        return tituloSubmissao;
    }

    public void setTituloSubmissao(String tituloSubmissao) {
        this.tituloSubmissao = tituloSubmissao;
    }

    public Situacao getSituacaoSubmissao() {
        return situacaoSubmissao;
    }

    public void setSituacaoSubmissao(Situacao situacaoSubmissao) {
        this.situacaoSubmissao = situacaoSubmissao;
    }

    public int getMaxAut() {
        return maxAut;
    }

    public void setMaxAut(int maxAut) {
        this.maxAut = maxAut;
    }
    //TODO
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//        result.append("Título: ")
//                .append(tituloSubmissao)
//                .append(" Situação: ")
//                .append(situacaoSubmissao)
//                .append(" Autores: ");
//        for (String a :
//                autoresSubmissao) {
//            //é o último
//            if (a.equals(autoresSubmissao[autoresSubmissao.length - 1]))
//                result.append(a);
//            else {
//                result.append(a).append(", ");
//            }
//        }
//        result.append(" Max autores: ")
//                .append(maxAut);
//
//        return result.toString();
//    }
}
