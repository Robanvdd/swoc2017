use generics::Point;

#[derive(Serialize, Deserialize)]
pub struct Ufo{
    pub id: i32,
    pub ufo_type: String,
    pub in_fight: bool,    
    pub coord: Point
}