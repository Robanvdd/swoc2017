package com.sioux.GameEngine;

/**
 * Created by Michael on 3/15/2017.
 */
public class Flight {

    private int _id;
    private Player _playerOne;
    private Player _playerTwo;

    public Flight(int id, Player playerOne, Player playerTwo) {
    }

    public void set_id(int _id) { this._id = _id; }
    public int get_id() { return _id; }

    public Player get_playerOne() { return this._playerOne; }
    public void set_playerOne(Player _playerOne) { this._playerOne = _playerOne; }

    public Player get_playerTwo() { return this._playerTwo; }
    public void set_playerTwo(Player _playerTwo) { this._playerTwo = _playerTwo; }
}
