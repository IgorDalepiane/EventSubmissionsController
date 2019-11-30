package submissao;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.GenericGenerator;
import submissao.embeddables.Autor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Submissao {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String titulo;

    private Situacao situacao;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Autor> autores = new ArrayList<>();

    private int maxAut;

    public Submissao() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public int getMaxAut() {
        return maxAut;
    }

    public void setMaxAut(int maxAut) {
        this.maxAut = maxAut;
    }

    //reflection para popular a table
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(this.getClass().getSimpleName());
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Título: ")
                .append(titulo)
                .append(" Situação: ")
                .append(situacao)
                .append(" Autores: ");
        for (Autor autor :
                autores) {
            //é o último
            if (autor.equals(autores.get(autores.size() - 1)))
                result.append(autor.getNome());
            else {
                result.append(autor.getNome()).append(", ");
            }
        }
        result.append(" Max autores: ")
                .append(maxAut);

        return result.toString();
    }
}
