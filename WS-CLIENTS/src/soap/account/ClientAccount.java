package soap.account;

import java.rmi.RemoteException;

import soap.account.AccountStub.Info;
import soap.account.AccountStub.Register;

public class ClientAccount {

	String name;
	String pwd;
	public int idBook;

	public ClientAccount(String name, String pwd){
		this.name = name;
		this.pwd = pwd;
	}

	public void register() throws RemoteException{
		AccountStub acc = new AccountStub();
		Register r = new Register();
		r.setName(this.name);
		r.setPwd(this.pwd);
		System.out.println("Client -- Creation compte - URL: http://localhost:8080/WS-SOAP-Project/services/Account/register?name="+this.name+"&pwd="+this.pwd+"\n");
		System.out.println(acc.register(r).get_return());
		System.out.println();
		System.out.println();
	}

	public void getInfo() throws RemoteException{
		AccountStub acc = new AccountStub();
		Info i = new Info();
		i.setName(this.name);
		i.setPwd(this.pwd);
		System.out.println("Client -- Get user Informations - URL: URL: http://localhost:8080/WS-SOAP-Project/services/Account/info?name="+this.name+"&pwd="+this.pwd+"\n");
		display(acc.info(i).get_return());
		if(acc.info(i).get_return().length > 2)
			this.idBook = Integer.parseInt(acc.info(i).get_return()[2].split("|")[0]);
		System.out.println();
		System.out.println();
	}

	public void display(String[] tab) {
		for (String e:tab)
			System.out.println(e);
	}

}
