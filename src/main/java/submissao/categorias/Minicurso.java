package submissao.categorias;

import submissao.SubmissaoApresentacao;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Minicurso extends SubmissaoApresentacao {
    private String recursos;
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
        StringBuilder result = new StringBuilder(super.toString());
        result.append("\n\nRecursos: ")
                .append(recursos)
                .append("\n\nMetodologia: ")
                .append(metodologia);
        return result.toString();
    }
}
