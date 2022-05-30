package infosys.poc.ldap.controller;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import infosys.poc.ldap.model.User;

@Controller
public class AdServerConnectorController {

	@RequestMapping(method = RequestMethod.POST, path = "test")
	@ResponseBody
	public String test(@RequestBody String test) {

		return test;

	}

	@RequestMapping(method = RequestMethod.POST, path = "login")
	@ResponseBody
	public String adConnector(@RequestBody User user) throws NamingException {

		Hashtable<String, String> environment = new Hashtable<String, String>();

		try {

			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, "ldap://127.0.0.1:10389");
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PRINCIPAL, "cn=" + user.getUserName() + ", ou=users , ou=system");
			environment.put(Context.SECURITY_CREDENTIALS, user.getPassword());

			DirContext adminContext = new InitialDirContext(environment);

			System.out.println("User Authenticated");

			return "User Authenticated";

		} catch (AuthenticationException e) {

			System.out.println("Exception: " + e.getMessage());

			return e.getMessage();

		}

	}

}
