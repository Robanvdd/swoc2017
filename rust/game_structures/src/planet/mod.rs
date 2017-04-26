#[derive(Serialize, Deserialize)]
pub struct Planet{
    pub id: i32,
    pub name: String,
    pub orbit_distance: i32,
    pub orbit_rotation: i32,
    pub owned_by: i32
}