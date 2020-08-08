package Controller;

import Controller.GameControl.TestGameController;
import Model.Chessboard;
import View.CLIView;
import chess_ai.NeuralNetwork.NeuralNetworkExperiments;
import chess_ai.move_reader.MRExperiments;


import java.io.IOException;

public class chessMain {

    public static void main(String[] args) throws IOException {

        //NeuralNetworkExperiments.trainXOR();

        MRExperiments.learnFromFile();

        //MRExperiments.showGameSequence("1. d4 Nf6 2. c4 e6 3. Nc3 Bb4 4. e3 O-O 5. Bd3 d5 6. Nf3 c5 7. O-O cxd4 8. exd4 dxc4 9. Bxc4 Nc6 10. a3 Ba5 11. Be3 h6 12. Qe2 Ne7 13. Rad1 a6 14. Bd3 b5 15. a4 b4 16. Ne4 Nfd5 17. Nc5 Nf5 18. Bb1 Bb6 19. Qc2 g6 20. Qc1 h5 21. Rfe1 Ra7 22. Bg5 f6 23. Nxe6 Bxe6 24. Rxe6 fxg5 25. Ba2 g4 26. Ne5 Kh7 27. Rxg6 Rg7 28. Re6 Nde7 29. Bb1 Ng6 30. Qc6 Nxd4 31. Bxg6+ Kh8 32. Bc2 Nxc6 33. Rh6+ Rh7 34. Rxh7+ Kg8 35. Rxd8 Rxd8 36. Nxc6 Rd2 37. Nxb4 a5 38. Rb7 Bxf2+ 39. Kf1 axb4 40. Bb3+ Kh8 41. Rxb4 Rxb2 42. Rb5 Be3 43. Rxh5+ Kg7 44. Bd5 Kf6 45. h3 gxh3 46. Rxh3 Bb6 47. Rb3 Rxb3 48. Bxb3 Ke5 49. Ke2 Kd4 50. Bc2 Bc7 51. g4 Bd8 52. Kf3 Ke5 53. Be4 Kf6 54. Kf4 Bc7+ 55. Ke3 Bd8 56. Kd4 Kg5 57. Bf5 Kf6 58. Kd5 Ba5 59. Kc4 Ke5 60. Kb5 Bd2 61. a5 Kd6 62. Kb6 Be3+ 63. Kb7 Bd2 64. a6 Be3 65. a7 Bxa7 66. Kxa7 Ke5 67. Kb6 Kf6 68. Kc6 Kg5 69. Kd5 Kf6 70. Bd7 Ke7 71. g5 Kxd7 72. g6 Ke7 73. Ke5 Kf8 74. Kf6 Kg8 75. g7 Kh7 76. Kf7 Kh6 77. g8=Q Kh5 78. Qg3 {Black resigns} 1-0");


        /*Chessboard board = new Chessboard();
        CLIView view = new CLIView(board);
        TestGameController controller = new TestGameController(board, view);

        controller.start();*/


    }



}
