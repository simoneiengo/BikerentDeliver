package com.iengos.bikerentDeliver;

/**
 * Created by Dave on 30/08/2016.
 */
public class InfoRequest {
    private String name;
    private String surname;
    private String number;
    private String email;
    private String price;
    private String time;
    private String numBike;
    private String reqNum;

    public InfoRequest(){
        super();
    }

    public InfoRequest(String _name, String _surname, String _number, String _email, String _price, String _time, String _reqNum, String _numBike) {
        super();
        this.name = _name;
        this.surname = _surname;
        this.number = _number;
        this.email = _email;
        this.price = _price;
        this.time = _time;
        this.numBike = _numBike;
        this.reqNum = _reqNum;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getNumber(){
        return number;
    }

    public String getEmail(){
        return email;
    }

    public String getNumBike(){
        return numBike;
    }

    public String getPrice(){
        return price;
    }

    public String getTime(){
        return time;
    }

    public String getReqNum(){
        return reqNum;
    }

    public void setName(String _name){
        name = _name;
    }

    public void setSurname(String _surname){
        surname = _surname;
    }

    public void setNumber(String _number){
        number = _number;
    }

    public void setEmail(String _email){
        email = _email;
    }

    public void setNumBike(String _numBike){
        numBike = _numBike;
    }

    public void setPrice(String _price){
        price = _price;
    }

    public void setTime(String _time){
        time = _time;
    }

    public void setReqNum(String _reqNum){
        reqNum = _reqNum;
    }

}