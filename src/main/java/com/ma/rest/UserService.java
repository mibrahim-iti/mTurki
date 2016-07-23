package com.ma.rest;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ma.dto.Message;
import com.ma.dto.User;
import com.ma.util.DBConnection;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/users")
public class UserService {

	@POST
	@Path("/")
	public Response createNewUser(User user) {
		if (user.getPhone() != null && !user.getPhone().equals("") && user.getPassword() != null && !user.getPassword().equals("")) {
			DBConnection.getConnection();
			PreparedStatement updateemp;
			try {
				updateemp = DBConnection.getConnection().prepareStatement("INSERT INTO user(phone, password) VALUES(?,?)");
				updateemp.setString(1, user.getPhone());
				updateemp.setString(2, user.getPassword());
				updateemp.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return Response.status(400).entity("Faild").build();
		}

		return Response.status(200).entity("Success").build();
	}

	@PUT
	@Path("/{phone}")
	public Response updateUser(@PathParam(value = "phone") String phone, User user) {
		DBConnection.getConnection();
		PreparedStatement updateemp;
		//TODO check if phone in path is equals to this one in object
		try {
			updateemp = DBConnection.getConnection().prepareStatement("UPDATE user SET userId = ?, name = ?, email = ?, amount = ? WHERE phone = ?");
			updateemp.setInt(1, user.getUserId());
			updateemp.setString(2, user.getName());
			updateemp.setString(3, user.getEmail());
			updateemp.setString(4, user.getAmount());
			updateemp.setString(5, phone);
			updateemp.executeUpdate();
		} catch (SQLException e) {
			return Response.status(400).entity("Invalid Data passed.").build();
		}

		return Response.status(200).entity("Success").build();
	}

	@GET
	@Path("/")
	public Response GetUsers() {
		DBConnection.getConnection();
		PreparedStatement updateemp;
		String usersJSON = null;
		try {
			updateemp = DBConnection.getConnection().prepareStatement("SELECT * FROM user");
			ResultSet rs = updateemp.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				User user = new User();
				// Retrieve by column name
				user.setPhone(rs.getString("phone"));
				user.setUserId(rs.getInt("userId"));
				user.setPhone(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setAmount(rs.getString("amount"));
				users.add(user);
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				usersJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			return Response.status(400).entity("Invalid Data passed.").build();
		}

		return Response.status(200).entity(new Message(2, usersJSON)).build();
	}

	@GET // For Login
	@Path("/{phone}")
	public Response GetUser(@PathParam(value = "phone") String phone, User user) {
		DBConnection.getConnection();
		PreparedStatement updateemp;
		//TODO check if phone in path is equals to this one in object
		try {
			updateemp = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE phone = ? AND password = ?");
			updateemp.setString(1, phone);
			updateemp.setString(2, user.getPassword());
			ResultSet rs = updateemp.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				String rsPhone = rs.getString("phone");
				// String rsPassword = rs.getString("password");
				if (rsPhone != null && !rsPhone.equals(""))
					return Response.status(200).entity("{\"userId\": " + rs.getString("userId") + "}").build();
			}
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(400, "Invalid Data passed.")).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

}