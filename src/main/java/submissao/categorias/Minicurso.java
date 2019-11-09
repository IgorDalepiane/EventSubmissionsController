package submissao.categorias;

import submissao.SubmissaoApresentacao;

public class Minicurso extends SubmissaoApresentacao {
    private String recursos;
    private String metodologia;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Recursos: ")
                .append(recursos)
                .append("Metodologia: ")
                .append(metodologia);
        return result.toString();
    }
}
