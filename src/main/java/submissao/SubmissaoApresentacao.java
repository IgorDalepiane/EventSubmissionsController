package submissao;
import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SubmissaoApresentacao extends Submissao {
    private String resumo;

    @Column(name = "abstract")
    private String _abstract;

    private int duracao; // minutos

    public SubmissaoApresentacao(int max) {
        super(max);
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {

        return super.toString() + "\n\nResumo: " +
                resumo +
                "\n\nAbstract: " +
                _abstract +
                "\n\nDuração: " +
                duracao +
                "min";

    }
}