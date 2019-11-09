package submissao.embeddables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Embeddable
public class Autor {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;
    private String nome;

    public Autor(String nome) {
        this.nome = nome;
    }

    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
