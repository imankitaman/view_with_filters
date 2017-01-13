package ankit.com.nbtask.Utils;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.List;

import ankit.com.nbtask.NBTaskApplication;
import ankit.com.nbtask.model.Property;

/**
 * Created by ankit
 */

public class FilterUtility {

    public static List<Property> getFilteredProperties(List<Property> originalCenters) {

        String propertyTypeAp = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.AP.name(), "");
        String propertyTypeIf = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IF.name(), "");
        String propertyTypeIh = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IH.name(), "");

        String apartmentTwo = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK2.name(), "");
        String apartmentThree = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK3.name(), "");
        String apartmentFour = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK4.name(), "");

        String semiFurnished = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.SEMI_FURNISHED.name(), "");
        String fullyFurnished = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.FULLY_FURNISHED.name(), "");

        Predicate<Property> propertyFilter = property -> Property.ApartmentType.in(property.getApartmentType(), apartmentTwo, apartmentThree, apartmentFour);
        Predicate<Property> furnishingFilter = property -> Property.Furnishing.in(property.getFurnishing(), semiFurnished, fullyFurnished);
        Predicate<Property> buildingTypeFilter = property -> Property.BuildingType.in(property.getBuildingType(), propertyTypeAp, propertyTypeIf, propertyTypeIh);

        final List<Property> collect = Stream.of(originalCenters)
                .filter(propertyFilter)
                .filter(furnishingFilter)
                .filter(buildingTypeFilter)
                .collect(Collectors.toList());

        MyLog.i("filtered list ", collect.toString());
        return collect;
    }
}
