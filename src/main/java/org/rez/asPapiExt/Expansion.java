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


        // Placeholder: %areashop_house_owned%
        if (identifier.equals("house_owned")) {
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

        // Placeholder: %areashop_available_housing
        if (identifier.equals("available_housing")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(areaShop.getFileManager().getRents().size() - count);
        }

        // Placeholder: %areashop_available_apartments%
        // How many apartments are still available (integer)
        if (identifier.equals("available_apartments")) {
            return this.getNumOfAvailHousingOfType("apt");
        }

        // Placeholder: %areashop_available_ghetto%
        if (identifier.equals("available_ghetto")) {
            return this.getNumOfAvailHousingOfType("gh");
        }

        // Placeholder: %areashop_available_upperclass%
        if (identifier.equals("available_upperclass")) {
            return this.getNumOfAvailHousingOfType("up");
        }

        // Placeholder: %areashop_available_mansions%
        if (identifier.equals("available_mansions")) {
            return this.getNumOfAvailHousingOfType("msn");
        }


        return null; // If the placeholder is not recognized
    }

    private String getNumOfAvailHousingOfType(String type) {
        int count = 0;
        for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
            RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
            if (!thisRegion.isRented() && thisRegion.getName().contains(type)) {
                count++;
            }
        }

        return String.valueOf(count);

    }

}
