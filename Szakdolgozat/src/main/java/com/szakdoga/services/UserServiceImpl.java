package com.szakdoga.services;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.AccessTokenEntity;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.RefreshTokenEntity;
import com.szakdoga.entities.Role;
import com.szakdoga.entities.User;
import com.szakdoga.entities.UserActivation;
import com.szakdoga.entities.DTOs.UserDTO;
import com.szakdoga.exceptions.ActivationExpiredException;
import com.szakdoga.exceptions.EmailAddressAlreadyRegisteredException;
import com.szakdoga.exceptions.MissingUserInformationException;
import com.szakdoga.exceptions.NewPasswordIsMissingException;
import com.szakdoga.exceptions.NotOwnUsernameException;
import com.szakdoga.exceptions.OldPasswordDoesNotMatchException;
import com.szakdoga.exceptions.OldPasswordIsMissingException;
import com.szakdoga.exceptions.RoleDoesNotExistsException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.exceptions.UserIsNotActivatedException;
import com.szakdoga.exceptions.UserameAlreadyRegisteredException;
import com.szakdoga.exceptions.UsernameIsMissingException;
import com.szakdoga.exceptions.WrongActivationCodeException;
import com.szakdoga.repos.AccessTokenRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.BuyerRepository;
import com.szakdoga.repos.RefreshTokenRepository;
import com.szakdoga.repos.RoleRepository;
import com.szakdoga.repos.UserActivationRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.utils.EmailUtil;
import com.szakdoga.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private UserActivationRepository userActivationRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailUtil emailSender;
	@Autowired
	AccessTokenRepository accTokenRepository;
	@Autowired
	RefreshTokenRepository refTokenRepository;
	@Autowired
	private HttpServletRequest request;

	@Value("${server.port}")
	private String port;

	@Value("${server.contextPath}")
	private String serverContext;
	
	@Override
	public void removeUser(String username) {
		deleteUsersToken(username);
	}

	@Override
	public void removeAllUsers() {

	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void checkIfActivated(User user) {
		if (!user.isActivated())
			throw new UserIsNotActivatedException("User is not activated.");
	}

	public User checkUserValues(String username) {
		if (username == null)
			throw new UsernameIsMissingException("Username is missing !");
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The username given does not exists!");

		checkIfActivated(user);
		return user;
	}

	@Override
	public void register(UserDTO userDTO) {
		
		if( userDTO.getUsername() == null || userDTO.getEmail()  == null || 
			userDTO.getPassword() == null || userDTO.getRole() == null)
			throw new MissingUserInformationException("Userinformation is missing");
		
		if( userDTO.getUsername().isEmpty() || userDTO.getEmail().isEmpty() || 
				userDTO.getPassword().isEmpty() || userDTO.getRole().isEmpty())
				throw new MissingUserInformationException("Userinformation is missing");
			
		checkIfAlreadyInDb(userDTO.getUsername(), userDTO.getEmail());

		List<String> validRoles = roleRepository.findAll().stream().map( r-> r.getName() ).collect(Collectors.toList());
		
		if( !validRoles.contains(userDTO.getRole()) )
			throw new RoleDoesNotExistsException("The role does not exists !");
		
		User user = new User();
		Role role = roleRepository.findByName(userDTO.getRole());

		user.setEmail(userDTO.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setUsername(userDTO.getUsername());

		user.addRole(role);

		userRepository.save(user);

		Seller seller = getDefaultSeller(user);
		Buyer buyer = getDefaultBuyer(user);
		UserActivation userActivation = getDefaultUserActivation(user);

		sellerRepository.save(seller);
		buyerRepository.save(buyer);
		userActivationRepository.save(userActivation);

		emailSender.sendSimpleMessage(userDTO.getEmail(), "Registration",
				getRegistrationText(userDTO.getUsername(), userActivation.getActivationString()));
	}

	public void activateUser(String activationCode) {
		UserActivation activation = userActivationRepository.findByActivationString(activationCode);

		if (activation == null)
			throw new WrongActivationCodeException("The given activation code does not exists");

		Date thisMoment = new Date();

		if (activation.getExpiration_date().before(thisMoment)) {
			userActivationRepository.delete(activation);
			userRepository.delete(activation.getUser().getId());
			throw new ActivationExpiredException("The activation date has expired!");
		}

		// findByID nem ment emiatt ez alatt stream-el oldottam meg
		// User user = userRepository.findByID(activation.getUser().getId());
		List<User> lista = userRepository.findAll();
		User user = lista.stream().filter(e -> e.getId().equals(activation.getUser().getId())).findFirst().get();
		user.setActivated(true);

		userActivationRepository.delete(activation);
		userRepository.save(user);
	}

	private void deleteUsersToken(String username) {
		List<AccessTokenEntity> accTokens = accTokenRepository.findAll();

		Iterator<AccessTokenEntity> accTokenIterator = accTokens.iterator();
		while (accTokenIterator.hasNext()) {
			AccessTokenEntity accToken = accTokenIterator.next();
			RefreshTokenEntity refToken = refTokenRepository.findByTokenId(accToken.getRefreshToken());
			refTokenRepository.delete(refToken);
			accTokenIterator.remove();
		}
		
	}

	private void checkIfAlreadyInDb(String username, String email) {
		// TODO: error message localization

		if (userRepository.findByUsername(username) != null)
			throw new UserameAlreadyRegisteredException("Already registered with the username: " + username);

		if (userRepository.findByEmail(email) != null)
			throw new EmailAddressAlreadyRegisteredException("Already registered with an email: " + email);
	}

	private String getRegistrationText(String username, String activationCode) {
		StringBuilder sb = new StringBuilder();
		String link = "localhost:" + port + serverContext + "/user" + "/registered/activation/" + activationCode;

		sb.append("<h1>" + "Üdvözöljük " + username + "!" + "</h1></br>");
		sb.append("<p>" + "Köszöntjük oldalunkon." + "</p>");
		sb.append("</br>" + "A linkre kattintva tudja regisztrációját megerősíteni: " + "</br>" + link);
		// sb.append("<a href='"+link+"'>"+ "Aktiválás"+"</a>");

		return sb.toString();
	}

	private Seller getDefaultSeller(User user) {
		Seller offerer = new Seller();

		offerer.setAboutMe("Empty");
		offerer.setCategories(null);
		offerer.setFirstName("John");
		offerer.setLastName("Doe");
		offerer.setProfileImage(null);
		offerer.setUser(user);

		return offerer;
	}

	private Buyer getDefaultBuyer(User user) {
		Buyer seeker = new Buyer();

		seeker.setAboutMe("Empty");
		seeker.setCategories(null);
		seeker.setFirstName("John");
		seeker.setLastName("Doe");
		seeker.setProfileImage(null);
		seeker.setUser(user);

		return seeker;
	}

	private UserActivation getDefaultUserActivation(User user) {
		UserActivation activation = new UserActivation();

		Date today = new Date();
		Date plusOneDay = new Date(today.getTime() + (1000 * 60 * 60 * 24));

		activation.setActivationString(Utils.randomString(20));
		activation.setExpiration_date(plusOneDay);
		activation.setUser(user);

		return activation;
	}

	@Override
	public void changePassword(UserDTO userDTO) {
		if(userDTO ==null)
			throw new UsernameIsMissingException("No data found!");
		
		User user = userRepository.findByUsername(userDTO.getUsername());
		
		if(user==null)
			throw new UsernameIsMissingException("Username is missing!");
		
		if(!user.getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");
		
		checkIfActivated(user);
		
		if(userDTO.getNewPassword() ==null)
			throw new NewPasswordIsMissingException("No new password was found!");
		
		if(userDTO.getPassword() ==null)
			throw new OldPasswordIsMissingException("No old password was found!");
		
		String givenOldPasswordEncripted =  bCryptPasswordEncoder.encode(userDTO.getPassword()); 
		String givenNewPasswordEncripted = bCryptPasswordEncoder.encode(userDTO.getNewPassword());
		
		String oldPasswordEncripted = user.getPassword();
		
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), oldPasswordEncripted))
		{
			user.setPassword(givenNewPasswordEncripted);
			userRepository.save(user);
			return;
		}
		else
			throw new OldPasswordDoesNotMatchException("The given password does not match with the old one!");
	}
	
	private String getCurrentUsername()
	{
	    Principal principal = request.getUserPrincipal();

	    return principal.getName();
	}
}
