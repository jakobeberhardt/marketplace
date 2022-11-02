package de.neocargo.marketplace.entity

data class Requirement(
    val boxTrailer: BooleanRequirement?,
    val cleanedVehicle: BooleanRequirement?,
    val craneLoadable: BooleanRequirement?,
    val emptyVehicle: BooleanRequirement?,
    val fireExtinguisher: BooleanRequirement?,
    val foodstuffs: BooleanRequirement?,
    val healthCert: BooleanRequirement?,
    val keepDry: BooleanRequirement?,
    val loadHeight: IntegerRequirement?,
    val loadLength: IntegerRequirement?,
    val loadWidth: IntegerRequirement?,
    val mobileForklift: BooleanRequirement?,
    val palletSwap: BooleanRequirement?,
    val ppeFfp2Mask: BooleanRequirement?,
    val ppeHelmet: BooleanRequirement?,
    val ppeShoes: BooleanRequirement?,
    val ppeVest: BooleanRequirement?,
    val sideLoadable: BooleanRequirement?,
    val tailLift: BooleanRequirement?,
    val trackingLevel: EnumRequirement?,
    val wasteSign: BooleanRequirement?,
    val whitelabelVehicle: BooleanRequirement?,
)
