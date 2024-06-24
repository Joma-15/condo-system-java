import java.util.Scanner;
public class CondominiumManagementSystem {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      try{
        System.out.printf("%s\n===========MAIN DASH===========\n%s", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        System.out.println("1.Landlord\n" + "2.Tenant\n" + "3.Exit");
        System.out.print("option : ");
        int option = sc.nextInt();
        sc.nextLine();//to clear buffered lines

        if (option == 1) {
             LandlordDash dash1 = new LandlordDash();
            dash1.ShowLandlordDash();
            break;
        }else if (option == 2) {
          TenantDash dash2 = new TenantDash();
          dash2.ShowTenantDash();
          break;
        }else if (option == 3) {
          System.out.printf("%sProgram stoped%s\n", UI_Material.Visuals.LOADING, UI_Material.Visuals.RESET);
          System.exit(0);
          break;
        }else{
          System.out.printf("%sPlease choose within the choices%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
          continue;
        }
      } catch (Exception e) {
        sc.nextLine();
        System.out.printf("%sInvalid input try again!%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
      }
    }
    sc.close();
  }
}