package com.ayushsingh.assessmentportal.model;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "user") // to give a name of our choice to the table
public class User implements UserDetails /*
                                          * Spring Security uses the UserDetails interface, can also create a separate
                                          * class
                                          */ {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "userid")
        private Long userId;
        @Column(name = "username", nullable = false, unique = true)
        private String username;
        @Column(name = "password", nullable = false)
        private String password;
        @Column(name = "lastname")
        private String lastName;
        @Column(name = "firstname", nullable = false)
        private String firstName;

        @Column(name = "email", nullable = false, unique = true)
        private String email;
        @Column(name = "contactno")
        private String phone;
        @Column(name = "isenabled")
        private String enabled = "true"; // by default: enable the user

        private String profile;

        @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
                        CascadeType.REFRESH }/* Save role when user is saved */, fetch = FetchType.EAGER) // each user
                                                                                                          // can have
                                                                                                          // many roles
        @JoinTable(name = "user_role", // the name of the table which manages this relationship
                        // specify the join columns in that table
                        joinColumns = @JoinColumn(
                                        /*
                                         * name of the join column
                                         * The name of the foreign key column which stores the user id
                                         * The table in which it is found depends upon the context.
                                         */
                                        name = "user",
                                        /*
                                         * The name of the primary key in the
                                         * user table which works as the foreign key in this table
                                         * column.
                                         */
                                        referencedColumnName = "userid"),
                        /*
                         * The foreign key columns of the join table which reference the primary table
                         * of the entity that does not own the association. (I.e. the inverse side of
                         * the association).
                         */
                        inverseJoinColumns = @JoinColumn(
                                        /*
                                         * The name of the foreign key column. The table in which it is found depends
                                         * upon the context. The column which keeps the role id in this table
                                         */
                                        name = "role",

                                        /*
                                         * The name of the column referenced by this foreign key column. The primary key
                                         * in the role table working as the foreign key in this table
                                         */
                                        referencedColumnName = "roleid"))
        private List<Role> roles = new ArrayList<>();

        /*
         * Spring Security: UserDetails methods
         * These methods are used by Spring Security
         */

        // This method will be called to get the list of authorities
        // the user has
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                // create authorites using roles and return
                Set<Authority> set = new HashSet<>();
                for (Role role : roles) {
                        set.add(new Authority(role.getRoleName()));
                }
                return set;
        }

        @Override
        public boolean isAccountNonExpired() {

                return true;
        }

        @Override
        public boolean isAccountNonLocked() {

                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {

                return true;
        }

        @Override
        public boolean isEnabled() {

                // return the enabled status
                // if the enabled is set to false
                // return false
                // this will disable account
                System.out.println("is enabled: "+enabled);
                if (this.enabled.equalsIgnoreCase(enabled)) {
                        System.out.println(User.class.getName()+" User is enabled");
                        return true;
                } else {
                        System.out.println(User.class.getName()+" User is disabled");

                        return false;
                }
        }

}