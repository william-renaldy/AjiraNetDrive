import java.util.*;


class InvalidSyntaxException extends Exception
{
    @Override
    public String getMessage() {
        return "Error: Invalid command syntax.";
    }
}

class NameExistException extends Exception
{
    @Override
    public String getMessage() {
        return "Error: That name already exists";
    }
}


interface Devices{
    public Devices setStrength(int strength);
    public void setConnection(Object connection);
    public Devices getInstance();

}


class Computer implements Devices{

    String name;
    int strength;
    List<Object> connections;

    public Computer(String name)
    {
        this.name = name;
        this.strength = 5;
        this.connections = new ArrayList<Object>();
    }

    public Devices setStrength(int strength){
        this.strength = strength;

        return this;
    }

    public void setConnection(Object connectons){
        connections.add(connectons);
    }

    @Override
    public Devices getInstance() {
        return this;
    }


}

class Repeater implements Devices{

    String name;
    int strength;
    List<Object> connections;

    public Repeater(String name)
    {
        this.name = name;
        this.strength = 5;
        this.connections = new ArrayList<Object>();
    }

    public Devices setStrength(int strength){
        this.strength = strength;

        return this;
    }

    public void setConnection(Object connectons){
        connections.add(connectons);
    }

    public Devices getInstance()
    {
        return this;
    }

}



class Connection{

    List<Object> network;
    List<String> elements;

    

    public Connection(){
        this.network = new ArrayList<Object>();
        this.elements = new ArrayList<String>();
    }

    public void addConnection(Object new_entry,String name) throws NameExistException
    {
        if(this.elements.contains(name))
            throw new NameExistException();
        this.network.add(new_entry);
        this.elements.add(name);
    }

    public boolean isExist(String name)
    {
        return this.network.contains(name);
    }

    public boolean isComputer(String name) {
        int index = this.elements.indexOf(name);

        Object el = this.network.get(index);

        return el.getClass() == Computer.class;
    }

    public int getIndex(String name)
    {
        return this.elements.indexOf(name);
    }


    
}

public class AjiraNet {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Connection con = new Connection();

        while(true)
        {
            String command = sc.nextLine();

            String[] c_array = command.split(" ");

            if(c_array[0].equalsIgnoreCase("ADD"))
            {
                try{
                    if(c_array.length != 3 || (!c_array[1].equalsIgnoreCase("computer") && !c_array[1].equalsIgnoreCase("repeater")))
                        throw new InvalidSyntaxException();

                    if(c_array[1].equalsIgnoreCase("computer"))
                    {
                        con.addConnection(new Computer(c_array[2]),c_array[2]);
                    }
                    
                    else
                    {
                        con.addConnection(new Repeater(c_array[2]),c_array[2]);
                    }



                    System.out.println("Successfully added " + c_array[2]);
                }
                catch (InvalidSyntaxException e)
                {
                    System.out.println(e.getMessage());
                }
                catch(NameExistException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            else if(c_array[0].equalsIgnoreCase("SET_DEVICE_STRENGTH"))
            {
                try{
                    if(con.isExist(c_array[1]) && con.isComputer(c_array[1]))
                    {
                        try{
                            int strength = Integer.parseInt(c_array[2]);

                            int index = con.getIndex(c_array[2]);

                            con.network.set(index,(Object)((Computer) con.network.get(index)).setStrength(strength));
                            // computer.setStrength(strength);
                        }
                        catch(NumberFormatException e)
                        {
                            throw new InvalidSyntaxException();
                        }
                    }
                }
                catch(InvalidSyntaxException e)
                {
                    System.out.println(e.getMessage());
                }
            }

        }
        
    }
}