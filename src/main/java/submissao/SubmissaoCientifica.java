package submissao;

public abstract class SubmissaoCientifica extends Submissao {
    protected String[] instituicao;
    protected String[] palavraChave;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append( "Instituição: ");
        for (String i :
                instituicao) {
            //é o último
            if (i.equals(instituicao[instituicao.length - 1]))
                result.append(i);
            else {
                result.append(i).append(", ");
            }
        }
        result.append( " Palavras-chave: ");
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
