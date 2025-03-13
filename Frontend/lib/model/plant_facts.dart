class PlantFacts {

  //Auto-generated class containing plant info. Placeholder for now, this sort of info should be contained on the database.
  static final Map<String, String> facts = {
    "Monstera": "Monstera plants develop natural holes in their leaves, called fenestrations, to adapt to rainforest conditions!",
    "Aloe Vera": "Aloe Vera can survive for weeks without water, making it one of the easiest plants to care for.",
    "Snake Plant": "The Snake Plant is one of the few plants that releases oxygen at night, making it great for bedrooms!",
    "Cactus": "Cacti store water in their thick stems, allowing them to survive in extremely dry climates.",
    "Peace Lily": "Peace Lilies are known for their air-purifying abilities and are great for improving indoor air quality!",
  };

  static final Map<String, String> caringTips = {
    "Monstera": "Monstera plants prefer bright, indirect light and should be watered when the top inch of soil is dry.",
    "Aloe Vera": "Aloe Vera thrives in bright light and requires infrequent watering, allowing the soil to dry between waterings.",
    "Snake Plant": "Snake Plants prefer low light and require minimal watering, only when the soil is dry.",
    "Cactus": "Cacti need bright sunlight and should only be watered when the soil is completely dry.",
    "Peace Lily": "Peace Lilies prefer medium, indirect light and need to be watered when the soil feels dry to the touch.",
  };


  static String getFact(String commonName) {
    return facts[commonName] ?? "Your plant is unique and is thriving!!" ;
  }
  static String getCaringTips(String commonName){
    return caringTips[commonName] ?? "No caring tips available" ;
  }
}