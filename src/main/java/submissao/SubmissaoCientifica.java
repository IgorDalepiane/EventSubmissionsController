package submissao;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SubmissaoCientifica extends Submissao {
    protected String[] instituicao;
    protected String[] palavraChave;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("\n\nInstituição: ");
        for (String i :
                instituicao) {
            //é o último
            if (i.equals(instituicao[instituicao.length - 1]))
                result.append(i);
            else {
                result.append(i).append(", ");
            }
        }
        result.append("\n\nPalavras-chave: ");
        for (String p :
                palavraChave) {
            //é o último
            if (p.equals(palavraChave[palavraChave.length - 1]))
                result.append(p);
            else {
                result.append(p).append(", ");
            }
        }

        return result.toString();
    }
}
