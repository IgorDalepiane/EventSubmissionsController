package submissao.categorias;

import submissao.SubmissaoApresentacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Minicurso extends SubmissaoApresentacao {

    @Column(columnDefinition = "TEXT")
    private String recursos;
    @Column(columnDefinition = "TEXT")
    private String metodologia;

    public Minicurso() {
        super(3);
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    @Override
    public String toString() {
        return super.toString() + "\n\nRecursos: " +
                recursos +
                "\n\nMetodologia: " +
                metodologia;
    }
}