package com.nhxv.botbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the user database table.
 *
 */
@Entity
@Table(name = "wuser")
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
	private static final long serialVersionUID = 65981149772133526L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	@Column
	private String email;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;

	@Column
	private String password;

	@Column
	private String provider;

	@Column
	private String avatar;

	// bi-directional many-to-many association to Role
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "wuser_wrole", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", providerUserId='" + providerUserId + '\'' +
				", email='" + email + '\'' +
				", enabled=" + enabled +
				", displayName='" + displayName + '\'' +
				", createdDate=" + createdDate +
				", modifiedDate=" + modifiedDate +
				", password='" + password + '\'' +
				", provider='" + provider + '\'' +
				", avatar='" + avatar + '\'' +
				'}';
	}
}
