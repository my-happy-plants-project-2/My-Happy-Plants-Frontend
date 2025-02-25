//@author Filip Claesson, Pehr Norten
import 'package:json_annotation/json_annotation.dart';
part 'plant.g.dart';

@JsonSerializable()
class Plant {
  String plantId;
  final String commonName;
  final String scientificName;
  final String familyName;
  final String imagePath;
  String nickname;
  DateTime lastWatered;
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

  double get calculateWaterLevel {
    DateTime now = DateTime.now();
    int daysSinceWatered = now.difference(lastWatered).inDays;

    double waterDepletion = 1.0/waterFrequency;
    double lightFactor = 1 + (light/10.0);

    double waterLevel = 1.0 - (daysSinceWatered * waterDepletion * lightFactor);

    return waterLevel.clamp(0.0, 1.0);
  }
  factory Plant.fromJson(Map<String, dynamic> json) => _$PlantFromJson(json);
  Map<String, dynamic> toJson() => _$PlantToJson(this);
}
