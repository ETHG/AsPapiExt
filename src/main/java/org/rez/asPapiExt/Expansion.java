package org.rez.asPapiExt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wiefferink.areashop.AreaShop;
import me.wiefferink.areashop.regions.BuyRegion;
import me.wiefferink.areashop.regions.GeneralRegion;
import me.wiefferink.areashop.regions.RentRegion;
import org.bukkit.entity.Player;

import javax.swing.plaf.synth.Region;

public class Expansion extends PlaceholderExpansion {
    private final AreaShop areaShop;

    public Expansion(AreaShop areaShop) {
        this.areaShop = areaShop;
    }

    @Override
    public String getIdentifier() {
        return "areashop";
    }

    @Override
    public String getAuthor() {
        return "HighRez";
    }

    @Override
    public String getVersion() {
        return "1.2";
    }

    @Override
    public boolean persist() {
        return true; // Required for PlaceholderAPI to keep the expansion active
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return null;

        // Stev functionality:

//        // Player-Specific Placeholders
//        if (identifier.equals("own_region_count")) {
//            return String.valueOf(
//                    areaShop.getFileManager().getRents(player.getName()).size()
//            );
//        }
//
//        if (identifier.equals("own_region_price_total")) {
//            return areaShop.getFileManager().getRents(player.getName())
//                    .stream()
//                    .mapToDouble(Region::getPrice)
//                    .sum() + "";
//        }

        // The total number of rental regions, occupied + unoccupied %areashop_total_rental_regions%
        if (identifier.equals("total_rental_regions")) {
            return String.valueOf(areaShop.getFileManager().getRents().size());
        }

        // The total number of buy regions, occupied + unoccupied %areashop_total_buy_regions%
        if (identifier.equals("total_buy_regions")) {
            return String.valueOf(areaShop.getFileManager().getBuys().size());
        }

        // The total number of regions, occupied + unoccupied %areashop_total_region_count%
        if (identifier.equals("total_region_count")) {
            return String.valueOf(areaShop.getFileManager().getRegions().size());
        }

        // Total regions up for rent %areashop_available_rents%
        if (identifier.equals("available_rents")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(areaShop.getFileManager().getRents().size() - count);
        }



        // Total regions up for sale %areashop_avaliable_buys%
        if (identifier.equals("available_buys")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);
                if (thisRegion.isSold()) {
                    count++;
                }
            }

            return String.valueOf(areaShop.getFileManager().getBuys().size() - count);
        }

        // Name of the region currently bePlaceholder: %areashop_house_owned%
        if (identifier.equals("house_owned") || identifier.equals("my_rental")) {
            for (int i = 0; i <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRenter(player)) {
                    return thisRegion.getName();
                }
            }
            return "No house owned";
        }



        // Placeholder: %areashop_house_owned_price%
        if (identifier.equals("house_owned_price")) {
            for (int i = 0; i <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRenter(player)) {
                    return "" + thisRegion.getPrice();
                }
            }
            return "0";
        }


//
//        // Region-Specific Placeholders
//        if (identifier.startsWith("region_") && identifier.endsWith("_status")) {
//            String regionName = identifier.substring(7, identifier.length() - 7); // Extract "region_<name>_status"
//            Region region = areaShop.getFileManager().getRegion(regionName);
//            return region != null ? region.getStatus() : "Not Found";
//        }
//
//        if (identifier.startsWith("region_") && identifier.endsWith("_price")) {
//            String regionName = identifier.substring(7, identifier.length() - 6); // Extract "region_<name>_price"
//            Region region = areaShop.getFileManager().getRegion(regionName);
//            return region != null ? String.valueOf(region.getPrice()) : "Not Found";
//        }
//
//        if (identifier.startsWith("regions_by_type_")) {
//            String type = identifier.substring(17); // Extract the type after "regions_by_type_"
//            return String.valueOf(getRegionsByType(type));
//        }

        return null; // Placeholder not recognized
    }
}
