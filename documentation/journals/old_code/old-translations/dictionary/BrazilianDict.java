package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class BrazilianDict extends Dictionary {
    public BrazilianDict() {
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Desabafar gás? - Sim"; // gás de ventilação
        no = "Detonar? - Não";
        fancyChars = "á à â ã ç é ê í ó ô õ ú ü";
        moduleLabels = new String[]{"Botões", "", "Codigo Morse", "Senhas", "Ventilação de gás"};
        buttonLabels = new String[]{"Vermelho", "Azul", "Amarelo", "Branco", "Segurar", "Pressionar", "Detonar", "Abortar"};
        passwords = new String[]{
                "achar", "aluno", "baixo", "breve", "caixa",
                "canto", "carta", "certo", "cinco", "coisa",
                "disco", "falar", "horas", "letra", "livro",
                "lugar", "menor", ",menos", "mesmo", "mundo",
                "nobre", "nunca", "ontem", "outro", "parou",
                "pensa", "pobre", "ponto", "praia", "salas",
                "sobre", "terra", "troca", "trono", "verde"};
    }

    @Override
    public String predictWord(String part, boolean isMorse) {
        if (!isMorse) {
            switch (part.toLowerCase()) {
                case "i":
                case "is":
                case "iss":
                    return "isso";
                case "no":
                case "n":
                case "nao":
                case "nã":
                case "na":
                    return "não";
                case "6":
                    return "^";
                case "v":
                case "vi":
                case "vis":
                case "viso":
                    return "visor";
                case "nad":
                case "nd":
                    return "nada";
                case "p":
                case "pr":
                case "pro":
                case "pron":
                case "pront":
                    return "pronto";
                case "pri":
                case "prim":
                case "prime":
                case "primei":
                case "primeir":
                    return "primeiro";
                case "b":
                case "br":
                case "bra":
                case "bran":
                case "branc":
                    return "branco";
                case "q":
                case "qu":
                case "que":
                case "qe":
                case "qê":
                    return "quê";
                case "s":
                case "se":
                case "sei":
                case "si":
                    return "sei";
                case "ses":
                case "sis":
                    return "seis";
                case "e":
                case "es":
                case "esq":
                case "esqu":
                case "esque":
                case "esquer":
                case "esquerd":
                    return "esquerda";
                case "d":
                case "di":
                case "dir":
                case "dire":
                case "direi":
                case "direit":
                    return "direita";
                case "m":
                case "me":
                case "mei":
                    return "meio";
                case "be":
                case "bel":
                case "bele":
                case "belez":
                    return "beleza";
                case "a":
                case "ap":
                case "ape":
                case "aper":
                case "apert":
                    return "aperta";
                case "vo":
                case "voc":
                case "voce":
                case "vce":
                case "voe":
                    return "você";
                case "c":
                case "ce":
                case "ces":
                case "cest":
                    return "cesto";
                case "sex":
                case "sext":
                    return "sexto";
                case "c ":
                    return "c e";
                case "ê é":
                case "e e":
                case "ce e":
                case "cee":
                case "cêé":
                    return "cê é";
                case "q?":
                case "qu?":
                case "que?":
                    return "quê?";
                case "seg":
                case "segu":
                case "segur":
                    return "segura";
                case "f":
                case "fo":
                case "fi":
                    return "foi";
                case "pró":
                case "prox":
                case "próx":
                case "proxi":
                case "próxi":
                case "proxim":
                case "próxim":
                case "proximo":
                    return "próximo";
                case "t":
                case "ti":
                case "tip":
                case "tpo":
                case "tp":
                    return "tipo";
                case "em":
                case "emp":
                case "empt":
                    return "empty";
                case "esc":
                case "escr":
                case "escri":
                case "escrit":
                    return "escrito";
                case "ac":
                case "ace":
                case "acen":
                case "acent":
                    return "acento";
                case "cel":
                case "cla":
                    return "cela";
                case "2":
                    return "2S";
                case "asse":
                case "assen":
                case "assent":
                    return "assento";
                case "s la":
                case "sla":
                case "seila":
                case "sei la":
                    return "sei lá";
                case "sel":
                    return "sela";
                case "pe":
                case "per":
                case "pera":
                case "perai":
                case "peria":
                case "peraí":
                    return "pería";
                case "voce e":
                case "você e":
                case "vocêé":
                    return "você é";
                case "ess":
                    return "esse";
                case "as":
                case "ass":
                case "ao":
                case "aso":
                    return "asso";
                case "aco":
                    return "aço";
                default:
                    return part;
            }
        }
        switch (part.toLowerCase()) {
            case "b":
            case "bu":
            case "bur":
            case "burr":
            case "brr":
            case "brro":
                return "burro";
            case "bo":
            case "bos":
            case "boss":
                return "bossa";
            case "bom":
            case "bomb":
                return "bomba";
            case "ba":
            case "bat":
            case "bato":
                return "batom";
            case "bombo":
            case "bbom":
                return "bombom";
            case "d":
            case "da":
            case "dad":
            case "dado":
                return "dados";
            case "do":
            case "dos":
            case "doss":
            case "dossi":
            case "dossie":
                return "dossiê";
            case "m":
            case "mo":
            case "mor":
            case "mors":
                return "morse";
            case "doi":
            case "doid":
                return "doido";
            case "mod":
            case "modu":
            case "modul":
                return "modulo";
            case "s":
            case "se":
            case "sen":
            case "senh":
                return "senha";
            case "ser":
            case "seri":
            case "seria":
                return "serial";
            case "sa":
            case "sam":
            case "samb":
                return "samba";
            case "c":
            case "ch":
            case "cha":
            case "chav":
            case "chva":
                return "chave";
            case "co":
            case "cod":
            case "codi":
            case "codig":
                return "codigo";
            case "con":
            case "cont":
                return "conta";
            default:
                return part;
        }
    }

    @Override
    protected void initFreqs() {
        frequencies.put("bossa", 3.505);
        frequencies.put("bomba", 3.515);
        frequencies.put("batom", 3.522);
        frequencies.put("bombom", 3.532);
        frequencies.put("burro", 3.535);
        frequencies.put("dados", 3.542);
        frequencies.put("dossiê", 3.545);
        frequencies.put("doido", 3.552);
        frequencies.put("morse", 3.555);
        frequencies.put("modulo", 3.565);
        frequencies.put("senha", 3.572);
        frequencies.put("serial", 3.575);
        frequencies.put("samba", 3.582);
        frequencies.put("chave", 3.592);
        frequencies.put("codigo", 3.595);
        frequencies.put("conta", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("PRONTO", "ISSO, BELEZA, QUÊ, MEIO, ESQUERDA, APERTA, DIREITA, BRANCO, PRONTO");
        stepTwoMap.put("PRIMEIRO", "ESQUERDA, BELEZA, ISSO, MEIO, NÃO, DIREITA, NADA, SEIS, ESPERA, PRONTO, BRANCO, QUÊ, APERTA, PRIMEIRO");
        stepTwoMap.put("NÃO", " BRANCO, SEIS, ESPERA, PRIMEIRO, QUÊ, PRONTO, DIREITA, ISSO, NADA, ESQUERDA, APERTA, BELEZA, NÃO");
        stepTwoMap.put("BRANCO", "ESPERA, DIREITA, BELEZA, MEIO, BRANCO");
        stepTwoMap.put("NADA", "SEIS, DIREITA, BELEZA, MEIO, ISSO, BRANCO, NÃO, APERTA, ESQUERDA, QUÊ, ESPERA, PRIMEIRO, NADA");
        stepTwoMap.put("ISSO", "BELEZA, DIREITA, SEIS, MEIO, PRIMEIRO, QUÊ, APERTA, PRONTO, NADA, ISSO");
        stepTwoMap.put("QUÊ", "SEIS, QUÊ");
        stepTwoMap.put("SEIS", "PRONTO, NADA, ESQUERDA, QUÊ, BELEZA, ISSO, DIREITA, NÃO, APERTA, BRANCO, SEIS");
        stepTwoMap.put("ESQUERDA", "DIREITA, ESQUERDA");
        stepTwoMap.put("DIREITA", "ISSO, NADA, PRONTO, APERTA, NÃO, ESPERA, QUÊ, DIREITA");
        stepTwoMap.put("MEIO", "BRANCO, PRONTO, BELEZA, QUÊ, NADA, APERTA, NÃO, ESPERA, ESQUERDA, MEIO");
        stepTwoMap.put("BELEZA", "MEIO, NÃO, PRIMEIRO, ISSO, SEIS, NADA, ESPERA, BELEZA");
        stepTwoMap.put("ESPERA", "SEIS, NÃO, BRANCO, BELEZA, ISSO, ESQUERDA, PRIMEIRO, APERTA, QUÊ, ESPERA");
        stepTwoMap.put("APERTA", "DIREITA, MEIO, ISSO, PRONTO, APERTA");
        stepTwoMap.put("VOCÊ", "SEI, VOCÊ É, C, CÊ É, PRÓXIMO, CESTO, C E, SEGURA, QUÊ?, VOCÊ");
        stepTwoMap.put("VOCÊ É", " C, PRÓXIMO, TIPO, CESTO, QUÊ?, FOI, SEXTO, SEGURA, VOCÊ, CÊ, CÊ É, SEI, C E, VOCÊ É");
        stepTwoMap.put("C", "SEXTO, VOCÊ É, CESTO, C");
        stepTwoMap.put("CÊ É", "VOCÊ, CÊ É");
        stepTwoMap.put("C E", "FOI, CÊ, C E");
        stepTwoMap.put("CÊ", "CESTO, SEI, PRÓXIMO, QUÊ?, CÊ É, C E, SEXTO, FOI, CÊ");
        stepTwoMap.put("CESTO", "CESTO");
        stepTwoMap.put("SEXTO", "C E, CÊ, VOCÊ É, CÊ É, PRÓXIMO, SEXTO");
        stepTwoMap.put("QUÊ?", "VOCÊ, SEGURA, CÊ É, C, CÊ, FOI, SEXTO, TIPO, VOCÊ É, CESTO, C E, PRÓXIMO, QUÊ?");
        stepTwoMap.put("FOI", "SEI, CESTO, PRÓXIMO, QUÊ?, C, C E, CÊ É, SEGURA, TIPO, VOCÊ, CÊ, VOCÊ É, SEXTO, FOI");
        stepTwoMap.put("PRÓXIMO", "QUÊ?, CESTO, SEXTO, C, SEGURA, SEI, PRÓXIMO");
        stepTwoMap.put("SEGURA", "VOCÊ É, CÊ, FOI, SEXTO, VOCÊ, C E, SEI, QUÊ?, CÊ É, PRÓXIMO, SEGURA");
        stepTwoMap.put("SEI", "VOCÊ É, FOI, TIPO, CÊ É, VOCÊ, SEGURA, CESTO, C E, SEI");
        stepTwoMap.put("TIPO", "CÊ É, PRÓXIMO, CÊ, C E, SEGURA, FOI, SEXTO, QUÊ?, CESTO, VOCÊ, TIPO");
    }
}
