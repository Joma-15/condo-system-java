import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.io.File;
public class LandlordDash extends Database{

    private String username, password;
    Scanner sc = new Scanner(System.in);
    //location where the account located
    final File LandLord_AccountPath = new File("D:\\Condominium Mangement System\\Databases\\Landlord database\\LandLord_Account.txt");

    void ShowLandlordDash(){
        System.out.printf("\n%s=========LANDLORD DASH===============%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        System.out.print("Username         : ");
        username = sc.nextLine();
        System.out.print("Password         : ");
        password = sc.nextLine();

        if (is_InDatabase(LandLord_AccountPath,username, password)) {
            UI_Material.Sounds.playSound(UI_Material.Sounds.sounds[0]);
            UI_Material.Visuals.delayprint(UI_Material.Visuals.SUCCESS + "Login successfully\n\n" + UI_Material.Visuals.RESET, 50);
            ShowMainDash();
        }else{
            UI_Material.Sounds.playSound(UI_Material.Sounds.sounds[1]);
            UI_Material.Visuals.delayprint(UI_Material.Visuals.FAILED + "Login Failed\n\n" + UI_Material.Visuals.RESET, 50);
            CondominiumManagementSystem.main(null);
         }
    }


    void ShowMainDash(){
      while(true){
        try{
        System.out.printf("%s============MAIN LANDLORD DASH=============%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        System.out.println("1.Show list of tenants/buyers\n2.Collect Payment\n" + "3.Check available balance\n" + "4.Exit");
        System.out.print("Option: ");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                DisplayTenantsList();
                System.exit(0);
                break;
            case 2:
                askedToWithdraw();
                break;
            case 3:
                ReadUserPaymentData();
                break;
            case 4:
                 CondominiumManagementSystem.main(null);//return to main page
                 System.exit(0);
                 break;
            default:
            throw new InputMismatchException();
        }

     }catch(InputMismatchException e){
        sc.nextLine();
        System.out.printf("%sInvalid input try again%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.FAILED);
     }
    }
   }

 void DisplayTenantsList(){
    List<String> TenantsName = showTenantsName();
    System.out.print("\nEnter the Tenant Name        : ");
    String name = sc.nextLine();
    boolean isexist = false;

    for (int i = 0; i < TenantsName.size(); i++) {
        if (name.equalsIgnoreCase(TenantsName.get(i))) {
            showTenantInformation(name);
            isexist = true;
            ShowMainDash();
            break;
        }
    }
    if (!isexist) {
        System.out.printf("%sTenants Doesn't exist%s\n\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        ShowMainDash();
    }
   }

   void askedToWithdraw(){
    System.out.printf("\n%s==============================WITHDRAWAL PAGE====================%s\n",UI_Material.Visuals.BANNER,UI_Material.Visuals.RESET);
      System.out.print("Do you want to withdraw your account        : ");
      String decision = sc.nextLine();

      if (decision.equalsIgnoreCase("yes")) {
        checkFileExist();
      }else if(decision.equalsIgnoreCase("No")){
        System.out.printf("%sProgram exit%\n",UI_Material.Visuals.LOADING, UI_Material.Visuals.RESET);
        ShowMainDash();
      }else{
        System.out.printf("%sInvalid input%s\n",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
        ShowMainDash();
      }
   }

   void checkFileExist(){
    File paymentfile = new File("D:\\Condominium Mangement System\\Databases\\Landlord database\\TenantPayment.txt");

    if (!paymentfile.exists()) {
       System.out.printf("%sNo available balance to withdraw%s\n\n",UI_Material.Visuals.FAILED,UI_Material.Visuals.RESET);
    }else{
      UI_Material.Visuals.loadStatement("Withrawing the amount fee", 3000);
      paymentfile.delete();
      System.out.printf("\n%sAmount has been withdraw successfully%s\n",UI_Material.Visuals.SUCCESS,UI_Material.Visuals.RESET);
      ShowMainDash();
    }
   }
}