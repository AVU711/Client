import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class FinallyParser {
//    private CatVector catVector = new CatVector();
//
//    public  boolean matchesCommand(String string){
//        Pattern pattern = Pattern.compile("\\{");
//        Matcher matcher = pattern.matcher(string);
//        String newString = "";
//        if (matcher.find()){
//            char[] chars = string.toCharArray();
//            for (char elem: chars){
//                if (elem != '{'){
//                    newString += elem;
//                }else{
//                    newString = newString.replaceAll("\\s+","");
//                    break;
//                }
//            }
//        }else{
//            newString = string;
//        }
//        switch (newString){
//            case "add":
//                return true;
//            case "remove":
//                return true;
//            case "remove_greater":
//                return true;
//            case "info":
//                return true;
//            case "show":
//                return true;
//            case "save":
//                return true;
//            case "import":
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    public void StartCommandWithoutArguments(String string){
//        switch (string){
//            case "info":
//                catVector.info();
//                break;
//            case "save":
//                catVector.save();
//                break;
//            case "show":
//                catVector.show();
//                break;
//        }
//    }
//
//    public void StartCommandWithArguments(String command, String argumentFirst, String argumentSecond, String argumentThird){
//        switch (command){
//            case "add":
//                catVector.add(new Cat(argumentFirst,Integer.parseInt(argumentSecond),Integer.parseInt(argumentThird)));
//                break;
//            case "remove":
//                catVector.remove(new Cat(argumentFirst,Integer.parseInt(argumentSecond), Integer.parseInt(argumentThird)));
//                break;
//            case "remove_greater":
//                catVector.remove_greater(new Cat(argumentFirst, Integer.parseInt(argumentSecond), Integer.parseInt(argumentThird)));
//                break;
//        }
//    }
//
//
//    public String[] getArgument(String string){
//        boolean flag1 = false;
//        boolean flag2 = false;
//        boolean flag3 = false;
//        String argumentFirst = "";
//        String argumentSecond = "";
//        String argumentThird = "";
//        String newString = "";
//        char[] chars = string.toCharArray();
//        for (char elem: chars){
//            if (elem != '{'){
//                newString += elem;
//            }else{
//                newString = newString.replaceAll("\\s+","");
//                break;
//            }
//        }
//        if (newString.equals("import")){
//            Pattern pattern = Pattern.compile("\\{");
//            Matcher matcher = pattern.matcher(string);
//            Pattern pattern1 = Pattern.compile("\\}");
//            Matcher matcher1 = pattern1.matcher(string);
//            matcher.find();
//            matcher1.find();
//            int x = matcher.start()  + 1;
//            int y = matcher1.start();
//            char[] elems = string.toCharArray();
//            while (x < y){
//                argumentFirst += elems[x];
//                x++;
//            }
//            String[] arguments = {argumentFirst.replaceAll("\\s+", "")};
//            return arguments;
//        }else{
//            Pattern pattern = Pattern.compile("name");
//            Pattern pattern1 = Pattern.compile("age");
//            Pattern pattern2 = Pattern.compile("volume");
//            Matcher matcher = pattern.matcher(string);
//            char[] elems = string.toCharArray();
//            matcher.find();
//            int x = matcher.start() + 4;
//            while ((elems[x] != ',') && (elems[x] != '}')){
//                if ((elems[x] != ':') && (flag1 == true) && (elems[x] != '"')){
//                    argumentFirst += elems[x];
//                }else if (elems[x] == ':'){
//                    flag1 = true;
//                }
//                x++;
//            }
//            matcher = pattern1.matcher(string);
//            matcher.find();
//            x = matcher.start();
//            while ((elems[x] != ',') && (elems[x] != '}') ){
//                if ((elems[x] != ':') && (flag2 == true) && (elems[x] != '"')){
//                    argumentSecond += elems[x];
//                }else if (elems[x] == ':'){
//                    flag2 = true;
//                }
//                x++;
//            }
//            Pattern pattern3 = Pattern.compile("Stomach");
//            Matcher matcherStomach = pattern3.matcher(string);
//            if (!matcherStomach.find()){
//                return null;
//            }
//            argumentSecond = argumentSecond.replaceAll("\\s+","");
//            matcher = pattern2.matcher(string);
//            matcher.find();
//            x = matcher.start();
//            while ((elems[x] != ',') && (elems[x] != '}') ) {
//                if ((elems[x] != ':') && (flag3 == true) && (elems[x] != '"')) {
//                    argumentThird += elems[x];
//                } else if (elems[x] == ':') {
//                    flag3 = true;
//                }
//                x++;
//            }
//            argumentThird = argumentThird.replaceAll("\\s+","");
//            String[] arguments = {argumentFirst,argumentSecond,argumentThird};
//            return arguments;
//        }
//    }
//
//    public void perfomanceCommands(String string) {
//        if (matchesCommand(string)) {
//            String command = "";
//            Pattern pattern = Pattern.compile("\\{");
//            Matcher matcher = pattern.matcher(string);
//            if (!matcher.find() && (!string.equals("import")) && (!string.equals("add")) && (!string.equals("remove")) && (!string.equals("remove_greater"))) {
//                StartCommandWithoutArguments(string);
//            }else {
//                Pattern pattern1 = Pattern.compile("import");
//                matcher = pattern1.matcher(string);
//                if (matcher.find()){
//                    int test = 0;
//                    int test2 = 0;
//                    for (char elem : string.toCharArray()){
//                        if (elem == '}') {
//                            test += 1 ;
//                        }else if ( elem == '}'){
//                            test2 +=1;
//                        }
//                    }
//                    while (test != 1){
//                        if ((test > 1) || (test2 > 1)) {
//                            System.out.println("Неверный формат ввода команды");
//                            return;
//                        }
//                        Scanner scanner = new Scanner(System.in);
//                        String scan = scanner.nextLine();
//                        string += scan;
//                        test = 0;
//                        for (char elem : string.toCharArray()){
//                            if (elem == '}'){
//                                test += 1;
//                            }
//                        }
//                    }
//                    try {
//                        catVector.Import(getArgument(string)[0]);
//                    }catch ( NullPointerException e){
//                        System.out.println("Неверный адрес файла");
//                    }catch (FileNotFoundException e1){
//                        System.out.println("Проверьте наличие файла или его права доступа");
//                    }catch (ArrayIndexOutOfBoundsException e){
//                        System.out.println("Проверьте файл");
//                    }
//                }else{
//                    int test = 0;
//                    int test2 = 0;
//                    for (char elem : string.toCharArray()){
//                        if (elem == '}') {
//                            test += 1;
//                        }else if (elem == '{'){
//                            test2 += 1;
//                        }
//                    }
//                    while (test != 2){
//                        if (test > 2){
//                            System.out.println("Неправильный формат ввода команды");
//                            return;
//                        }
//                        test = 0;
//                        test2 = 0;
//                        Scanner scanner = new Scanner(System.in);
//                        String scan = scanner.nextLine();
//                        string += scan;
//                        for (char elem : string.toCharArray()){
//                            if (elem == '}') {
//                                test += 1;
//                            }else if (elem == '{'){
//                                test2 += 1;
//                            }
//                        }
//                    }
//                    if (test2 != test){
//                        System.out.println("Неправильный формат ввода команды");
//                        return;
//                    }
//                    for (char elem: string.toCharArray()) {
//                        if (elem != '{') {
//                            command += elem;
//                        } else if (elem == '{') {
//                            command = command.replaceAll("\\s+", "");
//                            break;
//                        }
//                    }
//                    String argumentFirst = "";
//                    String argumentSecond = "";
//                    String argumentThird = "";
//
//
//                    for (String elem : getArgument(string)) {
//                        if (argumentFirst.equals("")) {
//                            argumentFirst = elem;
//                        } else if ((!argumentFirst.equals("")) && (argumentSecond.equals(""))) {
//                            argumentSecond = elem;
//                        } else {
//                            argumentThird = elem;
//                        }
//                    }
//                    String argumentFirstOutput = "";
//                    for (char elem : argumentFirst.toCharArray()){
//                        if ((elem == ' ') && (!argumentFirstOutput.equals(""))){
//                            argumentFirstOutput += elem;
//                        } else if (elem != ' ') {
//                            argumentFirstOutput += elem;
//                        }
//                    }
//
//                    StartCommandWithArguments(command, argumentFirstOutput, argumentSecond, argumentThird);
//                }
//            }
//        }else{
//            System.out.println("Неправильный формат ввода команды");
//        }
//    }
//}
