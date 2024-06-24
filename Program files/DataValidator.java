import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataValidator {
        Scanner sc = new Scanner(System.in);
        Pattern pattern;
        Matcher m;


    String AskForName(){
        String username;

        System.out.print("Enter your full name           : ");
        username = sc.nextLine();

        return capitalizeAllNames(username);

    }
    String AskForPassword(){
        while(true){
        System.out.print("Create a password              : ");
        String password = sc.nextLine();

        pattern = Pattern.compile("^.{8,}$");
        m = pattern.matcher(password);

        if (m.find()) {
            return password;
        }else{
            System.out.printf("%sPassword must be 8 characters above%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
             continue;
        }
    }
  }


   String AskForCivilStat(){
    while(true){
    System.out.println("MARRIED || SINGLE || WIDOWED");
    System.out.print("Civil status                   : ");
    String choice = sc.nextLine().toUpperCase();

    pattern = Pattern.compile("MARRIED|SINGLE|WIDOWED");
    m = pattern.matcher(choice);

    if (m.matches()) {
        return choice;
    }else{
        System.out.printf("%sNot within choices try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        continue;
    }
   }
 }

   String AskForAge(){
     while (true) {
        try {
            System.out.print("Age                            : ");
            int age = sc.nextInt();
            sc.nextLine();

            if (age >= 18) {
                return Integer.toString(age);//returning the string verion of age
            }else{
                System.out.printf("%sNot enough age%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
                continue;
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.printf("%sInvalid input%s\n",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
        }
    }
  }

   String AskForOccupation(){
    System.out.print("Occupation                     : ");
    String occupation  = sc.nextLine();

    String capitalLet = occupation.substring(0,1).toUpperCase() + occupation.substring(1);
    return capitalLet;
  }

  String askForSex(){
   while(true){
    System.out.println("MALE || FEMALE");
    System.out.print("Sex                            : ");
    String sex = sc.nextLine().toUpperCase();

    pattern = Pattern.compile("MALE|FEMALE");
    m = pattern.matcher(sex);

    if (m.matches()) {
        return sex;
    }else{
        System.out.printf("%sInvalid Sex%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
    }
  }
 }

 String AskForNationality(){
    System.out.print("Nationality                    : ");
    String nationality = sc.nextLine();

    return nationality.substring(0,1).toUpperCase() + nationality.substring(1);
 }

 String AskForEmail(){
  while(true){
    System.out.print("Email address                  : ");
    String email = sc.nextLine();

    pattern = Pattern.compile(".+"+"@gmail.com");
        Matcher m = pattern.matcher(email);

        if (m.matches()) {
            return email;
        }else{
            System.out.printf("%sInvalid email address%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        }
      }
   }


   String AskForContactNumber(){
    while(true){
     System.out.print("Contact Number                 : ");
     String contact = sc.nextLine();

     pattern = Pattern.compile("09" + "\\d{9}");
     m = pattern.matcher(contact);

     if (m.matches()) {
        return contact;
     }else{
         System.out.printf("%sInvalid Contact Number%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
         continue;
     }
   }
 }

 String AskForSalary(){
  while(true){
   try{
     System.out.print("Montly Salary                  : ");
     double salary = sc.nextDouble();sc.nextLine();
     DecimalFormat df = new DecimalFormat(".00");
     String MonthSalary = df.format(salary).toString();

     if (salary >= 10000) {
         return MonthSalary;
     }else{
         System.out.printf("%sNot eligable as a buyer salary must be 10000 above%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
     }
    }catch(InputMismatchException e){
         sc.nextLine();
         System.out.printf("%sInvalid input%s\n",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
     }
   }
 }
 public static String capitalizeAllNames(String input) {
    String[] words = input.split("\\s+"); // Split by whitespace
    StringBuilder capitalized = new StringBuilder();

    for (String word : words) {
        if (word.length() > 0) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                       .append(word.substring(1).toLowerCase())
                       .append(" ");
        }
    }
    // Trim the trailing space
    return capitalized.toString().trim();
  }
}


