package com.szakdoga.services.implementations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.szakdoga.entities.AccessTokenEntity;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.Buyer;
//import com.szakdoga.entities.RefreshTokenEntity;
import com.szakdoga.entities.Role;
import com.szakdoga.entities.User;
import com.szakdoga.entities.UserActivation;
import com.szakdoga.entities.DTOs.UserDTO;
import com.szakdoga.exceptions.ActivationExpiredException;
import com.szakdoga.exceptions.EmailAddressAlreadyRegisteredException;
import com.szakdoga.exceptions.MissingUserInformationException;
import com.szakdoga.exceptions.OldPasswordDoesNotMatchException;
import com.szakdoga.exceptions.RoleDoesNotExistsException;
import com.szakdoga.exceptions.UserIsNotActivatedException;
import com.szakdoga.exceptions.UserameAlreadyRegisteredException;
import com.szakdoga.exceptions.WrongActivationCodeException;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.RoleRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.repositories.UserActivationRepository;
import com.szakdoga.repositories.UserRepository;
import com.szakdoga.services.interfaces.UserService;
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
	private EmailUtil emailSender;/*
	@Autowired
	private AccessTokenRepository accTokenRepository;
	@Autowired
	private RefreshTokenRepository refTokenRepository;*/
	@Autowired
	private HttpServletRequest request;

	@Value("${server.port}")
	private String port;

	@Value("${server.contextPath}")
	private String serverContext;

	@Override
	public void checkIfActivated(User user) {
		if (!user.isActivated())
			throw new UserIsNotActivatedException("User is not activated.");
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

		List<User> users = userRepository.findAll();
		User user = users.stream().filter(e -> e.getId().equals(activation.getUser().getId())).findFirst().get();

		user.setActivated(true);

		userActivationRepository.delete(activation);
		userRepository.save(user);
	}
	/*
	private void deleteUsersToken(String username) {
		List<AccessTokenEntity> accTokens = accTokenRepository.findAll();

		Iterator<AccessTokenEntity> accTokenIterator = accTokens.iterator();
		while (accTokenIterator.hasNext()) {
			AccessTokenEntity accToken = accTokenIterator.next();
			RefreshTokenEntity refToken = refTokenRepository.findByTokenId(accToken.getRefreshToken());
			refTokenRepository.delete(refToken);
			accTokenIterator.remove();
		}

	}*/

	private void checkIfAlreadyInDb(String username, String email) {
		if (userRepository.findByUsername(username) != null)
			throw new UserameAlreadyRegisteredException("Already registered with the username: " + username);

		if (userRepository.findByEmail(email) != null)
			throw new EmailAddressAlreadyRegisteredException("Already registered with an email: " + email);
	}

	private String getRegistrationText(String username, String activationCode) {
		StringBuilder sb = new StringBuilder();
		String link = "localhost:" + port + serverContext + "/user/activation/" + activationCode;

		sb.append("<h1>" + "Üdvözöljük " + username + "!" + "</h1></br>");
		sb.append("<p>" + "Köszöntjük oldalunkon." + "</p>");
		sb.append("</br>" + "Regisztrációja megerősítéséhez kérjük " + "</br>");
		// sb.append("<a href=+'" + link + "'>kattintson ide</a>");
		sb.append("kattinson ide: " + link);

		return sb.toString();
	}

	private Seller getDefaultSeller(User user) {
		Seller offerer = new Seller();

		offerer.setAboutMe("Empty");
		offerer.setFirstName("John");
		offerer.setLastName("Doe");
		offerer.setProfileImage(null);
		offerer.setUser(user);

		return offerer;
	}

	private Buyer getDefaultBuyer(User user) {
		Buyer seeker = new Buyer();

		seeker.setAboutMe("Empty");
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

	private void changePassword(User entity, UserDTO dto) {

		checkIfActivated(entity);

		//String givenOldPasswordEncripted = bCryptPasswordEncoder.encode(dto.getPassword());
		String givenNewPasswordEncripted = bCryptPasswordEncoder.encode(dto.getNewPassword());

		String oldPasswordEncripted = entity.getPassword();

		if (bCryptPasswordEncoder.matches(dto.getPassword(), oldPasswordEncripted)) {
			entity.setPassword(givenNewPasswordEncripted);
			return;
		} else
			throw new OldPasswordDoesNotMatchException("The given password does not match with the old one!");
	}

	@Override
	public String getCurrentUsername() {
		Principal principal = request.getUserPrincipal();

		return principal.getName();
	}

	@Override
	public User getCurrentUser() {
		return userRepository.findByUsername(getCurrentUsername());
	}

	@Override
	public void mapDtoToEntity(UserDTO dto, User entity) {
		if (dto.getEmail() != null) {
			if (!dto.getEmail().equals(entity.getEmail())) {
				if (userRepository.findByEmail(dto.getEmail()) != null) {
					throw new EmailAddressAlreadyRegisteredException(
							"Already registered with an email: " + (dto.getEmail()));
				}
				entity.setEmail(dto.getEmail());
			}
		}
	}

	@Override
	public void mapEntityToDto(User entity, UserDTO dto) {
		dto.setEmail(entity.getEmail());
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(UserDTO dto, User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(UserDTO dto) {
		if (dto.getUsername() == null || dto.getEmail() == null || dto.getPassword() == null || dto.getRole() == null)
			throw new MissingUserInformationException("Userinformation is missing");

		if (dto.getUsername().isEmpty() || dto.getEmail().isEmpty() || dto.getPassword().isEmpty()
				|| dto.getRole().isEmpty())
			throw new MissingUserInformationException("Userinformation is missing");

		checkIfAlreadyInDb(dto.getUsername(), dto.getEmail());

		List<String> validRoles = roleRepository.findAll().stream().map(r -> r.getName()).collect(Collectors.toList());

		if (!validRoles.contains(dto.getRole()))
			throw new RoleDoesNotExistsException("The role does not exists !");

		User user = new User();
		Role role = roleRepository.findByName(dto.getRole());

		user.setEmail(dto.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		user.setUsername(dto.getUsername());

		user.addRole(role);

		userRepository.save(user);

		Seller seller = getDefaultSeller(user);
		Buyer buyer = getDefaultBuyer(user);
		UserActivation userActivation = getDefaultUserActivation(user);

		sellerRepository.save(seller);
		buyerRepository.save(buyer);
		userActivationRepository.save(userActivation);

		emailSender.sendSimpleMessage(dto.getEmail(), "Registration",
				getRegistrationText(dto.getUsername(), userActivation.getActivationString()));
	}

	@Override
	public UserDTO get(Integer id) {
		User entity = userRepository.findById(id);

		if (entity == null)
			return null;

		UserDTO dto = new UserDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, UserDTO dto) {
		User entity = userRepository.findById(id);

		if (entity == null) {
			return;
		}

		checkIfActivated(entity);

		if (dto.getPassword() != null && dto.getNewPassword()!=null) {
			changePassword(entity, dto);
		}

		mapDtoToEntity(dto, entity);

		userRepository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserDTO> getAll() {
		List<UserDTO> dtos = new ArrayList<UserDTO>();

		for (User entity : userRepository.findAll()) {
			UserDTO dto = new UserDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<UserDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<UserDTO>();

		List<UserDTO> dtos = getAll();
		List<UserDTO> pagedDtos = new ArrayList<UserDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<UserDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size() {
		return Math.toIntExact(userRepository.count());
	}
}
