use planet::Planet;
use generics::Point;

#[derive(Serialize, Deserialize)]
pub struct SolarSystem{
    pub id: i32,
    pub name: String,
    pub coord: Point,
    pub planets: Vec<Planet>
}