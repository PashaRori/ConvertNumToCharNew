import java.io.*;
import java.util.*;

class Number {
    String convertNumToString(long numeric) throws IOException {
        StringBuilder rez = new StringBuilder();
        if (numeric > Long.MAX_VALUE || numeric < Long.MIN_VALUE)
        {
            rez = new StringBuilder("Превышенно значение Long, которое является: +-" + Long.MAX_VALUE);
        }
        else {
            //открытие файла
            FileReader fr = null;
            try {
                fr = new FileReader("справочник");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //System.out.println("Файл справочник небыл обнаружен!");
            }
            assert fr != null;
            Scanner scan = new Scanner(fr);
            //заполнеие массива с названиями из файла
            String[] directoryArray = new String[100];
            int parToMas = 0;
            while (scan.hasNextLine()) {
                directoryArray[parToMas] = scan.nextLine();
                parToMas++;
            }
            fr.close();
            //создание массивов
            String[] stringTo9 = new String[10];
            String[] stringTo19 = new String[20];
            String[] string20To90 = new String[8];
            String[] string100To900 = new String[9];
            String[] string1000ToInfinity = new String[parToMas];
            String[] stringTo99 = new String[100];
            //заполнение массивов для работы
            System.arraycopy(directoryArray, 0, stringTo9, 0, 10);
            System.arraycopy(directoryArray, 0, stringTo19, 0, 20);
            System.arraycopy(directoryArray, 20, string20To90, 0, 8);
            System.arraycopy(directoryArray, 28, string100To900, 0, 9);
            if (parToMas - 37 >= 0) System.arraycopy(directoryArray, 37, string1000ToInfinity, 0, parToMas - 37);
            System.arraycopy(stringTo19, 0, stringTo99, 0, 20);
            for (int i = 20; i < 100; i++) {
                if (i % 10 == 0) {
                    stringTo99[i] = string20To90[(i / 10) - 2];
                } else stringTo99[i] = string20To90[(i / 10) - 2] + " " + stringTo9[i % 10];
            }
            //получения количества и разбиение на 3 числа
            long numericConverted = Math.abs(numeric);
            int col3chisel = 0;
            ArrayList segments = new ArrayList();
            while (numericConverted > 999) {
                long seg = numericConverted / 1000;
                segments.add(numericConverted - (seg * 1000));
                numericConverted = seg;
                col3chisel++;
            }
            segments.add(numericConverted);
            Collections.reverse(segments);
            int level3Num = segments.size() - 1;
            //Замена чисел на слова
            if (numeric<0)
            {
                rez.append(" минус");
            }
            int numericNew, hundredInteger, desInt, edenInt;
            String hundredString, dozensString;
            for (int i = 0; i < segments.size(); i++) {
                numericNew = (int) Integer.valueOf((segments.get(i).toString()));
                //заполнение сотен
                hundredInteger = numericNew / 100;
                if (hundredInteger == 0) {
                    hundredString = "";
                } else hundredString = " " + string100To900[hundredInteger - 1];
                desInt = numericNew % 100;
                if (hundredInteger > 0 && desInt == 0) {
                    dozensString = "";
                } else dozensString = " " + stringTo99[desInt];
                edenInt = numericNew % 10;
                //заполнение сотен
                if (level3Num == 0) {
                    if (hundredInteger == 0 && desInt == 0) {
                        if (col3chisel == 0) rez.append(hundredString).append(dozensString);
                    } else rez.append(hundredString).append(dozensString);
                }
                if (hundredInteger != 0 || desInt != 0 && level3Num > 0) {
                    //заполнение для тысячи
                    if (level3Num == 1) {
                        if (desInt > 10 && desInt < 20) {
                            rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]);
                        } else {
                            if (edenInt == 1) {
                                rez.append(hundredString).append(" одна ").append(string1000ToInfinity[level3Num - 1]).append("а");
                            }
                            if (edenInt == 2) {
                                rez.append(hundredString).append(" две ").append(string1000ToInfinity[level3Num - 1]).append("и");
                            }
                            if (edenInt > 2 && edenInt < 5) {
                                rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]).append("и");
                            }
                            if (edenInt >= 5 || edenInt == 0) {
                                rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]);
                            }
                        }
                    }
                    //заполнение для милионов и больше
                    if (level3Num > 1) {
                        if (edenInt == 1) {
                            rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]);
                        }
                        if (edenInt > 1 && edenInt < 5) {
                            rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]).append("а");
                        }
                        if (edenInt >= 5 || edenInt == 0) {
                            rez.append(hundredString).append(dozensString).append(" ").append(string1000ToInfinity[level3Num - 1]).append("ов");
                        }
                    }
                }
                level3Num--;
            }
        }
        //возврат результата
        return rez.toString();
    }
}