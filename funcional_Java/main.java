import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

//"main"
class Main {
    public static void main(String[] args) {
        String arquivo = "src\\dados.csv"; // path do .csv
        BufferedReader leitor = null; // leitor do arquivo .csv
        String linha = ""; // variavel que lê cada linha do .csv
        LinkedList<dado> dados = new LinkedList<>(); // lista dos objetos

        //Lendo N1, N2, N3 e N4
        Scanner input = new Scanner(System.in);
        int n1 = input.nextInt();
        int n2 = input.nextInt();
        int n3 = input.nextInt();
        int n4 = input.nextInt();

        //parte de stream
        try{
            //lendo e guardando os valores do csv
            leitor = new BufferedReader(new BufferedReader(new FileReader(arquivo)));
            while ((linha = leitor.readLine()) != null){
                String[] temp = linha.split(",");
                dados.add(new dado(temp[0], temp[1], temp[2],temp[3], temp[4]));
            }

            //CASO 1
            int caso1 = dados.stream()
                    .filter(d -> d.getConfirmed() >= n1) // filtra quais tem confirmed >= a n1
                    .mapToInt(d -> d.getActive()) // transforma em uma stream de inteiros de actives
                    .sum(); // soma a stream

            System.out.println(caso1);

            //CASO 2
            dados.stream()
                    .sorted(Comparator.comparingInt(d -> -d.getActive())) //ordena de forma decrescente com base no active
                    //.peek(d -> System.out.println(d.getActive()))
                    .limit(n2) // pega apenas os primeiros n2 dados
                    .sorted(Comparator.comparingInt(d -> d.getConfirmed())) //ordena de forma crescente com base no confirmed
                    .limit(n3) // pega apenas os primeiros n3 dados
                    .map(d -> d.getDeath()) // transforma em uma stream de deaths
                    .forEach(System.out::println); // printa cada um

            //CASO 3
            dados.stream()
                    .sorted(Comparator.comparingInt(d -> -d.getConfirmed())) // ordena de forma decrescente a partir do confirmed
                    .limit(n4) // pega os primeiros n4 dados
                    .sorted(Comparator.comparing(d -> d.getCountry())) // ordena de forma crescente a partir do country (ordem alfabetica)
                    .map(d -> d.getCountry()) // transforma em uma stream de nome de paises
                    .forEach(System.out::println); // printa cada um

        }catch (Exception e){
            System.out.println("DEU RUIM ;-; (verifique o path do arquivo)");
        }
    }
}

//armazena dados de cada linha do .csv
class dado {
    private final String country;
    private final int confirmed;
    private final int death;
    private final int recovery;
    private final int active;

    public dado(String country, String confirmed, String death, String recovery, String active) {
        this.country = country;
        this.confirmed = Integer.parseInt(confirmed);
        this.death = Integer.parseInt(death);
        this.recovery = Integer.parseInt(recovery);
        this.active = Integer.parseInt(active);
    }

    public String getCountry() {
        return country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeath() {
        return death;
    }

    public int getRecovery() {
        return recovery;
    }

    public int getActive() {
        return active;
    }
}