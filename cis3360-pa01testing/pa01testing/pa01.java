import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class pa01 {
    public static void main(String[] args) throws IOException {
        // Check if the number of command line arguments is correct
        if (args.length != 2) {
            System.out.println("HillCipher <keyfile> <plaintextfile>");
            System.exit(1);
        }

        // Read the keyfile
        Scanner keyInput = new Scanner(new File(args[0]));
        int matrixSize = keyInput.nextInt();
        int[][] encryptionKey = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                encryptionKey[i][rowIndex] = keyInput.nextInt();
            }
        }
        keyInput.close();

        // Read the plaintextfile
        Scanner plaintextInput = new Scanner(new File(args[1]));
        String plaintext = "";
        while (plaintextInput.hasNextLine()) {
            plaintext += plaintextInput.nextLine().toLowerCase().replaceAll("[^a-z]+", "");
        }
        plaintextInput.close();

        // Pad the plaintext if necessary
        while (plaintext.length() % matrixSize != 0) {
            plaintext += "x";
        }

        // Convert the plaintext to a matrix
        int[][] plaintextMatrix = new int[matrixSize][plaintext.length() / matrixSize];
        int count = 0;
        for (int i = 0; i < plaintextMatrix[0].length; i++) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                plaintextMatrix[rowIndex][i] = plaintext.charAt(count) - 'a';
                count++;
            }
        }

        // Encrypt the plaintext using the key
        int[][] ciphertextMatrix = multiplyMatrices(encryptionKey, plaintextMatrix);

        // Convert the ciphertext matrix to a string
        String ciphertext = "";
        for (int i = 0; i < ciphertextMatrix[0].length; i++) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                ciphertext += (char) (ciphertextMatrix[rowIndex][i] + 'a');
            }
            if (i % 80 == 79) {
                ciphertext += "\n";
            }
        }

        // Output the key/ plaintext/ cipher
        System.out.println("Key:");
        for (int i = 0; i < matrixSize; i++) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                System.out.print(encryptionKey[i][rowIndex] + " ");
            }
            System.out.println();
        }
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext:\n" + ciphertext);
    }

    // Multiplies two matrices
    private static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int nRowsA = a.length;  //num of rows in Matrix A
        int matrixSize = b[0].length;
        int[][] HillCipher = new int[nRowsA][matrixSize];
        for (int i = 0; i < nRowsA; i++) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                for (int collIndex = 0; collIndex < a[0].length; collIndex++) {
                    HillCipher[i][rowIndex] += a[i][collIndex] * b[collIndex][rowIndex];
                }
                HillCipher[i][rowIndex] %= 26;
            }
        }
        return HillCipher;
    }
}