use ufo;

#[derive(Serialize, Deserialize)]
pub struct Player{
    pub id: i32,
    pub name: String,
    pub credits: i32,
    pub ufos: Vec<ufo::Ufo>
}