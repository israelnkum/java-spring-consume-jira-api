package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import com.amalitech.amalitechprojectdashboard.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apd_password_reset_token")
public class PasswordResetToken {
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	
	public PasswordResetToken(User user, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
	
}
