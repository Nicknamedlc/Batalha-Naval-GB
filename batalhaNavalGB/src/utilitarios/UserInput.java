package utilitarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
    private static String s;
    private static InputStreamReader i = new InputStreamReader(System.in);
    private static BufferedReader d = new BufferedReader(i);

    /*
    * Lista de métodos para ler o Input do usuário com seus parâmetros
    * - anInt({String msg})
    * - anIntBetween({String msg}, int min, int max)
    * - aString({String msg})?
    * - aStringMaxLength(int min, int max)?
    * */

    public static int anInt() {
        int a = 0;
        do {
            try {
                s = d.readLine();
                a = Integer.parseInt(s);
                break;
            } catch (IOException e) {
                System.out.println("Erro de I/O: " + e);
            } catch (NumberFormatException e) {
                System.out.println("o valor digitado deve ser inteiro: " + e);
            }
        } while (true);
        return (a);
    }

    public static int anInt(String msg) {
        System.out.println(msg);
        return anInt();
    }

    public static int anIntBetween(int min, int max) {
        int a = 0;
        System.out.print("(Entre " + min + " e " + max + ")\n");
        do {
            try {
                s = d.readLine();
                a = Integer.parseInt(s);
                if (a >= min && a <= max) {
                    break;
                }
                System.out.print("O valor digitado deve estar entre " + min + " e " + max);
            } catch (IOException e) {
                System.out.println("Erro de I/O: " + e);
            } catch (NumberFormatException e) {
                System.out.println("o valor digitado deve ser inteiro: " + e);
            }
        } while (true);
        return (a);
    }

    public static int anIntBetween(String msg, int min, int max) {
    	System.out.println(msg);
        return anIntBetween(min, max);
    }

    public static double aDouble() {
        double a = 0;
        do {
            try {
                s = d.readLine();
                a = Double.parseDouble(s);
                break;
            } catch (IOException e) {
                System.out.println("Erro de I/O: " + e);
            } catch (NumberFormatException e) {
                System.out.println("o valor digitado deve ser número: " + e);
            }
        } while (true);
        return (a);
    }

    public static double aDouble(String msg) {
        System.out.println(msg);
        return aDouble();
    }

    public static String aString() {
        s = "";
        try {
            s = d.readLine();
        } catch (IOException e) {
            System.out.println("Erro de I/O: " + e);
        }
        return (s);
    }

    public static String aString(String msg) {
        System.out.println(msg);
        return aString();
    }

    public static char aChar() {
        char a = ' ';
        try {
            s = d.readLine();
            a = s.charAt(0);
        } catch (IOException e) {
            System.out.println("Erro de I/O: " + e);
        } catch (NumberFormatException e) {
            System.out.println("o valor digitado deve ser char: " + e);
        }
        return (a);
    }

    public static char aChar(String msg) {
        System.out.println(msg);
        return aChar();
    }
}