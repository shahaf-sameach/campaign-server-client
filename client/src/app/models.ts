export interface Campaign {
  id: Number;
  name: String;
  data: Data;
  cap: Cap;
}

export interface Data {
  message: String;
}

export interface Cap {
  max_count_per_user: Number;
  max_count: Number;
}
