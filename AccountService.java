/**
 * 
 */
package database;

import beans.AccountBean;
import model.Account;

/**
 * 
 */
public class AccountService {
	private AccountDAO accountDAO = new AccountDAO();
	
	public void createAccount(Account a) {
		accountDAO.addAccount(a);
	}
	
	public boolean registerAccount(AccountBean user) {
		if (accountDAO.findAccountByUsername(user.getUsername()) != null) {
            System.out.println("Tên đăng nhập đã tồn tại.");
            return false;
		}
		
		return accountDAO.addAccount(user);
	}
	
	public boolean loginAccount(String username, String password) {
		AccountBean user = accountDAO.findAccountByUsername(username);
		
		if (user == null) return false;
		
		return user.checkPassword(password);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
