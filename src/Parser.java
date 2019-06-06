

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public ArrayList<Request> perfomanceCommands(String string) {
        try {
            ArrayList<Request> requests = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{");
            Request request = new Request();
            Matcher matcher = pattern.matcher(string);
            if (!matcher.find() && (!string.equals("add") && (!string.equals("remove") && (!string.equals("remove_greater")) && (!string.equals("import"))))) {
                //Значит команда без аргумента
                if (string.equals("help")) {
                    request.setCommand("help");
                    request.setAge(null);
                    request.setName(null);
                    request.setVolume(null);
                    request.setPosition(0, 0);
                    requests.add(request);
                    return requests;
                } else if (string.equals("info")) {
                    request.setCommand("info");
                    request.setAge(null);
                    request.setName(null);
                    request.setVolume(null);
                    request.setPosition(0, 0);
                    requests.add(request);
                    return requests;
                } else if (string.equals("show")) {
                    request.setCommand("show");
                    request.setAge(null);
                    request.setName(null);
                    request.setVolume(null);
                    request.setPosition(0, 0);
                    requests.add(request);
                    return requests;
                } else if (string.equals("save")) {
                    request.setCommand("save");
                    request.setAge(null);
                    request.setName(null);
                    request.setVolume(null);
                    request.setPosition(0, 0);
                    requests.add(request);
                    return requests;
                } else if (string.equals("end")) {
                    request.setCommand("end");
                    request.setAge(null);
                    request.setName(null);
                    request.setVolume(null);
                    request.setPosition(0, 0);
                    requests.add(request);
                    return  requests;
                }else{
                    System.out.println("Неверный формат ввода команды");
                    return null;
                }

            } else {
                Pattern patternImport = Pattern.compile("import");
                Matcher matcherImport = patternImport.matcher(string);
                if (!matcherImport.find()) {
                    pattern = Pattern.compile("\\}");
                    Matcher matcher1 = pattern.matcher(string);
                    Comparator<String> comparator = (String x, String y) -> {
                        int i = 0;
                        char[] elems = x.toCharArray();
                        for (char obj : elems) {
                            if (obj == '}') {
                                i++;
                            }
                        }
                        return i;
                    };

                    int i = comparator.compare(string, "");
                    if (i == 2) {

                        //Команда с аргументами в одну строку
                        String[] arguments = Parser.getArguments(string);
                        request.setName(arguments[0]);
                        request.setAge(Integer.parseInt(arguments[1]));
                        request.setVolume(Integer.parseInt(arguments[4]));
                        request.setPosition(Integer.parseInt(arguments[2].replaceAll("\\s+", "")), Integer.parseInt(arguments[3].replaceAll("\\s+", "")));

                        request.setCommand(Parser.getCommand(string));
                        requests.add(request);
                        return requests;

                    } else if (i > 2) {
                        System.out.println("Неверный формат ввода команды");
                        return null;
                    } else if (i < 2) {
                        while (i != 2) {
                            Scanner scanner = new Scanner(System.in);
                            string += scanner.nextLine();
                            i = comparator.compare(string, "");
                            if (i > 2) {
                                System.out.println("Неверный формат ввода команды");
                                return null;
                            }
                        }
                        String[] arguments = Parser.getArguments(string);
                        request.setName(arguments[0]);
                        request.setAge(Integer.parseInt(arguments[1]));
                        request.setVolume(Integer.parseInt(arguments[4]));
                        request.setPosition(Integer.parseInt(arguments[2].replaceAll("\\s+", "")), Integer.parseInt(arguments[3].replaceAll("\\s+", "")));
                        request.setCommand(Parser.getCommand(string));
                        requests.add(request);
                        return requests;
                    } else {
                        return null;
                    }
                } else {
                    pattern = Pattern.compile("\\}");
                    Matcher matcher1 = pattern.matcher(string);
                    Comparator<String> comparator = (String x, String y) -> {
                        int i = 0;
                        char[] elems = x.toCharArray();
                        for (char obj : elems) {
                            if (obj == '}') {
                                i++;
                            }
                        }
                        return i;
                    };

                    int i = comparator.compare(string, "");
                    if (i > 1) {
                        System.out.println("Неверный формат ввода команды");
                        return null;
                    } else if (i < 1) {
                        while (i != 1) {
                            Scanner scanner = new Scanner(System.in);
                            string += scanner.nextLine();
                            i = comparator.compare(string, "");
                            if (i > 1) {
                                System.out.println("Неверный формат ввода команды");
                                return null;
                            }

                        }
                        int flag = 0;
                        String adress = "";
                        for (char elem : string.toCharArray()) {
                            if (elem == '{') {
                                flag += 1;
                            } else if (elem == '}') {
                                break;
                            } else if ((elem != '{') && (flag == 1)) {
                                adress += elem;
                            }
                        }
                        File file = new File(adress);
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNext()) {
                            String obj = scanner.nextLine();
                            String[] massive = obj.split(",");
                            request.setCommand("add");
                            request.setName(massive[0]);
                            request.setAge(Integer.parseInt(massive[1]));
                            request.setVolume(Integer.parseInt(massive[2]));
                            request.setPosition(Integer.parseInt(massive[3]), Integer.parseInt(massive[4]));
                            requests.add(request);
                        }
                        return requests;
                    }else{
                        int flag = 0;
                        String adress = "";
                        for (char elem : string.toCharArray()) {
                            if (elem == '{') {
                                flag += 1;
                            } else if (elem == '}') {
                                break;
                            } else if ((elem != '{') && (flag == 1)) {
                                adress += elem;
                            }
                        }
                        File file = new File(adress);
                        if (adress.equals("")) {
                            System.out.println("Неверный формат ввода команды.");
                            return null;
                        }
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNext()) {
                            try {
                                request = new Request();
                                String obj = scanner.nextLine();
                                String[] massive = obj.split(",");
                                request.setCommand("add");
                                request.setName(massive[0]);
                                request.setAge(Integer.parseInt(massive[1]));
                                request.setVolume(Integer.parseInt(massive[2]));
                                request.setPosition(Integer.parseInt(massive[3]), Integer.parseInt(massive[4]));
                                SimpleDateFormat format = new SimpleDateFormat("EEE MMMMMMMMMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                request.setDate(format.parse(massive[5]));
                                requests.add(request);
                            }catch (ParseException e){
                                System.out.println("Возможно, вы указали неверно дату, а может вообще не указали.");
                            }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                            System.out.println("Неверно записаны данные в файле. Образец : Имя, Возраст, x, y, объём желудка, дата. A,1,2,3,4,Tue Jun 07 01:23:42 MSK 2019");
                            return null;
                            }
                        }
                        return requests;
                    }
                }
            }
        }catch (NullPointerException e){
            return null;
        }catch (FileNotFoundException e){
            System.out.println("Нет доступа к файлу.");
            return null;
        }
    }

    private  static  String getCommand(String string){
        String command = "";
        int flag = 0;
        char[] chars = string.toCharArray();
        for (char elem : chars){
            if ((elem != '{') && (flag == 0)){
                command += elem;
            }else if (elem == '{'){
                break;
            }
        }
        return command.replaceAll(" ","");
    }

    private static String[] getArguments(String string) {
        String newString = "";
        int flag = 0;
        for (char elem : string.toCharArray()){
            if (elem == '{'){
                flag ++;
                newString += elem;
            }else if (flag > 0){
                newString += elem;
            }
        }
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(newString);
            JsonObject jsonObject = element.getAsJsonObject();
            JsonElement element1 = jsonObject.getAsJsonObject("Stomach");
            JsonObject jsonObject1 = element1.getAsJsonObject();
            String firstArgument = jsonObject.get("name").getAsString();
            String SecondArgument = jsonObject.get("age").getAsString();
            String ThirdArgument = jsonObject.get("x").getAsString();
            String FourthArgument = jsonObject.get("y").getAsString();
            String FifthArgument = jsonObject1.get("volume").getAsString();
            String[] strings = {firstArgument, SecondArgument, ThirdArgument, FourthArgument, FifthArgument};
            return strings;
        }catch (JsonSyntaxException e){
            System.out.println("Неверный формат ввода команды");
            return null;
        }

