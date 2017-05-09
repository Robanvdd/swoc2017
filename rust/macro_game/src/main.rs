extern crate game_structures;

use game_structures::game;
use game_structures::game::GameObjectFunctions;
use std::process::{Command, Stdio};

use std::error::Error;
use std::io::prelude::*;

fn f()
{
    let systems = game::GameObject::default();
    systems.save_game_object_json();
}

fn main() {
    // let mut child = Command::new("python")
    //                 .arg("/Users/Michael/Documents/swoc/swoc2017/rust/macro_game/test.py")
    //                 .output()
    //                 .expect("failed to execute python script");

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