#[derive(Serialize, Deserialize)]
pub struct Fights{
        pub id: i32,
        pub player_one_id: i32,
        pub player_two_id: i32,
        pub planet_id: i32,
        pub player_one_ufo_ids: Vec<i32>,
        pub player_two_ufo_ids: Vec<i32>
}
