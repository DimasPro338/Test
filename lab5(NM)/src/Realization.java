


import java.util.Arrays;
import java.util.Random;

class Controller {
    static int variant = 31;
    static int numbers = 290000;


    public static void main(String[] args) {

        //Normal
       // Model lab51 = new Model(variant, numbers,3,true,true);
       // System.out.println(lab51);
       // Model lab61 = new Model(variant, numbers,5,true,true);
       // Model lab71 = new Model(variant, numbers,4,false,true);
        //Rivnomirnyy rozpodil

Model lab52 = new Model(variant, numbers,3,true,false);
//Model lab62 = new Model(variant, numbers,5,true,false);
//Model lab72 = new Model(variant, numbers,4,false,false);


    }
}

class Model {
    int numOfGenerators;
    int[] generator;
    int[] tripleGenerator;
    int counters;
    int numbers;

    int[] normalNormal;
    int[] normal;
    int[] discrete;

    Random rand = new Random();

    public Model(int counters, int numbers, int numOfGenerators, boolean type, boolean gauss) {
        this.counters = counters;
        this.numbers = numbers;
        this.numOfGenerators = numOfGenerators;

        generator = new int[numOfGenerators];
        tripleGenerator = new int[numOfGenerators];
        normalNormal = new int[counters];
        normal = new int[counters];
        discrete = new int[counters];
        System.out.println("Discrete\tNormalized\tAverage of 3");
        if(type)
            for(int i = 0; i < numbers; i++) {
                discrete[toUniform(gauss)]++;
                normal[chooseLessAffectedTriple(gauss)]++;
            }
        else {
            for(int i = 0; i < numbers; i++) {
                discrete[toUniform(gauss)]++;
                normal[chooseAverage(gauss)]++;
                normalNormal[chooseAverageTriple(gauss)]++;
            }
        }
        for(int i = 0; i < counters; i++) {
            System.out.println(discrete[i] + "\t" + normal[i] + "\t" + normalNormal[i]);
        }
    }
    public int toUniform(boolean gauss){
        if(gauss)
            return randomGauss();
        return rand.nextInt(counters);
    }

    private int randomGauss() {
        Random random = new Random();
        double result;
        do {
            result = 0.3 * random.nextGaussian();
        } while(result >= 1 || result <-1);
        return (int)Math.round((result+1)*(counters/2-1));
    }


    public int chooseLessAffected(boolean gauss) {
        for (int i =0;i < numOfGenerators; i++) {
            generator[i] = toUniform(gauss);
        }
        Arrays.sort(generator);
        return Math.round(generator[numOfGenerators / 2]);
    }
    public int chooseAverage(boolean gauss) {
        for(int i = 0; i < numOfGenerators; i++) {
            generator[i] = toUniform(gauss);
        }
        Arrays.sort(generator);
        if(numOfGenerators % 2 == 1) {
            return Math.round(generator[numOfGenerators / 2]);
        }
        int fi1 = generator[numOfGenerators / 2];
        int fi2 = generator[numOfGenerators / 2 - 1];
        return (fi1 + fi2) / 2;
    }
    public int chooseLessAffectedTriple(boolean gauss) {
        for (int i = 0; i < numOfGenerators; i++) {
            tripleGenerator[i] = chooseLessAffected(gauss);
        }
        Arrays.sort(tripleGenerator);
        return Math.round(tripleGenerator[numOfGenerators / 2]);
    }
    public int chooseAverageTriple(boolean gauss) {
        for (int i = 0; i < numOfGenerators; i++) {
            tripleGenerator[i] = chooseAverage(gauss);
        }
        Arrays.sort(tripleGenerator);
        if (numOfGenerators % 2 == 1) {
            return Math.round(tripleGenerator[numOfGenerators / 2]);
        }
        int fi1 = tripleGenerator[numOfGenerators / 2];
        int fi2 = tripleGenerator[numOfGenerators / 2 - 1];
        return (fi1 + fi2) / 2;
    }
}