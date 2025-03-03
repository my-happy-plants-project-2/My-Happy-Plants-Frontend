class PlantFacts {

  //Auto-generated class containing plant info. Placeholder for now, this sort of info should be contained on the database.
  static final Map<String, String> facts = {
    "Monstera": "Monstera plants develop natural holes in their leaves, called fenestrations, to adapt to rainforest conditions!",
    "Aloe Vera": "Aloe Vera can survive for weeks without water, making it one of the easiest plants to care for.",
    "Snake Plant": "The Snake Plant is one of the few plants that releases oxygen at night, making it great for bedrooms!",
    "Cactus": "Cacti store water in their thick stems, allowing them to survive in extremely dry climates.",
    "Peace Lily": "Peace Lilies are known for their air-purifying abilities and are great for improving indoor air quality!",
  };

  static String getFact(String commonName) {
    return facts[commonName] ?? "This plant is amazing in its own unique way!";
  }
}