package org.rez.asPapiExt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wiefferink.areashop.AreaShop;
import me.wiefferink.areashop.regions.RentRegion;
import org.bukkit.entity.Player;

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
        return "expanded by HighRez";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // Required for PlaceholderAPI to keep the expansion active
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return null;

        // Placeholder: %areashop_available_housing%
        if (identifier.equals("available_housing")) {
            return String.valueOf(areaShop.getFileManager().getRents().size());
        }

        // Placeholder: %areashop_house_owned%
        if (identifier.equals("house_owned")) {
            for (int i = 0; i <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRenter(player)) {
                    return "True";
                }
            }
            return "False";
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


        // Placeholder: %areashop_available_apartments%
        //if (identifier.equals("available_apartments")) {}

        // Placeholder: %areashop_available_ghetto%
        //if (identifier.equals("available_ghetto")) {
        //}

        // Placeholder: %areashop_available_upperclass%
        //if (identifier.equals("available_upperclass")) {
        //}

        // Placeholder: %areashop_available_mansions%
        //if (identifier.equals("available_mansions")) {
        //}

        return null; // If the placeholder is not recognized
    }

//    private int getRegionsByType(String type) {
//        // Custom method to filter regions by type
//        return (int) areaShop.getFileManager().getRegions().stream()
//                .filter(region -> region.getFlags().get("type").equals(type))
//                .count();
//    }
}
