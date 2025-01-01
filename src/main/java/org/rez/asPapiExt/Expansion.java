package org.rez.asPapiExt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wiefferink.areashop.AreaShop;
import me.wiefferink.areashop.regions.BuyRegion;
import me.wiefferink.areashop.regions.RentRegion;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Expansion extends PlaceholderExpansion {
    private final AreaShop areaShop;

    // Constructor to initialize the AreaShop instance
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
        return "1.3";
    }

    @Override
    public boolean persist() {
        // Ensure the expansion remains active between reloads
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return null; // Return null if the player is invalid

        // Handle various placeholders based on the identifier
        switch (identifier) {
            case "num_regions_for_rent":
                // Count the number of unrented regions
                return String.valueOf(countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, false));

            case "num_regions_for_sale":
                // Count the number of unsold regions
                return String.valueOf(countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false));

            case "num_regions_available":
                // Count total available (unsold + unrented) regions
                return String.valueOf(
                        countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false) +
                                countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, false)
                );

            case "num_regions":
                // Return the total number of regions
                return String.valueOf(areaShop.getFileManager().getRegions().size());

            case "num_regions_bought":
                // Count the number of regions that have been sold
                return String.valueOf(countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, true));

            case "num_regions_rented":
                // Count the number of regions currently rented
                return String.valueOf(countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, true));

            case "num_regions_occupied":
                // Count all occupied regions (rented + sold)
                return String.valueOf(
                        countRegions(areaShop.getFileManager().getBuys(), BuyRegion::isSold, true) +
                                countRegions(areaShop.getFileManager().getRents(), RentRegion::isRented, true)
                );

            case "list_regions_for_rent":
                // List names of all unrented regions
                return listRegionNames(areaShop.getFileManager().getRents(), RentRegion::isRented, false);

            case "list_regions_for_sale":
                // List names of all unsold regions
                return listRegionNames(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false);

            case "list_regions_available":
                // List all available (unsold + unrented) regions
                return String.join(", ",
                        listRegionNames(areaShop.getFileManager().getBuys(), BuyRegion::isSold, false),
                        listRegionNames(areaShop.getFileManager().getRents(), RentRegion::isRented, false)
                );

            case "num_regions_bought_own":
                // Count regions owned by the player
                return String.valueOf(countRegions(areaShop.getFileManager().getBuys(), b -> b.isOwner(player), true));

            case "num_regions_rented_own":
                // Count regions rented by the player
                return String.valueOf(countRegions(areaShop.getFileManager().getRents(), r -> r.isRenter(player), true));

            case "num_regions_occupied_own":
                // Count regions owned or rented by the player
                return String.valueOf(
                        countRegions(areaShop.getFileManager().getBuys(), b -> b.isOwner(player), true) +
                                countRegions(areaShop.getFileManager().getRents(), r -> r.isRenter(player), true)
                );

            case "list_regions_bought_own":
                // List names of regions owned by the player
                return listRegionNames(areaShop.getFileManager().getBuys(), b -> b.isOwner(player), true);

            case "list_regions_rented_own":
                // List names of regions rented by the player
                return listRegionNames(areaShop.getFileManager().getRents(), r -> r.isRenter(player), true);

            case "list_regions_occupied_own":
                // List names of regions owned or rented by the player
                return String.join(", ",
                        listRegionNames(areaShop.getFileManager().getBuys(), b -> b.isOwner(player), true),
                        listRegionNames(areaShop.getFileManager().getRents(), r -> r.isRenter(player), true)
                );

            case "num_price_owed":
                // Calculate total rent owed by the player
                return String.valueOf(calculateTotalPrice(areaShop.getFileManager().getRents(), r -> r.isRenter(player)));

            case "num_price_payed":
                // Calculate total price paid by the player for owned regions
                return String.valueOf(calculateTotalPrice(areaShop.getFileManager().getBuys(), b -> b.isOwner(player)));

            case "list_price_owed":
                // List prices of rented regions
                return listPrices(areaShop.getFileManager().getRents(), r -> r.isRenter(player));

            case "list_price_payed":
                // List prices of owned regions
                return listPrices(areaShop.getFileManager().getBuys(), b -> b.isOwner(player));

            case "list_price":
                // List prices of all owned and rented regions
                return String.join(", ",
                        listPrices(areaShop.getFileManager().getBuys(), b -> b.isOwner(player)),
                        listPrices(areaShop.getFileManager().getRents(), r -> r.isRenter(player))
                );

            default:
                return null; // Return null if the placeholder is not recognized
        }
    }

    /**
     * Counts regions based on a predicate condition.
     *
     * @param regions    List of regions to check
     * @param predicate  Condition to test
     * @param condition  Expected result of the predicate
     * @param <T>        Region type
     * @return Number of regions matching the condition
     */
    private <T> int countRegions(List<T> regions, java.util.function.Predicate<T> predicate, boolean condition) {
        return (int) regions.stream().filter(region -> predicate.test(region) == condition).count();
    }

    /**
     * Lists names of regions matching a predicate condition.
     *
     * @param regions    List of regions to check
     * @param predicate  Condition to test
     * @param condition  Expected result of the predicate
     * @param <T>        Region type
     * @return Comma-separated list of region names
     */
    private <T> String listRegionNames(List<T> regions, java.util.function.Predicate<T> predicate, boolean condition) {
        return regions.stream()
                .filter(region -> predicate.test(region) == condition)
                .map(region -> region instanceof BuyRegion ? ((BuyRegion) region).getName() : ((RentRegion) region).getName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Calculates the total price for regions matching a predicate condition.
     *
     * @param regions    List of regions to check
     * @param predicate  Condition to test
     * @param <T>        Region type
     * @return Total price
     */
    private <T> double calculateTotalPrice(List<T> regions, java.util.function.Predicate<T> predicate) {
        return regions.stream()
                .filter(predicate)
                .mapToDouble(region -> region instanceof BuyRegion ? ((BuyRegion) region).getPrice() : ((RentRegion) region).getPrice())
                .sum();
    }

    /**
     * Lists prices of regions matching a predicate condition.
     *
     * @param regions    List of regions to check
     * @param predicate  Condition to test
     * @param <T>        Region type
     * @return Comma-separated list of region prices
     */
    private <T> String listPrices(List<T> regions, java.util.function.Predicate<T> predicate) {
        return regions.stream()
                .filter(predicate)
                .map(region -> String.valueOf(region instanceof BuyRegion ? ((BuyRegion) region).getPrice() : ((RentRegion) region).getPrice()))
                .collect(Collectors.joining(", "));
    }
}
