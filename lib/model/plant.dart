class Plant {
  final String plantId;
  final String commonName;
  final String scientificName;
  final String familyName;
  final String imagePath;
  final String nickname;
  final DateTime lastWatered;
  final int waterFrequency;
  final int light;

  Plant({
    required this.plantId,
    required this.commonName,
    required this.scientificName,
    required this.familyName,
    required this.imagePath,
    required this.nickname,
    required this.lastWatered,
    required this.waterFrequency,
    required this.light,
  });
}
