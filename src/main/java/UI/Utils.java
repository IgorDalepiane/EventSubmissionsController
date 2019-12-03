package UI;

import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.CustomTextField;
import submissao.Situacao;
import submissao.Submissao;
import submissao.SubmissaoApresentacao;
import submissao.SubmissaoCientifica;
import submissao.categorias.*;
import utils.InterfaceUtil;

import java.time.Year;

class Utils {
    //formulário de inserção / update das submissões
    static String form(
            Submissao subGenerica,
            TextField textFieldTitulo,
            ChoiceBox<Situacao> choiceBoxSituacao,
            CheckListView<String> autores,
            TextArea textAreaResumo,
            TextArea textAreaAbstract,
            Slider sliderDuracao,
            TextArea spRecursos,
            TextArea spMetodologia,
            TextField spCurriculo,
            CheckListView<String> instituicoes,
            CheckListView<String> palavraschave,
            TextArea spResumo,
            TextArea spAbstract,
            ChoiceBox<Tipo> spTipo,
            TextField spOrientador,
            TextField spCurso,
            TextField spAno,
            TextField spNumPags) {
        String errorMsg = "";
        int maxChars = (int) Math.pow(2, 16) - 1;
        //Submissao.class
        if (textFieldTitulo.getText().length() < 1)
            errorMsg += "\nTítulo não pode ser vazio.";
        else if (textFieldTitulo.getText().length() > 255)
            errorMsg += "\nTítulo muito grande. Max 255 caracteres.";
        else
            subGenerica.setTitulo(textFieldTitulo.getText());
        if (choiceBoxSituacao.getValue() == null)
            errorMsg += "\nEscolha um valor para Situação";
        else
            subGenerica.setSituacao(choiceBoxSituacao.getValue());

        if (autores.getItems().isEmpty())
            errorMsg += "\nDefina pelo menos um autor.";
        else
            subGenerica.setAutores(autores.getItems());

        switch (subGenerica.getClass().getSuperclass().getSimpleName()) {
            case "SubmissaoApresentacao":
                SubmissaoApresentacao subApres = (SubmissaoApresentacao) subGenerica;
                if (textAreaResumo.getText().length() < 1)
                    errorMsg += "\nResumo não pode ser vazio.";
                else if (textAreaResumo.getText().length() > maxChars)
                    errorMsg += "\nResumo não pode ter mais que " + maxChars + " caracteres.";
                else
                    subApres.setResumo(textAreaResumo.getText());

                if (textAreaAbstract.getText().length() < 1)
                    errorMsg += "\nAbstract não pode ser vazio.";
                else if (textAreaAbstract.getText().length() > maxChars)
                    errorMsg += "\nAbstract não pode ter mais que " + maxChars + " caracteres.";
                else
                    subApres.set_abstract(textAreaAbstract.getText());
                subApres.setDuracao((int) sliderDuracao.getValue());


                switch (subApres.getClass().getSimpleName()) {
                    case "Minicurso":
                        Minicurso subMini = (Minicurso) subApres;
                        if (spRecursos.getText().length() < 1)
                            errorMsg += "\nRecursos não pode ser vazio.";
                        else if (spRecursos.getText().length() > maxChars)
                            errorMsg += "\nRecursos não pode ter mais que " + maxChars + " caracteres.";
                        else
                            subMini.setRecursos(spRecursos.getText());
                        if (spMetodologia.getText().length() < 1)
                            errorMsg += "\nMetodologia não pode ser vazio.";
                        else if (spMetodologia.getText().length() > maxChars)
                            errorMsg += "\nMetodologia não pode ter mais que " + maxChars + " caracteres.";
                        else
                            subMini.setMetodologia(spMetodologia.getText());
                        break;
                    case "Palestra":
                        Palestra subPal = (Palestra) subApres;
                        if (spCurriculo.getText().length() < 1)
                            errorMsg += "\nCurrículo não pode ser vazio.";
                        else if (spCurriculo.getText().length() > maxChars)
                            errorMsg += "\nCurrículo não pode ter mais que " + maxChars + "caracteres.";
                        else
                            subPal.setCurriculo(spCurriculo.getText());
                        break;
                }

                break;
            case "SubmissaoCientifica":
                SubmissaoCientifica subCi = (SubmissaoCientifica) subGenerica;
                //submissaocientifica.class
                if (instituicoes.getItems().isEmpty())
                    errorMsg += "\nAdicione pelo menos uma instituição.";
                else
                    subCi.setInstituicao(instituicoes.getItems());

                if (palavraschave.getItems().isEmpty())
                    errorMsg += "\nAdicione pelo menos uma palavra-chave.";
                else
                    subCi.setPalavraChave(palavraschave.getItems());


                switch (subCi.getClass().getSimpleName()) {
                    case "Artigo":
                        Artigo art = (Artigo) subCi;
                        if (spResumo.getText().length() < 1)
                            errorMsg += "\nResumo não pode ser vazio.";
                        else if (spResumo.getText().length() > maxChars)
                            errorMsg += "\nResumo não pode ter mais que " + maxChars + " caracteres.";
                        else
                            art.setResumo(spResumo.getText());

                        if (spAbstract.getText().length() < 1)
                            errorMsg += "\nAbstract não pode ser vazio.";
                        else if (spAbstract.getText().length() > maxChars)
                            errorMsg += "\nAbstract não pode ter mais que " + maxChars + " caracteres.";
                        else
                            art.set_abstract(spAbstract.getText());
                        break;
                    case "Monografia":
                        Monografia mono = (Monografia) subCi;
                        if (spTipo.getValue() == null)
                            errorMsg += "\nEscolha um valor para Tipo";
                        else
                            mono.setTipo(spTipo.getValue());
                        if (spOrientador.getText().length() < 1)
                            errorMsg += "\nOrientador não pode ser vazio.";
                        else if (spOrientador.getText().length() > 255)
                            errorMsg += "\nOrientador não pode ter mais que 255 caracteres.";
                        else
                            mono.setOrientador(spOrientador.getText());
                        if (spCurso.getText().length() < 1)
                            errorMsg += "\nCurso não pode ser vazio.";
                        else if (spCurso.getText().length() > 255)
                            errorMsg += "\nCurso não pode ter mais que 255 caracteres.";
                        else
                            mono.setCurso(spCurso.getText());
                        try {
                            if (spAno.getText().length() < 1)
                                errorMsg += "\nAno não pode ser vazio.";
                            else if (Integer.parseInt(spAno.getText()) > Year.now().getValue()
                                    || Integer.parseInt(spAno.getText()) <= 1900)
                                errorMsg += "\nEscolha um ano maior que 1900 e menor ou igual a " + Year.now().getValue();
                            else
                                mono.setAno(Integer.parseInt(spAno.getText()));
                        } catch (NumberFormatException nfe) {
                            errorMsg += "\nAno só pode conter números.";
                        }
                        try {
                            if (spNumPags.getText().length() < 1)
                                errorMsg += "\nNúmero de páginas não pode ser vazio.";
                            else if (Integer.parseInt(spNumPags.getText()) > 5000
                                    || Integer.parseInt(spNumPags.getText()) < 1)
                                errorMsg += "\nNúmero de páginas deve ser um intervalo entre 5000 e 1.";
                            else
                                mono.setnPaginas(Integer.parseInt(spNumPags.getText()));
                        } catch (NumberFormatException nfe) {
                            errorMsg += "\nNúmero de páginas só pode conter números.";
                        }

                        if (spResumo.getText().length() < 1)
                            errorMsg += "\nResumo não pode ser vazio.";
                        else if (spResumo.getText().length() > maxChars)
                            errorMsg += "\nResumo não pode ter mais que " + maxChars + " caracteres.";
                        else
                            mono.setResumo(spResumo.getText());

                        if (spAbstract.getText().length() < 1)
                            errorMsg += "\nAbstract não pode ser vazio.";
                        else if (spAbstract.getText().length() > maxChars)
                            errorMsg += "\nAbstract não pode ter mais que " + maxChars + " caracteres.";
                        else
                            mono.set_abstract(spAbstract.getText());
                        break;
                    case "RelatorioTecnico":
                        RelatorioTecnico rel = (RelatorioTecnico) subCi;
                        try {
                            if (spAno.getText().length() < 1)
                                errorMsg += "\nAno não pode ser vazio.";
                            else if (Integer.parseInt(spAno.getText()) > Year.now().getValue()
                                    || Integer.parseInt(spAno.getText()) <= 1900)
                                errorMsg += "\nEscolha um ano maior que 1900 e menor ou igual a " + Year.now().getValue();
                            else
                                rel.setAno(Integer.parseInt(spAno.getText()));
                        } catch (NumberFormatException nfe) {
                            errorMsg += "\nAno só pode conter números.";
                        }
                        try {
                            if (spNumPags.getText().length() < 1)
                                errorMsg += "\nNúmero de páginas não pode ser vazio.";
                            else if (Integer.parseInt(spNumPags.getText()) > 5000
                                    || Integer.parseInt(spNumPags.getText()) < 1)
                                errorMsg += "\nNúmero de páginas deve ser um intervalo entre 5000 e 1.";
                            else
                                rel.setnPaginas(Integer.parseInt(spNumPags.getText()));
                        } catch (NumberFormatException nfe) {
                            errorMsg += "\nNúmero de páginas só pode conter números.";
                        }
                        if (spResumo.getText().length() < 1)
                            errorMsg += "\nResumo não pode ser vazio.";
                        else if (spResumo.getText().length() > maxChars)
                            errorMsg += "\nResumo não pode ter mais que " + maxChars + " caracteres.";
                        else
                            rel.setResumo(spResumo.getText());
                        if (spAbstract.getText().length() < 1)
                            errorMsg += "\nAbstract não pode ser vazio.";
                        else if (spAbstract.getText().length() > maxChars)
                            errorMsg += "\nAbstract não pode ter mais que " + maxChars + " caracteres.";
                        else
                            rel.set_abstract(spAbstract.getText());
                        break;
                    case "Resumo":
                        //resumo nao tem campos específicos, mas pelo bem da
                        //consistência, merece um lugarzinho no switch
                        break;
                }
        }
        return errorMsg;
    }

