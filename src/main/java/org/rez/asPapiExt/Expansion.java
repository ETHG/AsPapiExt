package org.rez.asPapiExt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wiefferink.areashop.AreaShop;
import me.wiefferink.areashop.regions.BuyRegion;
import me.wiefferink.areashop.regions.GeneralRegion;
import me.wiefferink.areashop.regions.RentRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return "1.4";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return null;

        Location location = player.getLocation();
        GeneralRegion currentRegion = findCurrentRegion(areaShop.getFileManager().getRegions(), location);

        switch (identifier) {
            case "time_left_current_region":
                return (currentRegion instanceof RentRegion) ?
                        String.valueOf(((RentRegion) currentRegion).getRentedUntil()) : "Available";

            case "owner_current_region":
                return currentRegion != null ? String.valueOf(currentRegion.getOwner()) : "Available";

            case "name_current_region":
                return currentRegion != null ? currentRegion.getName() : "";

            case "num_regions_for_rent":
                return String.valueOf(countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, false));

            case "num_regions_for_sale":
                return String.valueOf(countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false));

            case "num_regions_available":
                return String.valueOf(
                        countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false) +
                                countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, false));

            case "num_regions":
                return String.valueOf(areaShop.getFileManager().getRegions().size());

            case "num_regions_occupied":
                return String.valueOf(
                        countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, true) +
                                countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, true));

            case "list_regions_for_rent":
                return listRegionNames(areaShop.getFileManager().getRents(), RentRegion::isRented, false);

            case "list_regions_for_sale":
                return listRegionNames(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false);

            default:
                return null;
        }
    }

    /**
     * Finds the region a player is currently in based on their location.
     */
    private GeneralRegion findCurrentRegion(List<GeneralRegion> regions, Location location) {
        return regions.stream()
                .filter(region -> region.getRegion().contains(
                        location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                .findFirst().orElse(null);
    }

    /**
     * Counts regions based on a predicate condition.
     */
    private <T> int countRegions(List<T> regions, Predicate<T> predicate, boolean condition) {
        return (int) regions.stream().filter(region -> predicate.test(region) == condition).count();
    }

    /**
     * Lists names of regions matching a predicate condition.
     */
    private <T> String listRegionNames(List<T> regions, Predicate<T> predicate, boolean condition) {
        return regions.stream()
                .filter(region -> predicate.test(region) == condition)
                .map(region -> region instanceof BuyRegion ? ((BuyRegion) region).getName() : ((RentRegion) region).getName())
                .collect(Collectors.joining(", "));
    }
}
