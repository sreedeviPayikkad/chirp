package com.intuit.chirp.feeds.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class User {
    private Long id;
    @Column("ldap_id")
    private String ldapId;
    @Column("first_name")
    private String fistName;
    @Column("last_name")
    private String lastName;
    @Column("email")
    private String email;
}
