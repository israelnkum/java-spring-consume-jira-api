package com.amalitech.amalitechprojectdashboard.controllers.registration.VerificationToken;

import com.amalitech.amalitechprojectdashboard.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apd_verification_tokens")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false)
	private String token;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	

	private LocalDateTime confirmedAt;
	
	@ManyToOne
	@JoinColumn(nullable = false,name = "user_id")
	private User user;
	
	public VerificationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.user = user;
	}
}
