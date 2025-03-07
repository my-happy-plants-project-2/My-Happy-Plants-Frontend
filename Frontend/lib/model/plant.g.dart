// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'plant.dart';

//An auto-generated file that handles Json files. Generated using the Json_serializable dependency.

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Plant _$PlantFromJson(Map<String, dynamic> json) => Plant(
      // plantId: json['plantId'] as String,
      plantId: json['plantID'] as String,
      // commonName: json['commonName'] as String,
      commonName: json['commonName'] as String,
      // scientificName: json['scientificName'] as String,
      scientificName: json['species'] as String,
      // familyName: json['familyName'] as String,
      familyName: json['family'] as String,
      // imagePath: json['imagePath'] as String,
      imagePath: json['imageUrl'] as String,
      nickname: json['nickname'] as String,
      // lastWatered: DateTime.parse(json['lastWatered'] as String),
      lastWatered: DateTime.fromMillisecondsSinceEpoch(json['lastWatered'] as int),
      // waterFrequency: (json['waterFrequency'] as num).toInt(),
      waterFrequency: (json['waterFrequency'] as num).toInt(),
      // light: (json['light'] as num).toInt(),
      light: (json['lightReqs'] as num).toInt(),
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
