import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class TenantDash extends Database {
    static Scanner sc = new Scanner(System.in);
    // Location where the landlord account is located
    final File fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Tenants Accounts\\Accounts.txt");
    static String username;
    static TenantDash dash = new TenantDash();
    static DecimalFormat f = new DecimalFormat(".00");
    static double money;
    static double[] CondoPrice = {60000000,30000000,40000000,55000000,65000000};
    static double prev_Money;
    static double prev_purchase;

    //container to hold the current thread will use to stop thread running in the background
    static Thread threadcontainer;

    void ShowTenantDash() {
        while (true) {
            try {
                System.out.printf("\n\n%s========TENANT DASH==========%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
                System.out.print("1. Register\n" + "2. Login\n" + "3. Exit\n");
                System.out.print("Option: ");
                int opt = sc.nextInt();
                sc.nextLine(); // to not skip the line immediately

                switch (opt) {
                    case 1:
                        register();
                        break;
                    case 2:
                        Login();
                        break;
                    case 3:
                        CondominiumManagementSystem.main(null);//will return to the main dash
                        System.exit(0);
                        break;
                    default:
                        System.out.printf("%sPlease choose within the choices%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
                        continue;
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.printf("%sInvalid input%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
            }
        }
    }

    void Login() {
        System.out.printf("\n%s========TENANT LOGIN PAGE====%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        System.out.print("Enter your username        : ");
        username = sc.nextLine();
        System.out.print("Enter your password        : ");
        String password = sc.nextLine();

        if (is_InDatabase(fileloc, username, password)) {
            UI_Material.Sounds.playSound(UI_Material.Sounds.sounds[0]);
            UI_Material.Visuals.delayprint(UI_Material.Visuals.SUCCESS + "Login Successfully\n" + UI_Material.Visuals.RESET, 50);
            ShowMainPage();
        } else {
            UI_Material.Sounds.playSound(UI_Material.Sounds.sounds[1]);
            UI_Material.Visuals.delayprint(UI_Material.Visuals.FAILED + "Login Failed\n" + UI_Material.Visuals.RESET, 50);
            ShowTenantDash();
        }
      }

    void register() {
        DataValidator val = new DataValidator();
        while (true) {
            try {
                System.out.printf("\n%s====TENANT REGISTRATION PAGE===%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
                String username = val.AskForName(); // serve as a name of the txt file
                String password = val.AskForPassword(); // use to access user account

                String[] informations = {
                    "Username       : " + username,
                    "Civil status   : " + val.AskForCivilStat(),
                    "Occupation     : " + val.AskForOccupation(),
                    "Age            : " + val.AskForAge(),
                    "Nationality    : " + val.AskForNationality(),
                    "Contact Number : " + val.AskForContactNumber(),
                    "Sex            : " + val.askForSex(),
                    "Email          : " + val.AskForEmail(),
                    "Monthly Salary : " + val.AskForSalary()
                };
                WriteInfoToDatabase(username, password, informations); // writing the data to the userinfo
                break; // break the loop after successful registration

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.printf("%sInvalid input%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
            }
        }
    }

    void ShowMainPage() {
     while(true){
       try{
        System.out.printf("%s\n==========TENANT MAIN PAGE=========%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        System.out.println("1. Purchase Condo\n" + "2. Deposit Money\n" + "3. Exit");
        System.out.print("Option: ");
        int opt = sc.nextInt();

        switch (opt) {
            case 1:
                CheckBalance();
                break;
            case 2:
                 Money.AskToDeposit();
                 break;
            case 3:
                CondominiumManagementSystem.main(null);
                 break;
            default:
                System.out.printf("%sNot within the choices try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
                continue;
           }
        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.printf("%sInvalid input try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        }
      }
    }

    static class Money{
        static void AskToDeposit(){
         while(true){
           try{
            System.out.printf("\n%s================FINANCE PAGE======================%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
            System.out.print("How much money you want to deposit?                  : ");
            double deposit = sc.nextDouble();
            sc.nextLine();
            String ConvertedMoney = f.format(deposit).toString();

            System.out.print("Are you sure you want to deposit that amound Yes/No  : ");
            String decison = sc.nextLine();

            if (decison.equalsIgnoreCase("yes")) {
                UI_Material.Visuals.loadStatement("Depositing money to the database....", 5000);
                WritePaymentData(ConvertedMoney);//writing the payment data to the database
                prev_Money = StorePayment(username, ConvertedMoney, prev_Money);
                System.out.printf("\n%sMoney has been deposited%s\n", UI_Material.Visuals.SUCCESS, UI_Material.Visuals.RESET);
                break;

            }else if (decison.equalsIgnoreCase("no")) {
                System.out.printf("%sProgram cancelled%s\n", UI_Material.Visuals.LOADING, UI_Material.Visuals.LOADING);
                dash.ShowMainPage();;//returning to the login page
            }else{
                System.out.printf("%sInvalid choice try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
            }
           }catch(InputMismatchException e){
              sc.nextLine();
              System.out.printf("%sInvalid input try again\n%s", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
           }
          }
        }
    }

    static class Condo{
         static void DisplayCondoUnit(){
          while(true){
           System.out.printf("\n%s==============CONDO LISTS============%s\n",UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
           System.out.printf("%sWorth 30 Million Minimum Purchase%s\n",UI_Material.Visuals.LOADING, UI_Material.Visuals.RESET);
           TenantDash dash = new TenantDash();

          try{
            List<String> condoUnits = readCondoUnits();
            String condos[] = {"unit1","unit2","unit3","unit4","unit5"};

            displayCondoUnits(condoUnits);
            System.out.print("purchase: ");
            int unitNumber = sc.nextInt();
            sc.nextLine();

            if (unitNumber > 0 && unitNumber <= condoUnits.size()) {
                String UnavailableText = UI_Material.Visuals.FAILED + "Unavailable" + UI_Material.Visuals.RESET;
                String selectedUnit = condoUnits.get(unitNumber - 1);
                DisplayCondoInformation(condos[unitNumber-1]);
                LoadImageData(unitNumber -1 );//minus one to pair with the array
                System.out.print("Are you sure you want to proceed to this transaction?       : ");
                String decision = sc.nextLine();
                ConfirmationUpdate(decision, unitNumber);

                if (selectedUnit.contains("Available")) {
                    condoUnits.set(unitNumber - 1, selectedUnit.replace("Available", UnavailableText));
                    UI_Material.Visuals.loadStatement("transfering the payment data...", 5000);
                    writeCondoUnits(condoUnits);
                    System.out.printf("\n%sCondo unit " + unitNumber + " has been purchased.%s\n",UI_Material.Visuals.SUCCESS, UI_Material.Visuals.RESET);
                    dash.ShowMainPage();
                    break;
                } else {
                    System.out.printf("%sCondo unit " + unitNumber + " is already not available.\n%s", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
                    dash.ShowMainPage();
                    break;
                }
            } else {
                System.out.printf("%sInvalid condo unit number.%s\n", UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
                dash.ShowMainPage();
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());

        } catch (InputMismatchException e){
            sc.nextLine();
            System.out.printf("%sInvalid input try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        }
      }
    }
    static Thread LoadImageData(int unitnumber){
        Thread units[] = new Thread[5];

        //adding each thread manually
        units[0] = new Thread(new UI_Material.Unit1());
        units[1] = new Thread(new UI_Material.Unit2());
        units[2] = new Thread(new UI_Material.Unit3());
        units[3] = new Thread(new UI_Material.Unit4());
        units[4] = new Thread(new UI_Material.Unit5());

        units[unitnumber].setDaemon(true);
        units[unitnumber].start();

        return units[unitnumber];
    }
  }

  static void CheckBalance(){
    double fixprice = 30000000;

    money = ShowCurrentMoney(username);
    if (money >= fixprice) {
        Condo.DisplayCondoUnit();
    }else if(money < fixprice)
        System.out.printf("%sYou are not eligable to purchase a condo please deposit to proceed%s",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
        dash.ShowMainPage();
    }

    static void ConfirmationUpdate(String decision, int unitnumber){
      try{
        if (decision.equalsIgnoreCase("yes")) {
            //to ensure na walang utang ang tenant
            double DeducAmount = money - CondoPrice[unitnumber-1];
            if (DeducAmount >= 0) {
                addUserPaymentData(CondoPrice[unitnumber-1]);
                WriteTransactData(username,DeducAmount);//username pass as parameter val to define the path where to override payment
            }else{
                System.out.printf("%sNot enough balance to proceed to this transaction%s\n",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
                dash.ShowMainPage();
            }
        }else if (decision.equalsIgnoreCase("no")) {
            System.out.printf("%sprogram exit%s\n",UI_Material.Visuals.LOADING, UI_Material.Visuals.RESET);
            dash.ShowMainPage();

        }else{
           throw new InputMismatchException();
        }
      }catch(InputMismatchException e){
        System.out.printf("%sQuestion is answerable by yes or no%s\n", UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
        dash.ShowMainPage();
      }
   }
}






