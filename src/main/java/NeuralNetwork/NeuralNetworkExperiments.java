package NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkExperiments {


    public static void trainXOR(){

        System.out.print("Neural Network is trained for XOR..");

        double [][] X= {
                {0,0},
                {1,0},
                {0,1},
                {1,1}
        };
        double [][] Y= {
                {0},{1},{1},{0}
        };

        NeuralNetwork nn = new NeuralNetwork(2,10,1);
        nn.fit(X, Y, 50000);

        System.out.println("Training terminated. Tests are printed.. \n");

        double [][] input ={{0,0},{0,1},{1,0},{1,1}};
        List<Double> output;
        for(double d[]:input)
        {
            System.out.print(d[0] + " XOR " + d[1] + " is:");
            output = nn.predict(d);
            output.set(0, (double) (output.get(0) < 0.5 ? 0.0 : 1));
            //output.set(1, (double) (output.get(1) < 0.5 ? 0.0 : 1));
            System.out.println(output.toString());
            System.out.println("\n");
        }
    }


}
