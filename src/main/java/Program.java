import java.io.*;

public class Program {
    public static void main(String[] args) throws IOException {
        long inputNum;
        System.out.println("Введите число: ");
        try
        {
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            String a = br.readLine();
            inputNum = Long.valueOf(a);
            Number number = new Number();
            String rez = number.convertNumToString(inputNum);
            System.out.println(rez);
        }
        catch(Exception e){
        }

    }
}
