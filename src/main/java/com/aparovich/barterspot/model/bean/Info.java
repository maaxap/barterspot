package com.aparovich.barterspot.model.bean;

import com.aparovich.barterspot.model.Model;

import java.time.LocalDate;

/**
 * Created by Maxim on 21.03.2017
 */
public class Info extends Model {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String postCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;
        if (!super.equals(o)) return false;

        Info info = (Info) o;

        if (name != null ? !name.equals(info.name) : info.name != null) return false;
        if (surname != null ? !surname.equals(info.surname) : info.surname != null) return false;
        if (birthDate != null ? !birthDate.equals(info.birthDate) : info.birthDate != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(info.phoneNumber) : info.phoneNumber != null) return false;
        if (address != null ? !address.equals(info.address) : info.address != null) return false;
        return postCode != null ? postCode.equals(info.postCode) : info.postCode == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        return result;
    }
}
