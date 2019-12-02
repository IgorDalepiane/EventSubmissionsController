package submissao;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SubmissaoCientifica extends Submissao {

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> instituicao;

    private final int MAX_INSTITUICOES;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> palavraChave;


    private final int MAX_PALAVRASCHAVE;

    public SubmissaoCientifica(int aut, int inst, int chaves) {
        super(aut);
        MAX_INSTITUICOES = inst;
        MAX_PALAVRASCHAVE = chaves;
    }

    public int getMAX_INSTITUICOES() {
        return MAX_INSTITUICOES;
    }

    public int getMAX_PALAVRASCHAVE() {
        return MAX_PALAVRASCHAVE;
    }

    public List<String> getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(List<String> instituicao) {
        this.instituicao = instituicao;
    }

    public List<String> getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(List<String> palavraChave) {
        this.palavraChave = palavraChave;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("\n\nInstituição: ");
        for (String i : instituicao)
            //é o último
            if (i.equals(instituicao.get(instituicao.size() - 1)))
                result.append(i);
            else
                result.append(i).append(", ");

        result.append("\n\nPalavras-chave: ");
        for (String p : palavraChave)
            //é o último
            if (p.equals(palavraChave.get(palavraChave.size() - 1)))
                result.append(p);
            else
                result.append(p).append(", ");
        return result.toString();
    }
}
