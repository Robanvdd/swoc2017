extern crate serde;
extern crate serde_json;

use serde_json::Error;
use std::fs::File;
use std::io::Write;

#[derive(Serialize, Deserialize)]
pub struct GameStats {
    pub BotOne: i32,
    pub BotTwo: i32
}

impl GameStats {
    pub fn save(&self) -> bool {
        let mut buffer = File::create("stats.json").expect("Unable to open");
        let write_result = buffer.write(serde_json::to_string_pretty(&self).unwrap().as_bytes());
        buffer.flush().is_ok()
    }
}
