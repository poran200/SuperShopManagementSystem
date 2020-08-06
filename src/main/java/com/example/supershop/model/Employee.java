package com.example.supershop.model;

import com.example.supershop.enam.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(  of = {"id"},callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private Name name;
    private Date  dateOfBirth;
    @Email
    private String email;

    @ElementCollection
    private  List<String> phoneNumber = new ArrayList<>();

    private  Date joinDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String position;

    @OneToOne
    @JoinColumn(name = "address_id")
    private  Address  address;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Employee> subordinates;
    public void add(Employee employee){
        if (subordinates == null){
            subordinates = new HashSet<>();
        }
        subordinates.add(employee);
    }
    public void  remove(Employee employee){
         employee.setManager(null);
         this.subordinates.remove(employee);
    }

    public void  addPhoneNumber(String number){
        this.phoneNumber.add(number);
    }
    public Employee(Employee employee) {
        this.name= employee.name;
        this.dateOfBirth = employee.dateOfBirth;
        this.email= employee.email;
        this.phoneNumber= employee.phoneNumber;
        this.gender= employee.gender;
        this.address= employee.address;
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//                "id=" + id +
//                ", name=" + name +
//                ", dateOfBirth=" + dateOfBirth +
//                ", email='" + email + '\'' +
//                ", phoneNumber=" + phoneNumber +
//                ", joinDate=" + joinDate +
//                ", gender=" + gender +
//                ", position='" + position + '\'' +
//                '}';
//    }
}
