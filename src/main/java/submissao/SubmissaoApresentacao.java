package submissao;

public abstract class SubmissaoApresentacao extends Submissao{
    private String resumo;
    private String _abstract;
    private int duracao;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Resumo: ")
                .append(resumo)
                .append(" Abstract: ")
                .append(_abstract)
                .append(" Duração: ")
                .append(duracao)
                .append("min");

        return result.toString();
    }
}
