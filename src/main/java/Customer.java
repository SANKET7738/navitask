public class Customer {
    String name;
    int id;
    private static int uniqueId = 0;

    public Customer(String fullName){
        name = fullName;
        id = getUniqueId();
    }

    private static int getUniqueId(){
        int id = uniqueId;
        return  ++id;
    }


}
