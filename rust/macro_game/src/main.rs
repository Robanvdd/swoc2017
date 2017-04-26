extern crate serde;
extern crate serde_json;
extern crate game_structures;

#[macro_use]
extern crate serde_derive;

use serde_json::Error;

use std::fs::File;
use std::io::Write;
use game_structures::game;

fn main() {
    let systems = game::GameObject::default();
    let j = serde_json::to_string_pretty(&systems);
  
    let mut buffer = File::create("foo.json").expect("Unable to open");
    let write_result = buffer.write(j.unwrap().as_bytes());
    let flush_result = buffer.flush();
}