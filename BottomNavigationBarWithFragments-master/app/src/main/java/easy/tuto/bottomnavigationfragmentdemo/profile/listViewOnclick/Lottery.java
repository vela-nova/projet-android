package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lottery {
    private List<Float> percentages;
    private final Random random;
    private Float[] lotteryPourcentage;


    public Lottery(int boite) {
        this.random = new Random();
        this.percentages = new ArrayList<>();
        this.lotteryPourcentage=new Float[] {
                20F,30F,40F,50F,60F,
                20F,30F,40F,50F,60F,
                20F,30F,40F,50F,60F,
                20F,30F,40F,50F,60F,
                20F,30F,40F,50F,60F,
        };

        switch (boite) {
            case 1:
                this.percentages.add((Float) this.lotteryPourcentage[1]);
                this.percentages.add((Float) this.lotteryPourcentage[1]);
                this.percentages.add((Float) this.lotteryPourcentage[2]);
                this.percentages.add((Float) this.lotteryPourcentage[3]);
                this.percentages.add((Float) this.lotteryPourcentage[4]);
                break;
            case 2:
                this.percentages.add((Float) this.lotteryPourcentage[5]);
                this.percentages.add((Float) this.lotteryPourcentage[6]);
                this.percentages.add((Float) this.lotteryPourcentage[7]);
                this.percentages.add((Float) this.lotteryPourcentage[8]);
                this.percentages.add((Float) this.lotteryPourcentage[9]);
                break;
            case 3:
                this.percentages.add((Float) this.lotteryPourcentage[10]);
                this.percentages.add((Float) this.lotteryPourcentage[11]);
                this.percentages.add((Float) this.lotteryPourcentage[12]);
                this.percentages.add((Float) this.lotteryPourcentage[13]);
                this.percentages.add((Float) this.lotteryPourcentage[14]);
                break;
            case 4:
                this.percentages.add((Float) this.lotteryPourcentage[15]);
                this.percentages.add((Float) this.lotteryPourcentage[16]);
                this.percentages.add((Float) this.lotteryPourcentage[17]);
                this.percentages.add((Float) this.lotteryPourcentage[18]);
                this.percentages.add((Float) this.lotteryPourcentage[19]);
                break;
            case 5:
                this.percentages.add((Float) this.lotteryPourcentage[20]);
                this.percentages.add((Float) this.lotteryPourcentage[21]);
                this.percentages.add((Float) this.lotteryPourcentage[22]);
                this.percentages.add((Float) this.lotteryPourcentage[23]);
                this.percentages.add((Float) this.lotteryPourcentage[24]);
                break;
            default:
                System.out.println("Option par défaut sélectionnée");
                break;
        }
    }

    public int getWinningItemIndex() {
        // Vérifier si la liste des pourcentages est valide
        if (percentages == null || percentages.size() != 5) {
            throw new IllegalArgumentException("La liste des pourcentages doit contenir exactement 5 éléments.");
        }

        // Calculer la somme totale des pourcentages
        float totalPercentage = 0;
        for (float percentage : percentages) {
            if (percentage < 0) {
                throw new IllegalArgumentException("Les pourcentages ne peuvent pas être négatifs.");
            }
            totalPercentage += percentage;
        }

        // Générer un nombre aléatoire entre 0 et la somme totale des pourcentages
        float randomValue = random.nextFloat() * totalPercentage;

        // Parcourir les pourcentages pour déterminer l'élément gagnant
        float cumulativePercentage = 0;
        for (int i = 0; i < percentages.size(); i++) {
            cumulativePercentage += percentages.get(i);
            if (randomValue <= cumulativePercentage) {
                return i;
            }
        }

        // Si aucune correspondance n'a été trouvée, renvoyer -1 pour indiquer une erreur
        return -1;
    }

    public String getWinningItemName() {
        int winningIndex = getWinningItemIndex();
        if (winningIndex >= 0 && winningIndex < 5) {
            // Remplacer les noms d'éléments par les noms correspondants dans votre application
            String[] itemNames = {"Élément 1", "Élément 2", "Élément 3", "Élément 4", "Élément 5"};
            return itemNames[winningIndex];
        }
        return "Aucun élément gagnant";
    }
}
