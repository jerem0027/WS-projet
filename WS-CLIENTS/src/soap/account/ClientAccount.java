package soap.account;

import java.rmi.RemoteException;

import soap.account.AccountStub;
import soap.account.AccountStub.Register;
import soap.account.AccountStub.Info;

public class ClientAccount {
	
	String name;
	String pwd;
	
	public ClientAccount(String name, String pwd){
		this.name = name;
		this.pwd = pwd;
	}
	
    public void postRegister() throws RemoteException{
    	AccountStub acc = new AccountStub();
		Register r = new Register();
		r.setName(this.name);
		r.setPwd(this.pwd);
		System.out.println(acc.register(r).get_return());
    }

    public void getInfo() throws RemoteException{
    	AccountStub acc = new AccountStub();
		Info i = new Info();
		i.setName(this.name);
		i.setPwd(this.pwd);
        display(acc.info(i).get_return());
    }
    
    public void display(String[] tab) {
    	for (String e:tab)
			System.out.println(e);
    }

	public void runClient() throws RemoteException {
		System.out.println("Client -- Creation compte - URL: http://localhost:8080/WS-SOAP-Project/services/Account/register?name="+this.name+"&pwd="+this.pwd+"\n");
		this.postRegister();
        System.out.println();
        System.out.println();
        
        System.out.println("Client -- Get user Informations - URL: URL: http://localhost:8080/WS-SOAP-Project/services/Account/info?name="+this.name+"&pwd="+this.pwd+"\n");
        this.getInfo();
        System.out.println();
        System.out.println();

        System.out.println("");
	}

}
