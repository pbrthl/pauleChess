package NeuralNetwork;

import NeuralNetwork.matrixLibrary.Matrix;

import java.util.List;

public class NeuralNetwork {
    /*
    Matrix weights_ih , weights_ho , bias_h , bias_o;
    double l_rate = 0.01;


    public NeuralNetwork(int i,int h,int o)
    {
        weights_ih = new Matrix(h,i);
        weights_ho = new Matrix(o,h);

        bias_h= new Matrix(h,1);
        bias_o= new Matrix(o,1);

    }


    public List<Double> predict(double[] X)
    {
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        Matrix output = Matrix.multiply(weights_ho,hidden);
        output.add(bias_o);
        output.sigmoid();

        return output.toArray();
    }


    public void train(double [] X,double [] Y)
    {
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        Matrix output = Matrix.multiply(weights_ho,hidden);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(Y);

        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta =  Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden.dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);

    }


    public void fit(double[][]X,double[][]Y,int epochs)
    {
        for(int i=0;i<epochs;i++)
        {
            int sampleN =  (int)(Math.random() * X.length );
            this.train(X[sampleN], Y[sampleN]);
        }
    } */

    Matrix weights_ih , weights_ho, bias_o;
    Matrix[] biases;
    Matrix[] weights;
    double l_rate = 0.01;


    public NeuralNetwork(int i,int h,int o, int numberOfLayers)
    {
        weights_ih = new Matrix(h,i);
        weights_ho = new Matrix(o,h);
        bias_o= new Matrix(o,1);

        biases = new Matrix[numberOfLayers];
        weights = new Matrix[numberOfLayers - 1];

        for(int layIndex = 0; layIndex < numberOfLayers; layIndex++){
            biases[layIndex] = new Matrix(h,1);
        }

        for(int layIndex = 0; layIndex < numberOfLayers - 1; layIndex++){
            weights[layIndex] = new Matrix(h, h);
        }
    }


    public List<Double> predict(double[] x)
    {
        Matrix input = Matrix.fromArray(x);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(biases[0]);
        hidden.sigmoid();
        for(int i = 1; i < biases.length; i++){
            hidden = Matrix.multiply(weights[i - 1], hidden);
            hidden.add(biases[i]);
            hidden.sigmoid();
        }
        Matrix output = Matrix.multiply(weights_ho,hidden);
        output.add(bias_o);
        output.sigmoid();
        return output.toArray();
    }


    public void train(double [] x, double [] y)
    {
        Matrix[] layerVals = new Matrix[biases.length];
        Matrix input = Matrix.fromArray(x);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(biases[0]);
        hidden.sigmoid();
        layerVals[0] = hidden;

        for(int i = 1; i < biases.length; i++){
            hidden = Matrix.multiply(weights[i - 1], hidden);
            hidden.add(biases[i]);
            hidden.sigmoid();
            layerVals[i] = hidden;
        }

        Matrix output = Matrix.multiply(weights_ho,hidden);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(y);

        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta =  Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_t = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_t, error);

        for(int i = biases.length - 1; i > 0; i--){
            Matrix h_gradient = layerVals[i].dsigmoid();
            h_gradient.multiply(hidden_errors);
            h_gradient.multiply(l_rate);

            Matrix lv_t = Matrix.transpose(layerVals[i - 1]);
            Matrix w_hh_delta = Matrix.multiply(h_gradient, lv_t);

            weights[i - 1].add(w_hh_delta);
            biases[i].add(h_gradient);

            Matrix w_hh_t = Matrix.transpose(weights[i - 1]);
            hidden_errors = Matrix.multiply(w_hh_t, hidden_errors);
        }

        Matrix h_gradient = layerVals[0].dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        biases[0].add(h_gradient);

    }


    public void fit(double[][]X,double[][]Y,int epochs)
    {
        for(int i=0;i<epochs;i++)
        {
            int sampleN =  (int)(Math.random() * X.length );
            this.train(X[sampleN], Y[sampleN]);
        }
    }





}
