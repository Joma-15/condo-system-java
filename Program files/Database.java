import java.nio.file.*;
import java.text.DecimalFormat;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
public class Database {
    static BufferedReader reader;
    static BufferedWriter writer;
    static File fileloc;
    static DecimalFormat df;

    //locations
    final static String Pathfile = "D:\\Condominium Mangement System\\Databases\\Tenant Database\\Tenants Lists\\";
    final static String TenantsAccount = "D:\\Condominium Mangement System\\Databases\\Tenant Database\\Tenants Accounts\\Accounts.txt";
    final static String TenantsName = "D:\\Condominium Mangement System\\Databases\\Landlord database\\Tenants.txt";

    boolean is_InDatabase(File fileloc,String username, String password){
        Path path = Paths.get(fileloc.toString());
        try {
            reader = new BufferedReader(new FileReader(path.toFile()));

            String line;
            while ((line = reader.readLine()) != null) {
                String parts[] = line.split("-");
                if (parts[0].equalsIgnoreCase(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;//the method will return false if the condition not succeed
    }

    void WriteInfoToDatabase(String filename, String password, String[] userinfo){
        try {
            fileloc = new File(Pathfile + filename + ".txt");
            if (fileloc.createNewFile() == true) {//if the file has created successfully or it is not exist
                writer = new BufferedWriter(new FileWriter(fileloc,true));
                for (String information : userinfo) {
                    writer.write(information);
                    writer.newLine();
                    writer.flush();
                }
                writer.close();
                writeTenantName(filename);//writing the name of the tenant to the tenantslist
                StoreUserAccount(filename, password);//writing the user and pass to the account of the tenants

                //for ui
                UI_Material.Visuals.loadStatement("Storing data to the database......\n", 5000);
                 //if success register
                 UI_Material.Sounds.playSound(UI_Material.Sounds.sounds[3]);
                 UI_Material.Visuals.delayprint(UI_Material.Visuals.SUCCESS + "Data has been successfully saved" + UI_Material.Visuals.RESET, 50);

            }else{
                System.out.printf("%sTenant already exist on the database%s\n",UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void StoreUserAccount(String username, String password){
         try {
            fileloc = new File(TenantsAccount);
            writer = new BufferedWriter(new FileWriter(fileloc,true));

            writer.write(username + "-" + password);
            writer.newLine();
            writer.close();
         } catch (IOException e) {
            System.out.printf("%sAn error occur while writing data to a file%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
         }
    }

    void writeTenantName(String name){
        try {
            fileloc = new File(TenantsName);
            writer = new BufferedWriter(new FileWriter(fileloc,true));

            writer.write(name);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.printf("%sAn error occur while writing data to a file%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
        }
    }

    List<String> showTenantsName(){
        List<String> names = new ArrayList<>();
        try {
            fileloc = new File(TenantsName);
            reader = new BufferedReader(new FileReader(fileloc));
            System.out.printf("\n%s===========TENANTS LIST==========%s\n",UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);

            String line;
            for(int i = 1; (line = reader.readLine()) != null; i++){
                System.out.println(UI_Material.Visuals.LOADING + i + "." + line + UI_Material.Visuals.RESET);//printing each names in the datafile
                names.add(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.printf("%sAn error occur while reading data to a file%s\n", UI_Material.Visuals.FAILED, UI_Material.Visuals.RESET);
            e.printStackTrace();
        }
        return names;
    }

    static List<String> readCondoUnits(){
        fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\units\\condo_units.txt");
        List<String> condoUnits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileloc))) {
            String line;
            while ((line = reader.readLine()) != null) {
                condoUnits.add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return condoUnits;
    }

    static void displayCondoUnits(List<String> condoUnits) {
        for (int i = 0; i < condoUnits.size(); i++) {
            System.out.println((i + 1) + ". " + condoUnits.get(i));
        }
    }

    static void writeCondoUnits(List<String> condoUnits) throws IOException {
        fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\units\\condo_units.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileloc))) {
            for (String unit : condoUnits) {
                writer.write(unit);
                writer.newLine();
            }
        }
    }

    static void WritePaymentData(String payment){
        try {
            fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Payment\\TenantPayment.txt");
            writer = new BufferedWriter(new FileWriter(fileloc,true));

            writer.write(payment);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showTenantInformation(String Tenantname){
        System.out.printf("\n%s=============TENANT INFORMATION============%s\n",UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        try {
            fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Tenants Lists\\" + Tenantname + ".txt");
            reader = new BufferedReader(new FileReader(fileloc));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();//for spacing
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double StorePayment(String filename, String deposit, double prev_money){
        try {
            fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Payment\\" + filename + ".txt");
                prev_money += Double.parseDouble(deposit);
                fileloc.createNewFile();
                writer = new BufferedWriter(new FileWriter(fileloc));
                writer.write(Double.toString(prev_money));
                writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prev_money;
    }

    static double ShowCurrentMoney(String filename){
        double currentmoney = 0;
        DecimalFormat df = new DecimalFormat("#,##0.00");

        try {
            fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Payment\\" + filename + ".txt");
            reader = new BufferedReader(new FileReader(fileloc));

            String line;
            while ((line = reader.readLine()) != null) {
                currentmoney += Double.parseDouble(line);
            }
            String formattedmoney = df.format(currentmoney);
            if (currentmoney >= 30000000) {
                System.out.println("Available Balance : " + UI_Material.Visuals.SUCCESS + '\u0024' + formattedmoney + UI_Material.Visuals.RESET + "\n");
            }else{
                System.out.println("Available Balance : " + UI_Material.Visuals.FAILED + '\u0024' + formattedmoney + UI_Material.Visuals.RESET + "\n");
            }
        } catch (IOException e) {
            System.out.println("Available Balance : " + UI_Material.Visuals.FAILED + '\u0024' + 0 + UI_Material.Visuals.RESET + "\n");
        }
        return currentmoney;
    }

    static void DisplayCondoInformation(String condoname){
        System.out.printf("\n%s============CONDO INFORMATION=========%s\n", UI_Material.Visuals.BANNER, UI_Material.Visuals.RESET);
        String path = "D:\\Condominium Mangement System\\Databases\\Tenant Database\\units\\" + condoname + ".txt";
        fileloc = new File(path);

        try {
            reader = new BufferedReader(new FileReader(fileloc));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("%s==========================================================%s\n", UI_Material.Visuals.BANNER,UI_Material.Visuals.RESET);
    }

  static void WriteTransactData(String username,double payment){
    fileloc = new File("D:\\Condominium Mangement System\\Databases\\Tenant Database\\Payment\\" + username +".txt");
    DecimalFormat df = new DecimalFormat(".00");

    try {
        writer = new BufferedWriter(new FileWriter(fileloc));
        writer.write(df.format(payment));
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  static void addUserPaymentData(double purchase){
    fileloc = new File("D:\\Condominium Mangement System\\Databases\\Landlord database\\TenantPayment.txt");
    df = new DecimalFormat(".00");
    try {
        writer = new BufferedWriter(new FileWriter(fileloc,true));
        writer.write(df.format(purchase));
        writer.newLine();
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  static void ReadUserPaymentData(){
     fileloc = new File("D:\\Condominium Mangement System\\Databases\\Landlord database\\TenantPayment.txt");
     List<Double> WithdrawAmount = new ArrayList<>();
     double totalAmount = 0;
     df = new DecimalFormat("#,##0.00");

     try {
        reader = new BufferedReader(new FileReader(fileloc));

        String line;
        for (int i = 0; (line = reader.readLine()) != null; i++) {
            WithdrawAmount.add(Double.parseDouble(line));
            totalAmount += WithdrawAmount.get(i);
        }
        double amoundDeduction = DeductCommisionRate(totalAmount);
        System.out.println("Your available balance is " + UI_Material.Visuals.SUCCESS +'\u0024' + df.format(amoundDeduction) + UI_Material.Visuals.RESET + "\n");
        reader.close();
     } catch (Exception e) {
        System.out.println("Your available balance is " + UI_Material.Visuals.FAILED +'\u0024' + 0 + UI_Material.Visuals.RESET + "\n");
     }
  }
  static double DeductCommisionRate(double TotalAmount){
    df = new DecimalFormat("#,##0.00");
    double deduction = (TotalAmount * 0.03) ;
    double deductrate = TotalAmount - deduction;

    String newnumFormat = df.format(deductrate);

    try {
        fileloc = new File("D:\\Condominium Mangement System\\System comission\\profit.txt");
        writer = new BufferedWriter(new FileWriter(fileloc));
        writer.write("\u20B1" + newnumFormat);
        writer.newLine();
        writer.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return deduction;
  }
}