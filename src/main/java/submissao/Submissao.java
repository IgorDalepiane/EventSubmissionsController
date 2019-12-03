package submissao;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> autores = new ArrayList<>();

    private final int MAX_AUTORES;

    protected Submissao(int max) {
        MAX_AUTORES = max;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getAutores() {
        return autores;
    }

    public String getAutoresInline() {
        StringBuilder result = new StringBuilder();
        for (String a : autores)
            result.append(a);
        return result.toString();
    }

    public String getAutoresString() {
        StringBuilder result = new StringBuilder();
        for (String a : autores)
            //é o último
            if (a.equals(autores.get(autores.size() - 1)))
                result.append(a);
            else
                result.append(a).append(", ");
        return result.toString();
    }

    public void setAutores(List<String> autores) {
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

    public int getMAX_AUTORES() {
        return MAX_AUTORES;
    }

    //reflection para popular a table
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(this.getClass().getSimpleName());
    }

    public String toString() {
        return "Título: " +
                titulo +
                "\n\nSituação: " +
                situacao +
                "\n\nAutor(es): " +
                getAutoresString();
    }
}
