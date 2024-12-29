package org.rez.asPapiExt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wiefferink.areashop.AreaShop;
import me.wiefferink.areashop.regions.BuyRegion;
import me.wiefferink.areashop.regions.GeneralRegion;
import me.wiefferink.areashop.regions.RentRegion;
import org.bukkit.entity.Player;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;
import java.util.List;

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

        // Placeholder: %areashop_num_regions_for_rent%
        // Number of rent regions available to be rented by a tenant
        if (identifier.equals("num_regions_for_rent")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (!thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(count);

        }

        // Placeholder: %areashop_num_regions_for_sale%
        // Number of buy regions available to be purchased by a buyer
        if (identifier.equals("num_regions_for_sale")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);
                if (!thisRegion.isSold()) {
                    count++;
                }
            }

            return String.valueOf(count);
        }

        // Placeholder: %areashop_num_regions_available%
        // Number of buy and rent regions available to be bought or rented
        if (identifier.equals("num_regions_available")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);
                if (!thisRegion.isSold()) {
                    count++;
                }
            }

            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (!thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(count);

        }

        // Placeholder: %areashop_num_regions%
        // Total number of regions
        if (identifier.equals("num_regions")) {
            return String.valueOf(areaShop.getFileManager().getRegions().size());
        }

        // Placeholder: %areashop_num_regions_bought%
        // Number of buy regions that currently have an owner
        if (identifier.equals("num_regions_bought")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);
                if (thisRegion.isSold()) {
                    count++;
                }
            }

            return String.valueOf(count);
        }

        // Placeholder: %areashop_num_regions_rented%
        // Number of rental regions that currently have a tenant
        if (identifier.equals("num_regions_rented")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(count);

        }

        // Placeholder: %areashop_num_regions_occupied%
        // Number of buy and rent regions with an owner or tenant
        if (identifier.equals("num_regions_occupied")) {
            int count = 0;
            for (int i = 0; i  <areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);
                if (thisRegion.isSold()) {
                    count++;
                }
            }

            for (int i = 0; i  <areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);
                if (thisRegion.isRented()) {
                    count++;
                }
            }

            return String.valueOf(count);

        }

        // Placeholder: %areashop_list_regions_for_rent%
        // List of rent regions available to be rented
        if (identifier.equals("list_regions_for_rent")) {
            // Initialize a list to store region names
            List<String> availableRegions = new ArrayList<>();

            // Loop through all rent regions
            for (int i = 0; i < areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);

                // Check if the region is not rented
                if (!thisRegion.isRented()) {
                    availableRegions.add(thisRegion.getName()); // Add region name to the list
                }
            }

            // Convert the list to a comma-separated string and return it
            return String.join(", ", availableRegions);
        }

        // Placeholder: %areashop_list_regions_for_sale%
        // List of buy regions available to be purchased
        if (identifier.equals("list_regions_for_sale")) {
            // Initialize a list to store region names
            List<String> availableRegions = new ArrayList<>();

            // Loop through all rent regions
            for (int i = 0; i < areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);

                // Check if the region is not rented
                if (!thisRegion.isSold()) {
                    availableRegions.add(thisRegion.getName()); // Add region name to the list
                }
            }

            // Convert the list to a comma-separated string and return it
            return String.join(", ", availableRegions);
        }

        // Placeholder: %areashop_list_regions_available%
        // List of buy or rent regions available to be purchased or rented
        if (identifier.equals("list_regions_available")) {
            // Initialize a list to store region names
            List<String> availableRegions = new ArrayList<>();

            // Loop through all rent regions
            for (int i = 0; i < areaShop.getFileManager().getBuys().size(); i++) {
                BuyRegion thisRegion = areaShop.getFileManager().getBuys().get(i);

                // Check if the region is not rented
                if (!thisRegion.isSold()) {
                    availableRegions.add(thisRegion.getName()); // Add region name to the list
                }
            }

            // Loop through all rent regions
            for (int i = 0; i < areaShop.getFileManager().getRents().size(); i++) {
                RentRegion thisRegion = areaShop.getFileManager().getRents().get(i);

                // Check if the region is not rented
                if (!thisRegion.isRented()) {
                    availableRegions.add(thisRegion.getName()); // Add region name to the list
                }
            }

            // Convert the list to a comma-separated string and return it
            return String.join(", ", availableRegions);


        }

        // Placeholder: %areashop_list_regions%
        // List of all regions that can be bought or rented
        if (identifier.equals("list_regions")) {
            return String.valueOf(areaShop.getFileManager().getRegions());
        }

        // Placeholder: %areashop_num_regions_bought_own%
        // Number of buy regions the player owns
        if (identifier.equals("num_regions_bought_own")) {

        }

        // Placeholder: %areashop_num_regions_rented_own%
        // Number of rental regions the player rents
        if (identifier.equals("num_regions_rented_own")) {

        }

        // Placeholder: %areashop_num_regions_occupied_own%
        // Number of regions the player buys or rents
        if (identifier.equals("num_regions_occupied_own")) {

        }

        // Placeholder: %areashop_list_regions_bought_own%
        // List of the buy regions the player owns
        if (identifier.equals("list_regions_bought_own")) {

        }

        // Placeholder: %areashop_list_regions_rented_own%
        // List of rental regions the player rents
        if (identifier.equals("list_regions_rented_own")) {

        }

        // Placeholder: %areashop_list_regions_occupied_own%
        // List of regions the player buys or rents
        if (identifier.equals("list_regions_occupied_own")) {

        }

        // Placeholder: %areashop_num_price_owed%
        // Total amount player pays for all the regions they own
        if (identifier.equals("num_price_owed")) {

        }

        // Placeholder: %areashop_num_price_payed%
        // Total amount the player has paid for the regions they own
        if (identifier.equals("num_price_payed")) {

        }

        // Placeholder: %areashop_list_price_owed%
        // List of rent costs for regions rented by the player
        if (identifier.equals("list_price_owed")) {

        }

        // Placeholder: %areashop_list_price_payed%
        // List of prices paid for regions bought by the player
        if (identifier.equals("list_price_payed")) {

        }

        // Placeholder: %areashop_list_price%
        // List of rent and purchase prices of regions rented and purchased by the player
        if (identifier.equals("list_price")) {

        }

        return null; // Placeholder not recognized
    }


    private String getAvailableRentRegionList() {

    }

}
