package submissao.categorias;

import submissao.SubmissaoApresentacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Palestra extends SubmissaoApresentacao {
    @Column(columnDefinition = "TEXT")
    private String curriculo;

    public Palestra() {
        super(1);
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n\nCurrículo: "
                + curriculo;

    }
}
