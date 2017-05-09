extern crate serde;
extern crate serde_json;

use serde_json::Error;
use std::fs::File;
use std::io::Write;

use fights::Fights;
use generics::Point;
use planet::Planet;
use player::Player;
use solar_system::SolarSystem;
use ufo::Ufo;

#[derive(Serialize, Deserialize)]
pub struct GameObject{
        pub id: i32,
        pub name: String,
        pub tick: i32,
        pub solar_systems: Vec<SolarSystem>,
        pub players: Vec<Player>,
        pub fights: Fights
}

pub trait GameObjectFunctions {
    fn default() -> GameObject;
    fn convert_to_json(&self) -> String;
    fn read_from_json(path: String) -> GameObject;
    fn save_game_object_json(&self) -> bool;
}

impl GameObjectFunctions for GameObject{
    fn convert_to_json(&self) -> String{
        serde_json::to_string_pretty(&self).unwrap()
    }

    fn read_from_json(path: String) -> GameObject{
        GameObject::default()
    }

    fn save_game_object_json(&self) -> bool{
        let mut buffer = File::create("foo.json").expect("Unable to open");
        let write_result = buffer.write(self.convert_to_json().as_bytes());
        buffer.flush().is_ok()
    }

     fn default() -> GameObject {
        
        let mut all_planets_one = Vec::new();
        all_planets_one.push(Planet{id: 1, name: "test".to_string(), orbit_distance: 10, orbit_rotation: 80, owned_by: 1});
        all_planets_one.push(Planet{id: 2, name: "test".to_string(), orbit_distance: 11, orbit_rotation: 81, owned_by: 2});

        let mut all_planets_two = Vec::new();
        all_planets_two.push(Planet{id: 1, name: "test".to_string(), orbit_distance: 10, orbit_rotation: 80, owned_by: 1});
        all_planets_two.push(Planet{id: 2, name: "test".to_string(), orbit_distance: 11, orbit_rotation: 81, owned_by: 2});

        let mut all_solar_systems = Vec::new();
        all_solar_systems.push(SolarSystem{id: 1, name: "test".to_string(), coord: Point{x: 1, y: 1 +1}, planets: all_planets_one});
        all_solar_systems.push(SolarSystem{id: 2, name: "test".to_string(), coord: Point{x: 1, y: 1 +1}, planets: all_planets_two});

        let mut all_ufos_one = Vec::new();
        let mut all_ufos_two = Vec::new();
        let mut n = 0;
        while n < 2 {
            all_ufos_one.push(Ufo{id: n, ufo_type: "thank".to_string(), in_fight: false, coord: Point{x: n, y: n +1} });
            all_ufos_two.push(Ufo{id: n + 1, ufo_type: "thank".to_string(), in_fight: true, coord: Point{x: n, y: n +1} });
            n += 1;
        }
        
        let mut all_players = Vec::new();
        all_players.push(Player{id: 1, name: "jan".to_string(), credits: 10, ufos: all_ufos_one });
        all_players.push(Player{id: 1, name: "piet".to_string(), credits: 10, ufos: all_ufos_two });



        let all_fights = Fights { id: 1,
                                  player_one_id: 1,
                                  player_two_id: 2,
                                  planet_id: 1,
                                  player_one_ufo_ids: vec![1, 2, 3],
                                  player_two_ufo_ids:vec![4, 5, 6] };
        GameObject
            { id: -1, 
            name: "S1".to_string(),
            tick: 231,
            solar_systems: all_solar_systems,
            players: all_players,
            fights: all_fights 
            }
    }
}