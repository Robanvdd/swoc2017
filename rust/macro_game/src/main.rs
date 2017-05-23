extern crate game_structures;
extern crate game_logic;
extern crate game_process;

use game_structures::game;
use game_structures::game::GameObjectFunctions;
use game_logic::game_stats;

use game_process::process::GameProcess;

use std::process::{Command, Stdio};

use std::error::Error;
use std::io::prelude::*;

fn f()
{
    let systems = game::GameObject::default();
    systems.save_game_object_json();
    let stats = game_stats::GameStats{ BotOne: 1, BotTwo: 2 };
    stats.save();
}

fn main() {

    let micro = GameProcess::new("python".to_string(), "/Users/Michael/Documents/swoc/swoc2017/rust/macro_game/test.py".to_string());

    

    let process = match Command::new("python")
                                .arg("/Users/Michael/Documents/swoc/swoc2017/rust/macro_game/test.py")
                                .stdin(Stdio::piped())
                                .stdout(Stdio::piped())
                                .spawn() {
        Err(why) => panic!("couldn't spawn python: {}", why.description()),
        Ok(process) => process,
    };

    let mut s = String::new();
    match process.stdout.unwrap().read_to_string(&mut s) {
        Err(why) => panic!("couldn't read wc stdout: {}", why.description()),
        _ => f(),
    }
}