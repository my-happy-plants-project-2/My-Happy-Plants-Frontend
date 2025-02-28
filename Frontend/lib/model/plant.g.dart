// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'plant.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Plant _$PlantFromJson(Map<String, dynamic> json) => Plant(
      plantId: json['plantId'] as String,
      commonName: json['commonName'] as String,
      scientificName: json['scientificName'] as String,
      familyName: json['familyName'] as String,
      imagePath: json['imagePath'] as String,
      nickname: json['nickname'] as String,
      lastWatered: DateTime.parse(json['lastWatered'] as String),
      waterFrequency: (json['waterFrequency'] as num).toInt(),
      light: (json['light'] as num).toInt(),
    );

Map<String, dynamic> _$PlantToJson(Plant instance) => <String, dynamic>{
      'plantId': instance.plantId,
      'commonName': instance.commonName,
      'scientificName': instance.scientificName,
      'familyName': instance.familyName,
      'imagePath': instance.imagePath,
      'nickname': instance.nickname,
      'lastWatered': instance.lastWatered.toIso8601String(),
      'waterFrequency': instance.waterFrequency,
      'light': instance.light,
    };