//        Pattern patternName = Pattern.compile("name");
//        Matcher matcherName = patternName.matcher(string);
//        Pattern patternAge = Pattern.compile("age");
//        Matcher matcherAge = patternAge.matcher(string);
//        Pattern patternVolume = Pattern.compile("volume");
//        Matcher matcherVolume = patternVolume.matcher(string);
//        Pattern patternX = Pattern.compile("x");
//        Matcher matcherX = patternX.matcher(string);
//        Pattern patternY = Pattern.compile("y");
//        Matcher matcherY = patternY.matcher(string);
//        Pattern patternStomach = Pattern.compile("Stomach");
//        Matcher matcherStomach = patternStomach.matcher(string);
//        if (matcherStomach.find()) {
//            int flag = 0;
//            matcherAge.find();
//            matcherName.find();
//            matcherVolume.find();
//            matcherX.find();
//            matcherY.find();
//            for (int i = matcherName.start(); i < string.length() - 1; i++) {
//                char[] chars = string.toCharArray();
//                if (chars[i] == ':') {
//                    flag += 1;
//                } else if ((chars[i] == ',') || (chars[i] == '}')) {
//                    strings[0] = firstArgument;
//                    flag = 0;
//                    break;
//                } else if ((chars[i] != ':') && (flag == 1) && (chars[i] != '"')) {
//                    firstArgument += chars[i];
//                } else if ((chars[i] != ':') && (flag == 0)) {
//
//                }
//
//
//            }
//
//            for (int i = matcherAge.start(); i < string.length() - 1; i++) {
//                char[] chars = string.toCharArray();
//                if (chars[i] == ':') {
//                    flag += 1;
//                } else if ((chars[i] == ',') || (chars[i] == '}')) {
//                    strings[1] = SecondArgument.replaceAll("\\s+", "");
//                    flag = 0;
//                    break;
//                } else if ((chars[i] != ':') && (flag == 1) && (chars[i] != '"')) {
//                    SecondArgument += chars[i];
//                } else if ((chars[i] != ':') && (flag == 0)) {
//
//                }
//            }
//
//            for (int i = matcherVolume.start(); i < string.length() - 1; i++) {
//                char[] chars = string.toCharArray();
//                if (chars[i] == ':') {
//                    flag += 1;
//                } else if ( (chars[i] == '}')) {
//                    strings[2] = ThirdArgument.replaceAll("\\s+", "");
//                    flag = 0;
//                    break;
//                } else if ((chars[i] != ':') && (chars[i] != '"') && (flag == 1)) {
//                    ThirdArgument += chars[i];
//                } else if ((chars[i] != ':') && (flag == 0)) {
//
//                }
//            }
//
//            flag = 0;
//            int flag1 = 0;
//            char[] chars1 = string.toCharArray();
//            for (char elem : chars1){
//                if (elem == 'x'){
//                    flag1 = 1;
//                }else if ((elem == ':') && (flag1 == 1)){
//                    flag = 1;
//                }else if (((elem == '}') || (elem == ',')) && (flag == 1) && (flag1 == 1)){
//                    flag = 0;
//                    strings[3] += FourthArgument.replaceAll("\\s+", "");
//                    break;
//                }else  if ((flag == 1) && (flag1 == 1) && (elem != '"')){
//                    FourthArgument += elem;
//                }
//            }
//            flag = 0;
//            flag1 = 0;
//            chars1 = string.toCharArray();
//            for (char elem : chars1){
//                if (elem == 'y'){
//                    flag1 = 1;
//                }else if ((elem == ':') && (flag1 == 1)){
//                    flag = 1;
//                }else if (((elem == '}') || (elem == ',')) && (flag == 1) && (flag1 == 1)){
//                    flag = 0;
//                    strings[4] += FifthArgument.replaceAll("\\s+", "");
//                    break;
//                }else  if ((flag == 1) && (flag1 == 1) && (elem != '"')){
//                    FifthArgument += elem;
//                }
//            }


//        }else{
//            System.out.println("Неверный формат ввода команды" );
//            return null;
//        }
    }


}
