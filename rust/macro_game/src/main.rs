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

fn start()
{
    //init a gameobj
    let tempGameObj = game::GameObject::default();
    
    //start micro game and send the gameobj
    let mut micro = GameProcess::new("/Users/Michael/Documents/swoc/swoc2017/rust/micro_game/target/debug/micro_game".to_string(), "".to_string());
    micro.send_input(&tempGameObj);
    //receive the gameobj
    let systemsDone = micro.get_output();

    //save gameobj for vis.
    systemsDone.save_game_object_json();

    // save some stats
    let stats = game_stats::GameStats{ BotOne: 1, BotTwo: 2 };
    stats.save();
}

fn main() {
    ////swoc/swoc2017/rust/macro_game/test.py
    
    //start a python bot
    let process = match Command::new("python")
                                .arg("/Users/Michael/Documents/swoc/swoc2017/rust/macro_game/test.py")
                                .stdin(Stdio::piped())
                                .stdout(Stdio::piped())
                                .spawn() {
        Err(why) => panic!("couldn't spawn python: {}", why.description()),
        Ok(process) => process,
    };

    //receive a instruction from python script
    let mut s = String::new();
    match process.stdout.unwrap().read_to_string(&mut s) {
        Err(why) => panic!("couldn't read wc stdout: {}", why.description()),
        _ => start(),
    }
}