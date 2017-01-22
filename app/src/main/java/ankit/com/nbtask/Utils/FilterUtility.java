package ankit.com.nbtask.Utils;


import android.support.annotation.NonNull;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.List;

import ankit.com.nbtask.NBTaskApplication;
import ankit.com.nbtask.model.Property;

/**
 *
 *
 */

public class FilterUtility {

    public static List<Property> getFilteredProperties(@NonNull List<Property> originalCenters) {
        if (originalCenters.isEmpty()) return originalCenters;// if centerList is empty


        String propertyTypeAp = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.AP.name(), PrefConfig.emptyValue);
        String propertyTypeIf = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IF.name(), PrefConfig.emptyValue);
        String propertyTypeIh = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IH.name(), PrefConfig.emptyValue);

        String apartmentOne = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK1.name(), PrefConfig.emptyValue);
        String apartmentTwo = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK2.name(), PrefConfig.emptyValue);
        String apartmentThree = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK3.name(), PrefConfig.emptyValue);
        String apartmentFour = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK4.name(), PrefConfig.emptyValue);

        String minAmt = "500";
        String maxAmt = "2000";


        String semiFurnished = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.SEMI_FURNISHED.name(), PrefConfig.emptyValue);
        String fullyFurnished = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.FULLY_FURNISHED.name(), PrefConfig.emptyValue);

        /**
         * stream filter  for properties type
         */
        Predicate<Property> propertyFilter = property -> Property.contains(property.getApartmentType(), apartmentOne, apartmentTwo, apartmentThree, apartmentFour);
        /**
         * stream filter  for furnished type
         */
        Predicate<Property> furnishingFilter = property -> Property.contains(property.getFurnishing(), semiFurnished, fullyFurnished);
        /**
         * stream filter  for building type
         */
        Predicate<Property> buildingTypeFilter = property -> Property.contains(property.getBuildingType(), propertyTypeAp, propertyTypeIf, propertyTypeIh);


        Predicate<Property> buildAmtFilter = property -> {
            int minAmount = Integer.parseInt(minAmt);
            int maxAmount = Integer.parseInt(maxAmt);
            return property.getAmount() >= minAmount
                    && property.getAmount() < maxAmount;
        };

        /**
         * Will check for all kind of Stream filters and return the filtered list
         */
        final List<Property> collect = Stream.of(originalCenters)
                .filter(propertyFilter)
                .filter(furnishingFilter)
                .filter(buildingTypeFilter)
                .filter(buildAmtFilter)
                .collect(Collectors.toList());

        MyLog.i("filtered list ", String.valueOf(collect.size()));
        return collect;
    }
}
