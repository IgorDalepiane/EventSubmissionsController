package submissao.categorias;

import submissao.SubmissaoApresentacao;

public class Palestra extends SubmissaoApresentacao {
    private String curriculo;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Currículo: ")
                .append(curriculo);
        return result.toString();
    }
}
