package submissao.categorias;

import submissao.SubmissaoApresentacao;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Palestra extends SubmissaoApresentacao {
    private String curriculo;

    public Palestra() {
        super();
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Currículo: ")
                .append(curriculo);
        return result.toString();
    }
}