    //adiciona o texto inserido na caixa acima à lista abaixo
    static void textField_To_List(
            int maxAlgumaCoisa,
            CustomTextField textField,
            CheckListView<String> checkListView,
            Submissao sub) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                String input = textField.getText().replaceAll("\\P{L}", "");

                if (!input.equals("")) {
                    textField.clear();
                    textField.setDisable(true);

                    if (checkListView.getItems().size() < maxAlgumaCoisa)
                        if (!checkListView.getItems().contains(input))
                            checkListView.getItems().add(input);
                        else
                            InterfaceUtil.erro("O elemento já faz parte da submissão.");
                    else if (sub.getClass().getSimpleName().equals("Palestra") // caso especial pois a palestra e a
                            || sub.getClass().getSimpleName().equals("Monografia") // monografia só podem ter um autor
                            && checkListView.getItems().size() == sub.getMAX_AUTORES()) {
                        if (!checkListView.getItems().contains(input))
                            checkListView.getItems().set(0, input);
                        else
                            InterfaceUtil.erro("Já existe esse elemento na lista.");
                    } else
                        InterfaceUtil.erro("Número máximo de elementos excedido. ("+maxAlgumaCoisa+")");
                    textField.setDisable(false);
                }
            }
        });
        checkListView.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i : c.getAddedSubList()) {
                        if (checkListView.getItems().size() != 1)
                            checkListView.getItems().remove(i);
                        else
                            InterfaceUtil.erro("Deve existir pelo menos um elemento na lista.");
                    }
                }
            }
        });
    }
}